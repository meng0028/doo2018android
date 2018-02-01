package doors.open.ottawa.types;

/**
 * A Building is assigned to a Category.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */

public enum Category {
    ALL(11)
    , RELIGIOUS(0)
    , EMBASSIES(1)
    , GOVERNMENT(2)
    , FUNCTIONAL(3)
    , GALLERIES_AND_THEATRES(4)
    , ACADEMIC(5)
    , SPORTS_AND_LEISURE(6)
    , COMMUNITY_AND_CARE(7)
    , BUSINESS_AND_FOUNDATIONS(8)
    , MUSEUMS_ARCHIVES_HISTORIC(9)
    , OTHER(10);

    private int categoryId;

    private Category(int categoryId) { this.categoryId = categoryId; }

    public int getCategoryId() { return categoryId; }
}
