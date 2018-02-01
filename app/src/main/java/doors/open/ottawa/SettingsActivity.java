package doors.open.ottawa;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * SettingsActivity is responsible for displaying the {@link SettingsFragment}. It is also
 * responsible for orchestrating proper navigation when the up button is clicked. When the up
 * button is clicked from the SettingsActivity, we want to navigate to the Activity that the user
 * came from to get to the SettingsActivity.
 * <p>
 * For example, when the user is in the DetailActivity and clicks the settings option in the menu,
 * and then clicks the up button, we want to navigate back to the DetailActivity. If the user
 * navigates to the SettingsActivity from the MainActivity, then we want to navigate back to the
 * MainActivity when the user clicks the up button from the SettingsActivity.
 */

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
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
