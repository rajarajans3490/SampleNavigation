package com.android.test.samplenavigation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Locale;

public class SampleNavigationActivity extends Activity {
    private String TAG = "SampleNavigationActivity";
    private Spinner mSpinner = null;
    private TextView mTextView2 = null;
    private TextView mTextView3 = null;
    private Button mButton = null;
    private String ServerURL = null;
    private HttpJSONParser mHttpJSONParser = null;
    private JSONObject mJSONArrayObject = null;
    private JSONArray mJSONArray = null;
    private String[] name_arr = null;
    ArrayAdapter<String> adapter = null;
    Context mContext = null;
    private ArrayList<LocationValues> mlocationarray = null;
    private ArrayList<Place> mplacearray = null;
    private ProgressDialog Dialog = null;
    private Double mLatitude = null;
    private Double mLongitude = null;
    private AlertDialog.Builder mAlertDialogbuild= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mButton = (Button)findViewById(R.id.button);
        ServerURL = "http://express-it.optusnet.com.au/sample.json";
        ConnectivityManager mConncMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConncMgr.getActiveNetworkInfo();
            if (mNetworkInfo == null || (!mNetworkInfo.isConnected()))
            {
                mAlertDialogbuild = new AlertDialog.Builder(mContext);
                mAlertDialogbuild.setTitle(getResources().getString(R.string.app_name));
                mAlertDialogbuild.setMessage(getResources().getString(R.string.nointernet));
                mAlertDialogbuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SampleNavigationActivity.this.finish();
                    }
                });
                AlertDialog mAlertDialog = mAlertDialogbuild.create();
                mAlertDialog.show();
            } else {
                    new AsyncOperation().execute(ServerURL);
            }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLatitude != null && mLongitude != null){
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", mLatitude, mLongitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
                } else {
                    Toast.makeText(mContext,"Location Coordinates are not Found",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class AsyncOperation extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialog = new ProgressDialog(mContext);
            Dialog.setMessage("Please Wait Fetching Data from Server...");
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            mHttpJSONParser = new HttpJSONParser();
            mJSONArray = mHttpJSONParser.getJSONData(ServerURL);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (Dialog.isShowing())
                Dialog.dismiss();
            Toast.makeText(mContext,"Error while Connecting to the Server",Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (Dialog.isShowing())
                Dialog.dismiss();
                showValues();
        }


    public class CustomOnItemSelectedListener implements OnItemSelectedListener {

        Context mContext;

        public CustomOnItemSelectedListener(Context context){
            this.mContext = context;
        }

        public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
                String mModecar ="";
                String mModetrain ="";
                mLatitude = null;
                mLongitude = null;

                if(mplacearray != null && mplacearray.size() > 0) {
                    mModecar = mplacearray.get(pos).getCarvalue();

                    mTextView2.setText("Car -" + mModecar);
                    if(mplacearray.get(pos).getTrainvalue()!= null && mplacearray.get(pos).getTrainvalue().length() > 0 ) {
                        mModetrain = mplacearray.get(pos).getTrainvalue();
                        mTextView3.setText("Train -" + mModetrain);
                    } else {
                        mTextView3.setText("");
                    }
                }
                if(mlocationarray !=null && mlocationarray.size() > 0){
                    mLatitude  = mlocationarray.get(pos).getlatitude();
                    mLongitude = mlocationarray.get(pos).getlongitude();
                }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void showValues(){

            mplacearray = new ArrayList<Place>();
            mlocationarray = new ArrayList<LocationValues>();
            if(mJSONArray != null)
            name_arr = new String[mJSONArray.length()];
            else{
                Toast.makeText(mContext,"No Data Fetched from Server.Check your Internet Connectivity and Restart the Application",Toast.LENGTH_LONG).show();
                if (Dialog.isShowing())
                    Dialog.dismiss();
                    return;
            }
            for (int i = 0; i < mJSONArray.length(); i++) {
                try {
                    Place mplaceval = new Place();
                    LocationValues mlocationval = new LocationValues();
                    mJSONArrayObject = mJSONArray.getJSONObject(i);
                    name_arr[i] = mJSONArrayObject.getString("name");
                    mplaceval.setCarValue(mJSONArrayObject.getJSONObject("fromcentral").optString("car"));
                    mplaceval.setTrainValue(mJSONArrayObject.getJSONObject("fromcentral").optString("train"));
                    mlocationval.setlatitude(mJSONArrayObject.getJSONObject("location").getDouble("latitude"));
                    mlocationval.setlongitude(mJSONArrayObject.getJSONObject("location").getDouble("longitude"));
                    mplacearray.add(mplaceval);
                    mlocationarray.add(mlocationval);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            mTextView2 = (TextView)findViewById(R.id.textView2);
            mTextView3 = (TextView)findViewById(R.id.textView3);
            adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,name_arr);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            OnItemSelectedListener itemSelectedListener = new CustomOnItemSelectedListener(mContext);
            mSpinner.setOnItemSelectedListener(itemSelectedListener);
        }

    }

    @Override
    protected void onDestroy() {
        if (mplacearray != null)
            mplacearray.clear();
        if (mlocationarray != null)
            mlocationarray.clear();
        super.onDestroy();
    }
}
