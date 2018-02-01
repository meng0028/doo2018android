package doors.open.ottawa;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import doors.open.ottawa.model.BuildingPOJO;

import static doors.open.ottawa.services.APIService.BASE_URL;
import static doors.open.ottawa.services.APIService.FEED;

/**
 * Detailed Activity of a Building.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */
public class DetailActivity extends Activity {

    private TextView  nameText;
    private TextView  categoryText;
    private TextView  descriptionText;
    private ImageView buildingImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        BuildingPOJO selectedBuilding = getIntent().getExtras().getParcelable(BuildingAdapter.BUILDING_KEY);
        if (selectedBuilding == null) {
            throw new AssertionError("Null data item received!");
        }

        nameText = (TextView) findViewById(R.id.nameText);
        categoryText = (TextView) findViewById(R.id.categoryText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        buildingImage = (ImageView) findViewById(R.id.buildingImage);

        nameText.setText(selectedBuilding.getName());
        categoryText.setText(selectedBuilding.getCategory());
        descriptionText.setText(selectedBuilding.getDescription());
        buildingImage.setContentDescription(selectedBuilding.getImageDescription());

        String url = BASE_URL + FEED + "/" + selectedBuilding.getBuildingId() + "/image";
        Picasso.with(this)
                .load(Uri.parse(url))
                .error(R.drawable.noimagefound)
                .fit()
                .into(buildingImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}