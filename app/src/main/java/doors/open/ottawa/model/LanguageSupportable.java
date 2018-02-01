package doors.open.ottawa.model;

import doors.open.ottawa.types.Language;

/**
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */

public interface LanguageSupportable {

    Language getLanguage();
    void setLanguage(Language lang);

    String getAddress();
    String getCategory();
    String getDescription();
    String getImageDescription();
    String getName();
    String getStreetAddress();
}
