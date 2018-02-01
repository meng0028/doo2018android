package doors.open.ottawa;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * FiltersActivity is responsible for displaying the FiltersFragment.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */
public class FiltersActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FiltersFragment())
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
