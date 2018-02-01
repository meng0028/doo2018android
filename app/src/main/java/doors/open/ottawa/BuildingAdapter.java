package doors.open.ottawa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;

import doors.open.ottawa.model.BuildingPOJO;
import doors.open.ottawa.types.Category;
import doors.open.ottawa.types.Filter;

import static doors.open.ottawa.services.APIService.BASE_URL;
import static doors.open.ottawa.services.APIService.FEED;


/**
 * BuildingAdapter.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */
public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder> {

    public  static final String  BUILDING_KEY            = "building_key";
    private static final boolean LOG                     = true;
    private static final int     MAX_NUMBER_OF_BUILDINGS = 165;
    private static final String  TAG                     = "DOO";

    private final Collator COLLATOR = Collator.getInstance( new Locale("fr", "CA") );
    private final Comparator<BuildingPOJO> SORT_COMPARATOR = new Comparator<BuildingPOJO>() {
        @Override
        public int compare(BuildingPOJO b1, BuildingPOJO b2) {
            return COLLATOR.compare(b1.getName().toLowerCase(), b2.getName().toLowerCase());
        }
    };

    private Context                 mContext;
    private ArrayList<BuildingPOJO> mBuildingsList;
    private Category                mCategory;
    private ArrayList<Filter>       mFiltersList;
    private ArrayList<BuildingPOJO> mOriginalBuildingsList;

    public BuildingAdapter(Context context ) {
        this.mContext               = context;
        this.mCategory              = Category.ALL;
        this.mBuildingsList         = new ArrayList<>(MAX_NUMBER_OF_BUILDINGS);
        this.mOriginalBuildingsList = new ArrayList<>(MAX_NUMBER_OF_BUILDINGS);
        this.mFiltersList           = new ArrayList<>(Filter.values().length);
    }

    @Override
    public BuildingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View planetView = inflater.inflate(R.layout.item_building, parent, false);
        ViewHolder viewHolder = new ViewHolder(planetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BuildingAdapter.ViewHolder holder, int position) {
        final BuildingPOJO aBuilding = mBuildingsList.get(position);

        holder.nameText.setText(aBuilding.getName());
        holder.imageView.setContentDescription(aBuilding.getImageDescription());

        Picasso.with(mContext)
                .load(Uri.parse(BASE_URL + FEED + "/" + aBuilding.getBuildingId() + "/image"))
                .error(R.drawable.noimagefound)
                //.resize(96, 96)
                .fit()
                .into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(BUILDING_KEY, aBuilding);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBuildingsList.size();
    }

    /**
     * Apply filter with its value.
     *
     * IF value is true
     * THEN remember filter in list
     * ELSE remove filter from list
     *
     * Peform
     *
     * Supports LOG
     *
     * @param filter
     * @param value
     */
    public synchronized void applyFilter(Filter filter, boolean value) {
        if (value) {
            mFiltersList.add(filter);
        } else {
            mFiltersList.remove(filter);
        }

        intersectionOfFilterSet();

        if (mCategory != Category.ALL) setCategory(mCategory);

        if (LOG) {
            Log.e(TAG, "Adapter -> intersectionOfFilterSet(): filter= "
                    + filter.toString() + "\tvalue= " + value
                    + "\tsize= " + mBuildingsList.size()
                    + "\tsize(orig)= " + mOriginalBuildingsList.size());
        }
    }

    /**
     * Set list of buildings.
     *
     * Clear the list of buildings, and replace with buildingList.
     * Perform Intersection( FilterSet )
     *
     * Supports LOG
     *
     * @param buildingsList
     */
    public synchronized void setBuildings(ArrayList<BuildingPOJO> buildingsList) {
        mBuildingsList.clear();
        mBuildingsList.addAll(buildingsList);
        mOriginalBuildingsList.clear();
        mOriginalBuildingsList.addAll(buildingsList);

        intersectionOfFilterSet();

        if (LOG) {
            Log.e(TAG, "Adapter -> setBuildings(): size= " + mBuildingsList.size());
        }
    }

    /**
     * Set category.
     *
     * IF Category is ALL
     * THEN
     *    perform Intersection( FilterSet )
     * ELSE
     *    Iterate the building list
     *        IF the building under consideration does not belong to category
     *        THEN remove building
     *    Notify the building list has changed
     *
     * Supports LOG
     *
     * @param category
     */
    public synchronized void setCategory(Category category) {
        mCategory = category;
        if (category == Category.ALL) {
            intersectionOfFilterSet();
        } else {
            int targetCategoryId = category.getCategoryId();
            int beforeSize = mBuildingsList.size();
            ArrayList<BuildingPOJO> cloneList = (ArrayList<BuildingPOJO>) mBuildingsList.clone();
            for (BuildingPOJO aBuilding : cloneList) {
                if (aBuilding.getCategoryId() != targetCategoryId) {
                    mBuildingsList.remove(aBuilding);
                }
            }
            if (beforeSize != mBuildingsList.size()) {
                notifyDataSetChanged();
            }
        }

        if (LOG) {
            Log.e(TAG, "Adapter -> setCategory(): category= " + category.toString()
                    + "\tsize= " + mBuildingsList.size()
                    + "\tsize(orig)= " + mOriginalBuildingsList.size());
        }
    }

    /**
     * Sort the list of buildings.
     *
     * Notify the list of buildings has changed.
     */
    public synchronized void doSort() {
        Collections.sort(mBuildingsList, SORT_COMPARATOR);
        notifyDataSetChanged();
    }

    /**
     * Method intersectionOfFilterSet()
     *
     * Clear the list of buildings, and replace with original building list.
     *
     * For each filter (mirrors UI):
     *     list of buildings INTERSECTION list of buildings with filter
     *
     * Sort the filtered list of buildings.
     */
    private void intersectionOfFilterSet() {
        mBuildingsList.clear();
        mBuildingsList.addAll(mOriginalBuildingsList);
        HashSet<BuildingPOJO> filteredBuildings = new HashSet<>(MAX_NUMBER_OF_BUILDINGS);
        for( Filter filter : mFiltersList) {
            filteredBuildings.clear();
            for ( BuildingPOJO aBuilding: mOriginalBuildingsList) {
                switch (filter) {
                    case ACCESSIBLE:
                        if (aBuilding.isIsAccessible()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case BIKE_PARKING:
                        if (aBuilding.isIsBikeParking()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case CANADA150:
                        if (aBuilding.isIsCanada150()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case FAMILY_FRIENDLY:
                        if (aBuilding.isIsFreeParking()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case FREE_PARKING:
                        if (aBuilding.isIsFreeParking()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case GUIDED_TOUR:
                        if (aBuilding.isIsGuidedTour()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case NEW:
                        if (aBuilding.isIsNew()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case OC_TRANSPO_NEARBY:
                        if (aBuilding.isIsOCTranspoNearby()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case OPEN_SATURDAY:
                        if (aBuilding.isIsOpenSaturday()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case OPEN_SUNDAY:
                        if (aBuilding.isIsOpenSunday()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case PAID_PARKING:
                        if (aBuilding.isIsPaidParking()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case PUBLIC_WASHROOMS:
                        if (aBuilding.isIsPublicWashrooms()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;

                    case SHUTTLE:
                        if (aBuilding.isIsShuttle()) {
                            filteredBuildings.add(aBuilding);
                        }
                        break;
                }
            }
            mBuildingsList.retainAll(filteredBuildings);
        }
        doSort();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View     mView;
        public TextView nameText;
        public ImageView imageView;

        public ViewHolder(View buildingView) {
            super(buildingView);

            mView = buildingView;

            imageView = (ImageView) buildingView.findViewById(R.id.imageView);
            nameText = (TextView) buildingView.findViewById(R.id.nameText);
        }
    }
}
