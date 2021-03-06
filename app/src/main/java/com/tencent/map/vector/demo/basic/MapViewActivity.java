package com.tencent.map.vector.demo.basic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.map.vector.demo.R;
import com.tencent.tencentmap.mapsdk.maps.MapView;

public class MapViewActivity extends AppCompatActivity {
    /**
     * 由于SDK并没有提供用于MapView管理地图生命周期的Activity
     * 因此需要用户继承Activity后管理地图的生命周期，防止内存泄露
     */
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        mapView = findViewById(R.id.mapview);
    }

    /**
     * mapview的生命周期管理
     */
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
