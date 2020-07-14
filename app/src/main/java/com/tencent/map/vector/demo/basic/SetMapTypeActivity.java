package com.tencent.map.vector.demo.basic;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.tencent.map.vector.demo.R;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;


public class SetMapTypeActivity extends SupportMapFragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){
        radioGroup = findViewById(R.id.lay_map_type);
        radioGroup.setVisibility(View.VISIBLE);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.btn_normal: //普通地图-默认地图类型
                tencentMap.setMapType(TencentMap.MAP_TYPE_NORMAL);
                break;
            case R.id.btn_satellite: //卫星地图
                tencentMap.setMapType(TencentMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.btn_dark: //暗色地图
                tencentMap.setMapType(TencentMap.MAP_TYPE_DARK);
                break;
        }
    }
}