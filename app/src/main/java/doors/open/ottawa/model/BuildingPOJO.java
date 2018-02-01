package doors.open.ottawa.model;

import android.os.Parcel;
import android.os.Parcelable;

import doors.open.ottawa.types.Language;

/**
 * POJO class for a Building JSON object.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */
public class BuildingPOJO implements LanguageSupportable, Parcelable {

    public static Language LANGUAGE = Language.English;

    private int buildingId;
    private String nameEN;
    private String nameFR;
    private boolean isNew;
    private String addressEN;
    private String addressFR;
    private String descriptionEN;
    private String descriptionFR;
    private boolean isCanada150;
    private String categoryEN;
    private String saturdayStart;
    private String saturdayClose;
    private String sundayStart;
    private String sundayClose;
    private boolean isShuttle;
    private boolean isPublicWashrooms;
    private boolean isAccessible;
    private boolean isFreeParking;
    private boolean isBikeParking;
    private boolean isPaidParking;
    private boolean isFamilyFriendly;
    private String image;
    private boolean isGuidedTour;
    private boolean isOCTranspoNearby;
    private String imageDescriptionEN;
    private String imageDescriptionFR;
    private String streetAddressEN;
    private String streetAddressFR;
    private String city;
    private String province;
    private String postalCode;
    private double latitude;
    private double longitude;
    private int categoryId;
    private String categoryFR;
    private boolean isOpenSaturday;
    private boolean isOpenSunday;

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getNameFR() {
        return nameFR;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getAddressEN() {
        return addressEN;
    }

    public void setAddressEN(String addressEN) {
        this.addressEN = addressEN;
    }

    public String getAddressFR() {
        return addressFR;
    }

    public void setAddressFR(String addressFR) {
        this.addressFR = addressFR;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDescriptionFR() {
        return descriptionFR;
    }

    public void setDescriptionFR(String descriptionFR) {
        this.descriptionFR = descriptionFR;
    }

    public boolean isIsCanada150() {
        return isCanada150;
    }

    public void setIsCanada150(boolean isCanada150) {
        this.isCanada150 = isCanada150;
    }

    public String getCategoryEN() {
        return categoryEN;
    }

    public void setCategoryEN(String categoryEN) {
        this.categoryEN = categoryEN;
    }

    public String getSaturdayStart() {
        return saturdayStart;
    }

    public void setSaturdayStart(String saturdayStart) {
        this.saturdayStart = saturdayStart;
    }

    public String getSaturdayClose() {
        return saturdayClose;
    }

    public void setSaturdayClose(String saturdayClose) {
        this.saturdayClose = saturdayClose;
    }

    public String getSundayStart() {
        return sundayStart;
    }

    public void setSundayStart(String sundayStart) {
        this.sundayStart = sundayStart;
    }

    public String getSundayClose() {
        return sundayClose;
    }

    public void setSundayClose(String sundayClose) {
        this.sundayClose = sundayClose;
    }

    public boolean isIsShuttle() {
        return isShuttle;
    }

    public void setIsShuttle(boolean isShuttle) {
        this.isShuttle = isShuttle;
    }

    public boolean isIsPublicWashrooms() {
        return isPublicWashrooms;
    }

    public void setIsPublicWashrooms(boolean isPublicWashrooms) {
        this.isPublicWashrooms = isPublicWashrooms;
    }

    public boolean isIsAccessible() {
        return isAccessible;
    }

    public void setIsAccessible(boolean isAccessible) {
        this.isAccessible = isAccessible;
    }

    public boolean isIsFreeParking() {
        return isFreeParking;
    }

    public void setIsFreeParking(boolean isFreeParking) {
        this.isFreeParking = isFreeParking;
    }

    public boolean isIsBikeParking() {
        return isBikeParking;
    }

    public void setIsBikeParking(boolean isBikeParking) {
        this.isBikeParking = isBikeParking;
    }

    public boolean isIsPaidParking() {
        return isPaidParking;
    }

    public void setIsPaidParking(boolean isPaidParking) {
        this.isPaidParking = isPaidParking;
    }

    public boolean isIsFamilyFriendly() {
        return isFamilyFriendly;
    }

    public void setIsFamilyFriendly(boolean isFamilyFriendly) {
        this.isFamilyFriendly = isFamilyFriendly;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIsGuidedTour() {
        return isGuidedTour;
    }

    public void setIsGuidedTour(boolean isGuidedTour) {
        this.isGuidedTour = isGuidedTour;
    }

    public boolean isIsOCTranspoNearby() {
        return isOCTranspoNearby;
    }

    public void setIsOCTranspoNearby(boolean isOCTranspoNearby) {
        this.isOCTranspoNearby = isOCTranspoNearby;
    }

    public String getImageDescriptionEN() {
        return imageDescriptionEN;
    }

    public void setImageDescriptionEN(String imageDescriptionEN) {
        this.imageDescriptionEN = imageDescriptionEN;
    }

    public String getImageDescriptionFR() {
        return imageDescriptionFR;
    }

    public void setImageDescriptionFR(String imageDescriptionFR) {
        this.imageDescriptionFR = imageDescriptionFR;
    }

    public String getStreetAddressEN() {
        return streetAddressEN;
    }

    public void setStreetAddressEN(String streetAddressEN) {
        this.streetAddressEN = streetAddressEN;
    }

    public String getStreetAddressFR() {
        return streetAddressFR;
    }

    public void setStreetAddressFR(String streetAddressFR) {
        this.streetAddressFR = streetAddressFR;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryFR() {
        return categoryFR;
    }

    public void setCategoryFR(String categoryFR) {
        this.categoryFR = categoryFR;
    }

    public boolean isIsOpenSaturday() {
        return isOpenSaturday;
    }

    public void setIsOpenSaturday(boolean isOpenSaturday) {
        this.isOpenSaturday = isOpenSaturday;
    }

    public boolean isIsOpenSunday() {
        return isOpenSunday;
    }

    public void setIsOpenSunday(boolean isOpenSunday) {
        this.isOpenSunday = isOpenSunday;
    }

    public static void setLanguagePreference(Language languagePreference) { LANGUAGE = languagePreference; }

    @Override
    public Language getLanguage() {
        return LANGUAGE;
    }

    @Override
    public void setLanguage(Language lang) {
        LANGUAGE = lang;
    }

    @Override
    public String getAddress() {
        if (LANGUAGE == Language.French) {
            return addressFR;
        }
        return addressEN;
    }

    @Override
    public String getCategory() {
        if (LANGUAGE == Language.French) {
            return categoryFR;
        }
        return categoryEN;
    }

    @Override
    public String getDescription() {
        if (LANGUAGE == Language.French) {
            return descriptionFR;
        }
        return descriptionEN;
    }

    @Override
    public String getImageDescription() {
        if (LANGUAGE == Language.French) {
            return imageDescriptionFR;
        }
        return imageDescriptionEN;
    }

    @Override
    public String getName() {
        if (LANGUAGE == Language.French) {
            return nameFR;
        }
        return nameEN;
    }

    @Override
    public String getStreetAddress() {
        if (LANGUAGE == Language.French) {
            return streetAddressFR;
        }
        return streetAddressEN;
    }


    // PARCEABLE

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.buildingId);
        dest.writeString(this.nameEN);
        dest.writeString(this.nameFR);
        dest.writeByte(this.isNew ? (byte) 1 : (byte) 0);
        dest.writeString(this.addressEN);
        dest.writeString(this.addressFR);
        dest.writeString(this.descriptionEN);
        dest.writeString(this.descriptionFR);
        dest.writeByte(this.isCanada150 ? (byte) 1 : (byte) 0);
        dest.writeString(this.categoryEN);
        dest.writeString(this.saturdayStart);
        dest.writeString(this.saturdayClose);
        dest.writeString(this.sundayStart);
        dest.writeString(this.sundayClose);
        dest.writeByte(this.isShuttle ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isPublicWashrooms ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAccessible ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFreeParking ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isBikeParking ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isPaidParking ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFamilyFriendly ? (byte) 1 : (byte) 0);
        dest.writeString(this.image);
        dest.writeByte(this.isGuidedTour ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isOCTranspoNearby ? (byte) 1 : (byte) 0);
        dest.writeString(this.imageDescriptionEN);
        dest.writeString(this.imageDescriptionFR);
        dest.writeString(this.streetAddressEN);
        dest.writeString(this.streetAddressFR);
        dest.writeString(this.city);
        dest.writeString(this.province);
        dest.writeString(this.postalCode);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeInt(this.categoryId);
        dest.writeString(this.categoryFR);
        dest.writeByte(this.isOpenSaturday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isOpenSunday ? (byte) 1 : (byte) 0);
    }

    public BuildingPOJO() {
    }

    protected BuildingPOJO(Parcel in) {
        this.buildingId = in.readInt();
        this.nameEN = in.readString();
        this.nameFR = in.readString();
        this.isNew = in.readByte() != 0;
        this.addressEN = in.readString();
        this.addressFR = in.readString();
        this.descriptionEN = in.readString();
        this.descriptionFR = in.readString();
        this.isCanada150 = in.readByte() != 0;
        this.categoryEN = in.readString();
        this.saturdayStart = in.readString();
        this.saturdayClose = in.readString();
        this.sundayStart = in.readString();
        this.sundayClose = in.readString();
        this.isShuttle = in.readByte() != 0;
        this.isPublicWashrooms = in.readByte() != 0;
        this.isAccessible = in.readByte() != 0;
        this.isFreeParking = in.readByte() != 0;
        this.isBikeParking = in.readByte() != 0;
        this.isPaidParking = in.readByte() != 0;
        this.isFamilyFriendly = in.readByte() != 0;
        this.image = in.readString();
        this.isGuidedTour = in.readByte() != 0;
        this.isOCTranspoNearby = in.readByte() != 0;
        this.imageDescriptionEN = in.readString();
        this.imageDescriptionFR = in.readString();
        this.streetAddressEN = in.readString();
        this.streetAddressFR = in.readString();
        this.city = in.readString();
        this.province = in.readString();
        this.postalCode = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.categoryId = in.readInt();
        this.categoryFR = in.readString();
        this.isOpenSaturday = in.readByte() != 0;
        this.isOpenSunday = in.readByte() != 0;
    }

    public static final Creator<BuildingPOJO> CREATOR = new Creator<BuildingPOJO>() {
        @Override
        public BuildingPOJO createFromParcel(Parcel source) {
            return new BuildingPOJO(source);
        }

        @Override
        public BuildingPOJO[] newArray(int size) {
            return new BuildingPOJO[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingPOJO that = (BuildingPOJO) o;

        return buildingId == that.buildingId;

    }

    @Override
    public int hashCode() {
        return buildingId;
    }
}