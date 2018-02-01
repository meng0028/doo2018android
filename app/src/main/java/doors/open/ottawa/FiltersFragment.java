package doors.open.ottawa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.ListAdapter;

import doors.open.ottawa.types.Filter;


/**
 * The FiltersFragment serves as the display for all of the building filters.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */
public class FiltersFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.filters);

        ListAdapter sharedPreferences = getPreferenceScreen().getRootAdapter();
        for (int i = 0; i < sharedPreferences.getCount(); i++) {
            Object preference = sharedPreferences.getItem(i);
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                listPreference.setSummary(listPreference.getEntry());
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        /* Register the preference change listener */
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        /* Unregister the preference change listener */
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (null != preference) {
            if (!(preference instanceof SwitchPreference) && !(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference, sharedPreferences.getString(key, ""));
            }

            if (preference instanceof CheckBoxPreference) {
                // IS Clear All Filters Checkbox TRUE? THEN SET EACH Filter Switch Preference to FALSE
                if (((CheckBoxPreference) preference).isChecked()) {
                    SwitchPreference filterSwitchPref;
                    // FOR EACH Filter (Accessible, Bike Parking.... Shuttle)
                    for (Filter filter : Filter.values()) {
                        filterSwitchPref = (SwitchPreference) getPreferenceManager().findPreference(filter.name());
                        // IS Filter Switch Preference TRUE? THEN SET TO FALSE (b/c Clear All Filters is TRUE)
                        if (filterSwitchPref.isChecked()) {
                            // stop listening; i.e. suppress Toast message # of buildings
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(getString(R.string.pref_show_toast_key), false);
                            editor.commit();
                            getPreferenceScreen().getSharedPreferences()
                                    .unregisterOnSharedPreferenceChangeListener(this);
                            // SET Filter <= FALSE
                            filterSwitchPref.setChecked(false);
                            getPreferenceScreen().getSharedPreferences()
                                    .registerOnSharedPreferenceChangeListener(this);
                            // listen again
                            editor = sharedPreferences.edit();
                            editor.putBoolean(getString(R.string.pref_show_toast_key), true);
                            editor.commit();
                        }

                        if (filter == Filter.SHUTTLE) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(getString(R.string.pref_show_toast_key), false);
                            editor.commit();
                            getPreferenceScreen().getSharedPreferences()
                                    .unregisterOnSharedPreferenceChangeListener(this);
                            filterSwitchPref.setChecked(true);
                            getPreferenceScreen().getSharedPreferences()
                                    .registerOnSharedPreferenceChangeListener(this);
                            // listen again
                            editor = sharedPreferences.edit();
                            editor.putBoolean(getString(R.string.pref_show_toast_key), true);
                            editor.commit();
                        }
                    }
                    // Effect: just *one* Toast message # of buildings
                    filterSwitchPref = (SwitchPreference) getPreferenceManager().findPreference(getString(R.string.pref_filter_shuttle_key));
                    filterSwitchPref.setChecked(false);
                }
            }

            if (preference instanceof SwitchPreference) {
                // IS Filter Switch Preference TRUE? THEN SET Clear All Filters Checkbox to FALSE
                if (((SwitchPreference) preference).isChecked()) {
                    CheckBoxPreference clearAllFiltersCB = (CheckBoxPreference) getPreferenceManager().findPreference(getString(R.string.pref_filter_clear_all_key));
                    // IS Clear All Filters Checkbox TRUE? THEN SET to FALSE
                    if (clearAllFiltersCB.isChecked()) {
                        // stop listening; i.e. suppress Toast message # of buildings
                        getPreferenceScreen().getSharedPreferences()
                                .unregisterOnSharedPreferenceChangeListener(this);
                        // SET Clear All Filters Checkbox <= FALSE
                        clearAllFiltersCB.setChecked(false);
                        // listen again
                        getPreferenceScreen().getSharedPreferences()
                                .registerOnSharedPreferenceChangeListener(this);
                    }
                }
            }
        }
    }

    private void setPreferenceSummary(Preference preference, Object value) {
        String stringValue = value.toString();
        String key = preference.getKey();

        if (preference instanceof ListPreference) {
            /* For list preferences, look up the correct display value in */
            /* the preference's 'entries' list (since they have separate labels/values). */
            ListPreference listPreference = (ListPreference) preference;
            int preIndex = listPreference.findIndexOfValue(stringValue);
            if (preIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[preIndex]);
            }
        }else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
    }
}
