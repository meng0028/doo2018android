package doors.open.ottawa;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import doors.open.ottawa.data.UserPreferences;
import doors.open.ottawa.model.BuildingPOJO;
import doors.open.ottawa.services.APIServiceWrapper;
import doors.open.ottawa.services.NetworkHelper;
import doors.open.ottawa.types.Category;
import doors.open.ottawa.types.Filter;
import doors.open.ottawa.types.Language;

/**
 * Android app for Doors Open Ottawa
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */

public class MainActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private BuildingAdapter mAdapter;
    private TextView        mEmptyListText;
    private ProgressBar     mProgressBar;
    private RecyclerView    mRecyclerView;
    private Parcelable      mRecyclerViewState;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (intent.hasExtra(APIServiceWrapper.API_SERVICE_PAYLOAD)) {
                BuildingPOJO[] buildingsArray = (BuildingPOJO[]) intent
                        .getParcelableArrayExtra(APIServiceWrapper.API_SERVICE_PAYLOAD);

                mAdapter.setBuildings(new ArrayList<>(Arrays.asList(buildingsArray)));

                applySharedPreferences();
            } else if (intent.hasExtra(APIServiceWrapper.API_SERVICE_EXCEPTION)) {
                String message = intent.getStringExtra(APIServiceWrapper.API_SERVICE_EXCEPTION);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView + Adapter
        mRecyclerView = findViewById(R.id.buildingsRV);
        mRecyclerView.setHasFixedSize( true );
        mAdapter = new BuildingAdapter( this );
        mRecyclerView.setAdapter( mAdapter );

        // Reference <ProgressBar> when busy fetching data
        mProgressBar = findViewById(R.id.networkPB);
        mProgressBar.setVisibility(View.INVISIBLE);

        // Reference <TextView> when building list is empty
        mEmptyListText = findViewById(R.id.emptyListText);
        mEmptyListText.setVisibility(View.INVISIBLE);

        // Register (local) BroadcastReceiver
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(APIServiceWrapper.API_SERVICE_MESSAGE));

        // Initialization: suppress toast messages # of buildings
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.contains(getString(R.string.pref_show_toast_key))) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.pref_show_toast_key), false);
            //editor.commit();
            editor.apply();
        }

        /*
         * Register MainActivity as an OnPreferenceChangedListener to receive a callback when a
         * SharedPreference has changed. Please note that we must unregister MainActivity as an
         * OnSharedPreferenceChanged listener in onDestroy to avoid any memory leaks.
         */
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        /*
         * Set the language to display the building data. Lang is set according to user's lang pref.
         */
        BuildingPOJO.setLanguagePreference( UserPreferences.getLanguagePreference(this) );

        // Fetch the data, and display
        fetchBuildingData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Un-register (local) BroadcastReceiver
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);

        /* Unregister MainActivity as an OnPreferenceChangedListener to avoid any memory leaks. */
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // save RecyclerView state
        mRecyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // restore RecyclerView state
        if (mRecyclerViewState != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerViewState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_filters:
                Intent startFiltersCategory = new Intent(this, FiltersActivity.class);
                startActivity(startFiltersCategory);
                return true;

            case R.id.action_refresh:
                fetchBuildingData();
                return true;

            case R.id.action_settings:
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // IGNORE: CLEAR_ALL_FILTERS
        if (key.equals(getString(R.string.pref_filter_clear_all_key))) {
            return;
        }

        // IGNORE: SHOW_TOAST
        if (key.equals(getString(R.string.pref_show_toast_key))) {
            return;
        }

        // LANGUAGE
        if (key.equals(getString(R.string.pref_lang_key))) {
            // get lang pref from View (Settings)
            String defaultLang = getString(R.string.pref_lang_default);
            String langPref = sharedPreferences.getString(key, defaultLang);
            // set lang pref in Building POJO (aka model)
            BuildingPOJO.setLanguagePreference(Language.valueOf(langPref));
            mAdapter.doSort();
            return;
        }

        // remember the number of buildings before filtering by category or filters
        // only Toast provided count is different
        int beforeCount = mAdapter.getItemCount();

        // CATEGORY
        if (key.equals(getString(R.string.pref_category_key))) {
            String defaultCategory = getString(R.string.pref_category_default);
            String categoryPref = sharedPreferences.getString(key, defaultCategory);
            mAdapter.setCategory(Category.valueOf(categoryPref));
            int index;
            if (categoryPref.equals(getString(R.string.pref_category_all))) {
                index = 0;
            } else {
                index = Category.valueOf(categoryPref).ordinal();
            }
            if ( beforeCount != mAdapter.getItemCount() ) {
                if ( mAdapter.getItemCount() == 1 ) {
                    Toast.makeText(MainActivity.this
                            , String.format(getString(R.string.buildings_count_category_singular)
                                    , mAdapter.getItemCount()
                                    , getResources().getStringArray(R.array.pref_category_options)[index])
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this
                            , String.format(getString(R.string.buildings_count_category)
                                    , mAdapter.getItemCount()
                                    , getResources().getStringArray(R.array.pref_category_options)[index])
                            , Toast.LENGTH_SHORT).show();
                }
            }
            if (mAdapter.getItemCount() == 0) {
                mEmptyListText.setVisibility(View.VISIBLE);
            } else {
                mEmptyListText.setVisibility(View.INVISIBLE);
            }
            return;
        }

        // FILTERS
        if (key.equals(getString(R.string.pref_filter_accessible_key))) {
            mAdapter.applyFilter(Filter.ACCESSIBLE, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_bike_parking_key))) {
            mAdapter.applyFilter(Filter.BIKE_PARKING, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_canada150_key))) {
            mAdapter.applyFilter(Filter.CANADA150, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_family_friendly_key))) {
            mAdapter.applyFilter(Filter.FAMILY_FRIENDLY, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_free_parking_key))) {
            mAdapter.applyFilter(Filter.FREE_PARKING, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_guided_tour_key))) {
            mAdapter.applyFilter(Filter.GUIDED_TOUR, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_new_key))) {
            mAdapter.applyFilter(Filter.NEW, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_oc_transpo_nearby_key))) {
            mAdapter.applyFilter(Filter.OC_TRANSPO_NEARBY, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_open_saturday_key))) {
            mAdapter.applyFilter(Filter.OPEN_SATURDAY, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_open_sunday_key))) {
            mAdapter.applyFilter(Filter.OPEN_SUNDAY, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_paid_parking_key))) {
            mAdapter.applyFilter(Filter.PAID_PARKING, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_public_washrooms_key))) {
            mAdapter.applyFilter(Filter.PUBLIC_WASHROOMS, sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_filter_shuttle_key))) {
            mAdapter.applyFilter(Filter.SHUTTLE, sharedPreferences.getBoolean(key, false));
        }

        boolean showToast = true;
        if (sharedPreferences.contains(getString(R.string.pref_show_toast_key))) {
            showToast = sharedPreferences.getBoolean(getString(R.string.pref_show_toast_key), true);
        }

        if (showToast) {
//            if (beforeCount != mAdapter.getItemCount()) {
            displayToast();
//            }
        }
    }

    private void displayToast() {
        if ( mAdapter.getItemCount() == 1 ) {
            Toast.makeText(this
                    , String.format(getString(R.string.buildings_count_singular)
                            , mAdapter.getItemCount())
                    , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this
                    , String.format(getString(R.string.buildings_count)
                            , mAdapter.getItemCount())
                    , Toast.LENGTH_SHORT).show();
        }
        if (mAdapter.getItemCount() == 0) {
            mEmptyListText.setVisibility(View.VISIBLE);
        } else {
            mEmptyListText.setVisibility(View.INVISIBLE);
        }
    }

    private void fetchBuildingData() {
        if (NetworkHelper.hasNetworkAccess(this)) {
            mProgressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, APIServiceWrapper.class);
            startService(intent);
        } else {
            Toast.makeText(this, "Network not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void applySharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // CATEGORY
        if (sharedPreferences.contains(getString(R.string.pref_category_key))) {
            mAdapter.setCategory(Category.valueOf(sharedPreferences.getString(getString(R.string.pref_category_key), getString(R.string.pref_category_default))));
        }

        // FILTERS
        for( Filter f : Filter.values()) {
            switch (f) {
                case ACCESSIBLE:
                case BIKE_PARKING:
                case CANADA150:
                case FAMILY_FRIENDLY:
                case FREE_PARKING:
                case GUIDED_TOUR:
                case NEW:
                case OC_TRANSPO_NEARBY:
                case OPEN_SATURDAY:
                case OPEN_SUNDAY:
                case PAID_PARKING:
                case PUBLIC_WASHROOMS:
                case SHUTTLE:
                    if (sharedPreferences.contains(f.name())) {
                        // apply those filters that are true
                        if ( sharedPreferences.getBoolean(f.name(), false) ) {
                            mAdapter.applyFilter(f, true);
                        }
                    }
                    break;
            }
        }

        displayToast();
    }
}
