package com.sjz.zyl.appdemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.Article;

import org.kymjs.kjframe.KJActivity;

/**
 * AMapV2地图中简单介绍一些Marker的用法.
 */
public class InfoWindowActivity extends KJActivity implements OnClickListener {
	private MarkerOptions markerOption;
	private AMap aMap;
	private MapView mapView;
	private Marker marker;
	private float lat;
	private float lng;
	private String address;
	private LatLng latlng ;

	@Override
	public void setRootView() {

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marker_activity);
		Intent intent = getIntent();
		lat= intent.getFloatExtra("lat", 39.91746f);
		lng = intent.getFloatExtra("lng",116.396481f); // 没有输入值默认为0
		address=intent.getStringExtra("lacation");
		latlng= new LatLng(lat, lng);
		/*
		 * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
		 * 则需要在离线地图下载和使用地图页面都进行路径设置
		 */
		// Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
//		 MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState); // 此方法必须重写
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
//		Button clearMap = (Button) findViewById(R.id.clearMap);
//		clearMap.setOnClickListener(this);
////		Button resetMap = (Button) findViewById(R.id.resetMap);
//		resetMap.setOnClickListener(this);
		if (aMap == null) {
			aMap = mapView.getMap();
			addMarkersToMap();// 往地图上添加marker
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 在地图上添加marker
	 */
	private void addMarkersToMap() {
		markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.position(latlng)
//				.title("标题")
				.snippet(address)
				.draggable(true);
		marker = aMap.addMarker(markerOption);
		//改变可视区域为指定位置
		//CameraPosition4个参数分别为位置，缩放级别，目标可视区域倾斜度，可视区域指向方向（正北逆时针算起，0-360）
		CameraUpdate cameraUpdate= CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng,8,0,30));
		aMap.moveCamera(cameraUpdate);//地图移向指定区域
		marker.showInfoWindow();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/**
		 * 清空地图上所有已经标注的marker
		 */
//		case R.id.clearMap:
//			if (aMap != null) {
//				aMap.clear();
//			}
//			break;
//		/**
//		 * 重新标注所有的marker
//		 */
//		case R.id.resetMap:
//			if (aMap != null) {
//				aMap.clear();
//				addMarkersToMap();
//			}
//			break;
		default:
			break;
		}
	}


}
