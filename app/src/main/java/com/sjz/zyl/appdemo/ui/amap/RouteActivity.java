package com.sjz.zyl.appdemo.ui.amap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.WalkRouteOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.utils.AMapUtil;
import com.sjz.zyl.appdemo.utils.ToastUtil;

import org.kymjs.kjframe.ui.ViewInject;


public class RouteActivity extends Activity implements OnMapClickListener,
		OnMarkerClickListener, OnInfoWindowClickListener, InfoWindowAdapter, OnRouteSearchListener, LocationSource,
		AMapLocationListener {
	private AMap aMap;
	private MapView mapView;
	private Context mContext;
	private RouteSearch mRouteSearch;
	private DriveRouteResult mDriveRouteResult;
	private BusRouteResult mBusRouteResult;
	private WalkRouteResult mWalkRouteResult;
	private LatLonPoint mStartPoint = new LatLonPoint(39.942295, 116.335891);//起点，116.335891,39.942295
	private LatLonPoint mEndPoint = new LatLonPoint(39.995576, 116.481288);//终点，116.481288,39.995576
	private LatLonPoint mStartPoint_bus = new LatLonPoint(40.818311, 111.670801);//起点，111.670801,40.818311
	private LatLonPoint mEndPoint_bus = new LatLonPoint(44.433942, 125.184449);//终点，
	private String mCurrentCityName = "石家庄";
	private final int ROUTE_TYPE_BUS = 1;
	private final int ROUTE_TYPE_DRIVE = 2;
	private final int ROUTE_TYPE_WALK = 3;
	private final int ROUTE_TYPE_CROSSTOWN = 4;
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	private LinearLayout mBusResultLayout;
	private RelativeLayout mBottomLayout;
	private TextView mRotueTimeDes, mRouteDetailDes;
	private ImageView mBus;
	private ImageView mDrive;
	private ImageView mWalk;
	private ListView mBusResultList;
	private ProgressDialog progDialog = null;// 搜索时进度条
	private float lat;
	private float lng;
	private LatLng latlng;
	private String address;
//	private Boolean flag;
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.route_activity);
		mContext = this.getApplicationContext();
		mapView = (MapView) findViewById(R.id.route_map);
		mapView.onCreate(bundle);// 此方法必须重写
		Intent intent = getIntent();
		lat= intent.getFloatExtra("lat", 39.91746f);
		lng = intent.getFloatExtra("lng",116.396481f); // 没有输入值默认为0
		address=intent.getStringExtra("lacation");
		latlng= new LatLng(lat, lng);
		mEndPoint=new LatLonPoint(lat,lng);
		mEndPoint_bus=new LatLonPoint(lat,lng);
		init();
//		getIntentData();
		setfromandtoMarker();

		
	}
	
	private void setfromandtoMarker() {

		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.addMarker(new MarkerOptions());
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//		Marker marker= aMap.addMarker(new MarkerOptions()
//		.position(latlng)
//		.icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
//		marker.showInfoWindow();
		MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.position(latlng)
//				.title("标题")
				.snippet(address)
				.draggable(true);
		Marker	marker = aMap.addMarker(markerOption);
		//改变可视区域为指定位置
		//CameraPosition4个参数分别为位置，缩放级别，目标可视区域倾斜度，可视区域指向方向（正北逆时针算起，0-360）
		CameraUpdate cameraUpdate= CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng,8,0,30));
		aMap.moveCamera(cameraUpdate);//地图移向指定区域
		marker.showInfoWindow();

	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setfromandtoMarker();
		}
		registerListener();
		mRouteSearch = new RouteSearch(this);
		mRouteSearch.setRouteSearchListener(this);
		mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
		mBusResultLayout = (LinearLayout) findViewById(R.id.bus_result);
		mRotueTimeDes = (TextView) findViewById(R.id.firstline);
		mRouteDetailDes = (TextView) findViewById(R.id.secondline);
		mDrive = (ImageView)findViewById(R.id.route_drive);
		mBus = (ImageView)findViewById(R.id.route_bus);
		mWalk = (ImageView)findViewById(R.id.route_walk);
		mBusResultList = (ListView) findViewById(R.id.bus_result_list);

		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			ViewInject.toast("请打开定位权限");
		}
	}

	/**
	 * 注册监听
	 */
	private void registerListener() {
		aMap.setOnMapClickListener(RouteActivity.this);
		aMap.setOnMarkerClickListener(RouteActivity.this);
		aMap.setOnInfoWindowClickListener(RouteActivity.this);
		aMap.setInfoWindowAdapter(RouteActivity.this);
		
	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void onBusClick(View view) {
		searchRouteResult(ROUTE_TYPE_BUS, RouteSearch.BusDefault);
		mDrive.setImageResource(R.drawable.route_drive_normal);
		mBus.setImageResource(R.drawable.route_bus_select);
		mWalk.setImageResource(R.drawable.route_walk_normal);
		mapView.setVisibility(View.GONE);
		mBusResultLayout.setVisibility(View.VISIBLE);
	}
	
	public void onDriveClick(View view) {
		searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
		mDrive.setImageResource(R.drawable.route_drive_select);
		mBus.setImageResource(R.drawable.route_bus_normal);
		mWalk.setImageResource(R.drawable.route_walk_normal);
		mapView.setVisibility(View.VISIBLE);
		mBusResultLayout.setVisibility(View.GONE);
	}

	public void onWalkClick(View view) {
		searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
		mDrive.setImageResource(R.drawable.route_drive_normal);
		mBus.setImageResource(R.drawable.route_bus_normal);
		mWalk.setImageResource(R.drawable.route_walk_select);
		mapView.setVisibility(View.VISIBLE);
		mBusResultLayout.setVisibility(View.GONE);
	}
	
	public void onCrosstownBusClick(View view) {
		searchRouteResult(ROUTE_TYPE_CROSSTOWN, RouteSearch.BusDefault);
		mDrive.setImageResource(R.drawable.route_drive_normal);
		mBus.setImageResource(R.drawable.route_bus_normal);
		mWalk.setImageResource(R.drawable.route_walk_normal);
		mapView.setVisibility(View.GONE);
		mBusResultLayout.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 开始搜索路径规划方案
	 */
	public void searchRouteResult(int routeType, int mode) {
		if (mStartPoint == null) {
			ToastUtil.show(mContext, "起点未设置");
			return;
		}
		if (mEndPoint == null) {
			ToastUtil.show(mContext, "终点未设置");
		}
		showProgressDialog();
		final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
				mStartPoint, mEndPoint);
		if (routeType == ROUTE_TYPE_BUS) {// 公交路径规划
			BusRouteQuery query = new BusRouteQuery(fromAndTo, mode,
					mCurrentCityName, 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
			mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
		} else if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
			DriveRouteQuery query = new DriveRouteQuery(fromAndTo, mode, null,
					null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
			mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
		} else if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
			WalkRouteQuery query = new WalkRouteQuery(fromAndTo, mode);
			mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
		} else if (routeType == ROUTE_TYPE_CROSSTOWN) {
			RouteSearch.FromAndTo fromAndTo_bus = new RouteSearch.FromAndTo(
					mStartPoint_bus, mEndPoint_bus);
			BusRouteQuery query = new BusRouteQuery(fromAndTo_bus, mode,
					"呼和浩特市", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
			query.setCityd("农安县");
			mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
		}
	}

	@Override
	public void onBusRouteSearched(BusRouteResult result, int errorCode) {
		dissmissProgressDialog();
		mBottomLayout.setVisibility(View.GONE);
		aMap.clear();// 清理地图上的所有覆盖物
		if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getPaths() != null) {
				if (result.getPaths().size() > 0) {
					mBusRouteResult = result;
					BusResultListAdapter mBusResultListAdapter = new BusResultListAdapter(mContext, mBusRouteResult);
					mBusResultList.setAdapter(mBusResultListAdapter);		
				} else if (result != null && result.getPaths() == null) {
					ToastUtil.show(mContext, R.string.no_result);
				}
			} else {
				ToastUtil.show(mContext, R.string.no_result);
			}
		} else {
			ToastUtil.showerror(this.getApplicationContext(), errorCode);
		}
	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
		dissmissProgressDialog();
		aMap.clear();// 清理地图上的所有覆盖物
		if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getPaths() != null) {
				if (result.getPaths().size() > 0) {
					mDriveRouteResult = result;
					final DrivePath drivePath = mDriveRouteResult.getPaths()
							.get(0);
					DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(
							mContext, aMap, drivePath,
							mDriveRouteResult.getStartPos(),
							mDriveRouteResult.getTargetPos(), null);
					drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
					drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
					drivingRouteOverlay.removeFromMap();
					drivingRouteOverlay.addToMap();
					drivingRouteOverlay.zoomToSpan();
					mBottomLayout.setVisibility(View.VISIBLE);
					int dis = (int) drivePath.getDistance();
					int dur = (int) drivePath.getDuration();
					String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
					mRotueTimeDes.setText(des);
					mRouteDetailDes.setVisibility(View.VISIBLE);
					int taxiCost = (int) mDriveRouteResult.getTaxiCost();
					mRouteDetailDes.setText("打车约"+taxiCost+"元");
					mBottomLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mContext,
									DriveRouteDetailActivity.class);
							intent.putExtra("drive_path", drivePath);
							intent.putExtra("drive_result",
									mDriveRouteResult);
							startActivity(intent);
						}
					});
				} else if (result != null && result.getPaths() == null) {
					ToastUtil.show(mContext, R.string.no_result);
				}

			} else {
				ToastUtil.show(mContext, R.string.no_result);
			}
		} else {
			ToastUtil.showerror(this.getApplicationContext(), errorCode);
		}
		
		
	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
		dissmissProgressDialog();
		aMap.clear();// 清理地图上的所有覆盖物
		if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getPaths() != null) {
				if (result.getPaths().size() > 0) {
					mWalkRouteResult = result;
					final WalkPath walkPath = mWalkRouteResult.getPaths()
							.get(0);
					WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
							this, aMap, walkPath,
							mWalkRouteResult.getStartPos(),
							mWalkRouteResult.getTargetPos());
					walkRouteOverlay.removeFromMap();
					walkRouteOverlay.addToMap();
					walkRouteOverlay.zoomToSpan();
					mBottomLayout.setVisibility(View.VISIBLE);
					int dis = (int) walkPath.getDistance();
					int dur = (int) walkPath.getDuration();
					String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
					mRotueTimeDes.setText(des);
					mRouteDetailDes.setVisibility(View.GONE);
					mBottomLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mContext,
									WalkRouteDetailActivity.class);
							intent.putExtra("walk_path", walkPath);
							intent.putExtra("walk_result",
									mWalkRouteResult);
							startActivity(intent);
						}
					});
				} else if (result != null && result.getPaths() == null) {
					ToastUtil.show(mContext, R.string.no_result);
				}

			} else {
				ToastUtil.show(mContext, R.string.no_result);
			}
		} else {
			ToastUtil.showerror(this.getApplicationContext(), errorCode);
		}
	}
	

	/**
	 * 显示进度框
	 */
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		    progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		    progDialog.setIndeterminate(false);
		    progDialog.setCancelable(true);
		    progDialog.setMessage("正在搜索");
		    progDialog.show();
	    }

	/**
	 * 隐藏进度框
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
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

	@Override
	public void onRideRouteSearched(RideRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
				mStartPoint_bus=new LatLonPoint(amapLocation.getLatitude(),amapLocation.getLongitude());
				mStartPoint=new LatLonPoint(amapLocation.getLatitude(),amapLocation.getLongitude());
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
				Log.e("AmapErr",errText);
			}
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			//设置定位监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
			//设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

}

