package com.tencent.map.vector.demo.route;

import android.os.Bundle;
import android.util.Log;

import com.tencent.lbssearch.object.param.TransitParam;
import com.tencent.lbssearch.object.result.RoutePlanningObject;
import com.tencent.lbssearch.object.result.TransitResultObject;
import com.tencent.lbssearch.object.result.WalkingResultObject;
import com.tencent.map.vector.demo.basic.SupportMapFragmentActivity;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.DrivingParam;
import com.tencent.lbssearch.object.result.DrivingResultObject;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.LatLngBounds;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;

import java.util.List;

import static com.tencent.lbssearch.object.param.TransitParam.Preference.NO_SUBWAY;

public class DrivingRouteActivity extends SupportMapFragmentActivity {

    private LatLng fromPoint = new LatLng(20.072701, 110.335007);
    private LatLng toPoint = new LatLng(19.507853, 109.492021);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDrivingRoute();
    }

    /**
     * 获取驾车导航规划
     */
    private void getDrivingRoute() {
        DrivingParam drivingParam = new DrivingParam(fromPoint, toPoint); //创建导航参数
        TencentSearch tencentSearch = new TencentSearch(this);
        tencentSearch.getRoutePlan(drivingParam, new HttpResponseListener<DrivingResultObject>() {

            @Override
            public void onSuccess(int i, DrivingResultObject drivingResultObject) {
                if (drivingResultObject == null) {
                    Log.i("TAG", "baseObject为空");
                    return;
                }
                showDrivingRoute(drivingResultObject);
                Log.i("TAG", "message:" + drivingResultObject.message);
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("TAG:", i + "  " + s);
            }
        });
    }

    private void showDrivingRoute(DrivingResultObject drivingResultObject) {
        tencentMap.clearAllOverlays();
        if (drivingResultObject.result != null && drivingResultObject.result.routes != null && drivingResultObject.result.routes.size() > 0) {
            for (int i = 0; i < drivingResultObject.result.routes.size(); i++) {
                DrivingResultObject.Route result = drivingResultObject.result.routes.get(i);
                tencentMap.addPolyline(new PolylineOptions().addAll(result.polyline).color(i + 1).width(20));
                tencentMap.moveCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds.builder()
                        .include(result.polyline).build(), 100));
            }

        } else {
            Log.i("TAG", "路线结果为空");
        }
    }
}

