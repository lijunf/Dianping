package com.maiziedu.dianping_client.fragment;

import java.io.IOException;
import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.maiziedu.dianping_client.R;
import com.maiziedu.dianping_client.utils.SharedUtils;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//首页
public class FragmentHome extends Fragment implements LocationListener{
	@ViewInject(R.id.index_top_city)
	private TextView topCity;
	private String cityName;//当前城市名称
	private LocationManager locationManager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_index, null);
		ViewUtils.inject(this, view);
		//获取数据并且显示
		topCity.setText(SharedUtils.getCityName(getActivity()));
		return view;
	}
	@Override
	public void onStart() {
		super.onStart();
		//检查当前的gps模块
		checkGPSIsOpen();
	}
	//检查是否打开GPS
	private void checkGPSIsOpen(){
		//获取当前的LocationManager对象
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!isOpen) {
			//进入gps的设置页面
			Intent intent = new Intent();
			intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivityForResult(intent, 0);
		}
		//开始定位
		startLocation();
	}
	//使用gps定位的方法
	private void startLocation(){
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);
	}
	//接受并且处理消息
	private Handler handler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			if (msg.what==1) {
				topCity.setText(cityName);
			}
			return false;
		}
	});
	//获取对应位置的经纬度并且定位城市
	private void updateWithNewLocation(Location location){
		double lat = 0.0,lng = 0.0;
		if (location!=null) {
			lat = location.getLatitude();
			lng = location.getLongitude();
			Log.i("TAG", "经度是"+lat+",纬度是："+lng);
		}else{
			cityName = "无法获取城市信息";
		}
		//通过经纬度来获取地址，由于地址会有多个，这个和经纬度精确度有关，本实例中定义了最大的返回数2.即在集合对象中有两个值
		List<Address> list = null;
		Geocoder ge = new Geocoder(getActivity());
		try {
			list = ge.getFromLocation(lat, lng, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list!=null && list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				Address ad = list.get(i);
				cityName = ad.getLocality();//获取城市
			}
		}
		//发送空消息
		handler.sendEmptyMessage(1);
	}
	//位置信息更改执行的方法
	@Override
	public void onLocationChanged(Location location) {
		//更新当前的位置信息
		updateWithNewLocation(location);
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//保存城市
		SharedUtils.putCityName(getActivity(), cityName);
		//停止定位
		stopLocation();
	}
	//停止定位
	private  void stopLocation(){
		locationManager.removeUpdates(this);
	}
}
