package com.tencent.map.vector.demo.utils;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.map.vector.demo.R;
import com.tencent.map.vector.demo.basic.SupportMapFragmentActivity;
import com.google.gson.Gson;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.Projection;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnMapLongClickListener;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.VisibleRegion;

public class CoordinateActivity extends AppCompatActivity implements OnMapLongClickListener, TencentMap.OnMapClickListener {
    private TextView textView;
    private MapView mapView;
    private TencentMap tencentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);
        mapView = findViewById(R.id.mapview);
        tencentMap = mapView.getMap();
        textView = findViewById(R.id.tv);
        tencentMap.setOnMapClickListener(this);
        tencentMap.setOnMapLongClickListener(this);
    }

    /**
     * 点击地图，显示对应点击点的屏幕坐标
     *
     * @param latLng
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        Projection projection = tencentMap.getProjection();
        Point screen = projection.toScreenLocation(latLng);
        LatLng transferLatLng = projection.fromScreenLocation(screen);
        textView.setText("屏幕坐标：" + new Gson().toJson(screen) + "\n屏幕点对应经纬度：" + new Gson().toJson(transferLatLng));
    }


    /**
     * 长点击地图， 显示当前地图的坐标范围
     *
     * @param latLng
     */
    @Override
    public void onMapClick(LatLng latLng) {
        Projection projection = tencentMap.getProjection();
        VisibleRegion region = projection.getVisibleRegion();
        textView.setText("当前地图视野的经纬度：" + new Gson().toJson(region));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mapView.onRestart();
    }
}
