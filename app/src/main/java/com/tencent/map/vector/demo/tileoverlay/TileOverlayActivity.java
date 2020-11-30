package com.tencent.map.vector.demo.tileoverlay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tencent.map.vector.demo.R;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Tile;
import com.tencent.tencentmap.mapsdk.maps.model.TileOverlay;
import com.tencent.tencentmap.mapsdk.maps.model.TileOverlayOptions;
import com.tencent.tencentmap.mapsdk.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TileOverlayActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mapView;
    private TencentMap map;
    private Button btnAddTileOverlay;
    private Button btnClearTileOverlay;
    private TileOverlay tileOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_overlay);
        init();
    }


    private void init() {
        mapView = findViewById(R.id.mapView);
        map = mapView.getMap();
        //调整视野
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.917505, 116.397657), 15));
        btnAddTileOverlay = (Button) findViewById(R.id.btn_add_tile_overlay);
        btnClearTileOverlay = (Button) findViewById(R.id.btn_clear_tile_overlay_cache);
        btnAddTileOverlay.setOnClickListener(this);
        btnClearTileOverlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_tile_overlay:
                tileOverlay = map.addTileOverlay(
                        new TileOverlayOptions().
                                tileProvider(new LocalTileProvider()));

                break;
            case R.id.btn_clear_tile_overlay_cache:
                if (tileOverlay != null) {
                    tileOverlay.clearTileCache();
                    tileOverlay.remove();
                    tileOverlay = null;
                }
                break;
        }
    }

    //TileProvider瓦片获取接口
    class LocalTileProvider implements TileProvider {
        @Override
        public Tile getTile(int x, int y, int zoom) {//表示缩放级别 zoom 下，的第 (x,y) 个瓦片
            LatLng latLng = new LatLng(39.917505, 116.397657);
            int iZ = 16;
            //表示2的iZ次方
            double n = Math.pow(2, iZ);
            int iX = (int) (((latLng.getLongitude() + 180) / 360) * n);
            int iY = (int) ((1 - (Math.log(Math.tan(Math.toRadians(latLng.getLatitude())) +
                    (1 / Math.cos(Math.toRadians(latLng.getLatitude())))) / Math.PI)) / 2 * n);
            if (iX == x && iY == y && iZ == zoom) {
                Log.e("tile", "zoom:" + zoom + " x:" + x + " y:" + y);
                return new Tile(256, 256, tileData());
            }
            return TileProvider.NO_TILE;
        }

        byte[] tileData() {
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                is = getApplicationContext().getAssets().open("groundoverlay.jpg");
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                int count = 0;
                while ((count = is.read(byteBuffer)) != -1) {
                    baos.write(byteBuffer, 0, count);
                }
                return baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
