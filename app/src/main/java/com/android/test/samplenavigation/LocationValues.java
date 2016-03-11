package com.android.test.samplenavigation;

/*
 *This class introduced to set and get the Location values
 *populated from server.These Coordinate data values will be
 *used while Navigating to the Location
 */
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
