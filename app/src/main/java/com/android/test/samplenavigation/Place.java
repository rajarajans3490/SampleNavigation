package com.android.test.samplenavigation;

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
