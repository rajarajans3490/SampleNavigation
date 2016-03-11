package com.android.test.samplenavigation;
/*
 *This class introduced to set and get the Place values
 *populated from server.These Place data values will be
 *used for showing the Mode of transport.
 */
public class Place {
    String mCar;
    String mTrain;
    public Place(){
        mCar = null;
        mTrain = null;
    }


    public String getCarvalue() {
        return mCar;
    }
    public void setCarValue(String car) {
        this.mCar = car;
    }
    public String getTrainvalue() {
        return mTrain;
    }
    public void setTrainValue(String train) {
        this.mTrain = train;
    }
}
