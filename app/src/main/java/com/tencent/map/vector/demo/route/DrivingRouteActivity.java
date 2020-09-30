package com.tencent.map.vector.demo.route;

import android.os.Bundle;
import android.util.Log;

import com.tencent.lbssearch.object.param.TransitParam;
import com.tencent.lbssearch.object.result.TransitResultObject;
import com.tencent.map.vector.demo.basic.SupportMapFragmentActivity;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.DrivingParam;
import com.tencent.lbssearch.object.result.DrivingResultObject;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;

import java.util.List;

import static com.tencent.lbssearch.object.param.TransitParam.Preference.NO_SUBWAY;

public class DrivingRouteActivity extends SupportMapFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CameraUpdate cameraSigma =
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(20.072701, 110.335007),
                        15,
                        0f,
                        0f));
        //移动地图
        tencentMap.moveCamera(cameraSigma);
        getWalkingRoute();
    }

    private LatLng fromPoint = new LatLng(20.072701, 110.335007);
    private LatLng toPoint = new LatLng(19.507853, 109.492021);

    /**
     * 获取步行导航规划
     */
    private void getWalkingRoute() {
        // DrivingParam drivingParam = new DrivingParam(fromPoint, toPoint); //创建导航参数
        TransitParam transitParam = new TransitParam(new LatLng(20.072701, 110.335007), new LatLng(19.507853, 109.492021));
        transitParam.policy(TransitParam.Policy.LEAST_TIME, NO_SUBWAY);
        TencentSearch tencentSearch = new TencentSearch(this);
        tencentSearch.getRoutePlan(transitParam, new HttpResponseListener<TransitResultObject>() {

            @Override
            public void onSuccess(int i, TransitResultObject drivingResultObject) {
                Log.d("DrivingRouteActivity", "onSuccess: " + drivingResultObject.message + i);
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.d("DrivingRouteActivity", "onSuccess: " + s + i);
            }
        });
    }

}

