package com.holy.mobilesecurity.service;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.holy.mobilesecurity.utils.ConstantValue;
import com.holy.mobilesecurity.utils.SpUtil;

/**
 * Created by holy on 2016/5/8.
 */
public class LocationService extends Service {

    private MyLocationListener mMyLocationistener;
    private String mLongitude;
    private String altitude;
    private String accuracy;
    private String latitude;
    private LocationManager mLocationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationManager = (LocationManager) getApplicationContext().
                        getSystemService(getApplicationContext().LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        String bestProvider = mLocationManager.getBestProvider(criteria, true);

        mMyLocationistener = new MyLocationListener();
        mLocationManager.requestLocationUpdates(bestProvider, 0, 0, mMyLocationistener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mLocationManager.removeUpdates(mMyLocationistener);
    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            mLongitude =  "经度：" + location.getLongitude();//获取经度
            latitude = "纬度：" + location.getLatitude();    //获取纬度
            accuracy = "精确度：" + location.getAccuracy();     //获取精确度
            altitude = "海拔：" + location.getAltitude();     //获取海拔

            SpUtil.putString(getApplicationContext(), ConstantValue.LOCATION_INFO,
                    mLongitude+"\n"+latitude+"\n"+accuracy+"\n"+altitude);

            stopSelf();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}
