package com.tencent.map.vector.demo.marker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;


import com.tencent.map.vector.demo.R;
import com.tencent.map.vector.demo.basic.SupportMapFragmentActivity;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.GroundOverlay;
import com.tencent.tencentmap.mapsdk.maps.model.GroundOverlayOptions;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.LatLngBounds;
import com.tencent.tencentmap.mapsdk.maps.model.OverlayLevel;

import java.util.ArrayList;

public class GroundOverlayActivity extends SupportMapFragmentActivity {

    private MapView mMapView;
    private TencentMap mTencentMap;
    private GroundOverlay groundOverlay;
    GroundOverlayOptions groundOverlayOptions;
    private boolean mIsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_overlay);
        mMapView = findViewById(R.id.map_view);
        mTencentMap = mMapView.getMap();
        mTencentMap.setIndoorEnabled(true);
        mTencentMap.setBuildingEnable(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.marker_groundoverlay, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_add_groundoverlay).setVisible(!mIsSelected);
        menu.findItem(R.id.menu_remove_groundoverlay).setVisible(mIsSelected);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_groundoverlay:
                addGroundOverlays();
                break;
            case R.id.menu_remove_groundoverlay:
                removeGroundOverlays();
                break;
        }

        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    public void addGroundOverlays() {
        mIsSelected = true;
        LatLngBounds lb = new LatLngBounds(
                new LatLng(40.045226, 116.280069),
                new LatLng(40.038918, 116.271873));
        BitmapDescriptor custom = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        groundOverlayOptions = new GroundOverlayOptions()
                .bitmap(custom)
                .latLngBounds(lb)
                .alpha(10);
        groundOverlay = mTencentMap.addGroundOverlay(groundOverlayOptions);
        groundOverlay.setAnchor(0.5f, 0.5f);
        mTencentMap.moveCamera(CameraUpdateFactory.newLatLngBounds(lb, 300));

    }

    private Bitmap getBitMap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 55;
        int newHeight = 55;
        float widthScale = ((float) newWidth) / width;
        float heightScale = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    public void removeGroundOverlays() {
        mIsSelected = false;
        if (groundOverlay != null) {
            groundOverlay.remove();
            groundOverlay = null;
        }
    }

}
