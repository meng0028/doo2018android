package doors.open.ottawa.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import doors.open.ottawa.R;
import doors.open.ottawa.types.Language;

/**
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */

public class UserPreferences {

    public static Language getLanguagePreference(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForLang = context.getResources().getString(R.string.pref_lang_key);
        String defaultLang = context.getString(R.string.pref_lang_default);
        String langPref = prefs.getString(keyForLang, defaultLang);
        return Language.valueOf(langPref);
    }
}
