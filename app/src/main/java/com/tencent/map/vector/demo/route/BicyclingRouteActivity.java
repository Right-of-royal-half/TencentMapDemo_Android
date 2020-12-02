package com.tencent.map.vector.demo.route;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.BicyclingParam;
import com.tencent.lbssearch.object.param.WalkingParam;
import com.tencent.lbssearch.object.result.BicyclingResultObject;
import com.tencent.lbssearch.object.result.RoutePlanningObject;
import com.tencent.lbssearch.object.result.WalkingResultObject;
import com.tencent.map.vector.demo.R;
import com.tencent.map.vector.demo.basic.SupportMapFragmentActivity;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.LatLngBounds;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;

import java.util.Map;

public class BicyclingRouteActivity extends SupportMapFragmentActivity {

    private LatLng fromPoint = new LatLng(40.038691,116.361442); // 起点坐标
    private LatLng toPoint = new LatLng(39.984579,116.313156); //终点坐标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBicyclingRoute();

    }

    private void getBicyclingRoute() {

        BicyclingParam bicyclingParam = new BicyclingParam();
        bicyclingParam.from(fromPoint);
        bicyclingParam.to(toPoint);
        TencentSearch tencentSearch = new TencentSearch(getApplicationContext());
        Log.i("TAG", "checkParams:" + bicyclingParam.checkParams());
        tencentSearch.getRoutePlan(bicyclingParam, new HttpResponseListener<BicyclingResultObject>() {
            @Override
            public void onSuccess(int statusCode, BicyclingResultObject object) {
                if (object == null) {
                    Log.i("TAG", "baseObject为空");
                    return;
                }
                showBicyclingRoute(object);
                Log.i("TAG", "message:" + object.message);
            }

            @Override
            public void onFailure(int statusCode, String responseString, Throwable throwable) {
                Log.i("TAG:", statusCode + "  " + responseString);
            }
        });
    }
    private void showBicyclingRoute(BicyclingResultObject object) {
        tencentMap.clearAllOverlays();
        if (object.result != null && object.result.routes != null && object.result.routes.size() > 0) {
            for (int i = 0; i < object.result.routes.size(); i++) {
                BicyclingResultObject.Route result = object.result.routes.get(i);
                tencentMap.addPolyline(new PolylineOptions().addAll(result.polyline).color(i + 1).width(20));
                for (RoutePlanningObject.Step step : result.steps) {
                    Log.i("TAG", "step:" + step.road_name + " " + step.distance + " "
                            + step.instruction + " " + step.act_desc + " " + step.dir_desc);
                }
                tencentMap.moveCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds.builder()
                        .include(result.polyline).build(), 100));
            }

        } else {
            Log.i("TAG", "路线结果为空");
        }
    }
}
