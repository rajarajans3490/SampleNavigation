package com.android.test.samplenavigation;

public class LocationValues {
    Double mLatitude;
    Double mLongitude;
    public LocationValues(){
        mLatitude = null;
        mLongitude = null;
    }
     public Double getlatitude() {
        return mLatitude;
    }
    public void setlatitude(Double latitude) {
        this.mLatitude = latitude;
    }
    public Double getlongitude() {
        return mLongitude;
    }
    public void setlongitude(Double longitude) {
        this.mLongitude = longitude;
    }


}
