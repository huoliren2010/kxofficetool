package com.kx.officetool.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.kx.officetool.DailySignActivity;
import com.kx.officetool.MainApplication;
import com.kx.officetool.R;
import com.kx.officetool.baiduservice.LocationService;
import com.kx.officetool.infos.DailySignInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignFillFragment extends Fragment implements View.OnClickListener {
    private String mCurrentLocation, mCurrentTime;
    private TextView mTvTimeAddress;
    private EditText mEditText;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_fill, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View viewRoot = getView();
        viewRoot.findViewById(R.id.iv_back).setOnClickListener(this);
        viewRoot.findViewById(R.id.tv_ok).setOnClickListener(this);
        mTvTimeAddress = (TextView) viewRoot.findViewById(R.id.tv_datetime_and_address);
        mEditText = (EditText) viewRoot.findViewById(R.id.editText);
        mTvTimeAddress.setText(String.format("%s  %s", getCurrentDateTime(), TextUtils.isEmpty(mCurrentLocation) ? "" : mCurrentLocation));
        viewRoot.findViewById(R.id.tv_trim).setOnClickListener(this);
    }

    public String getCurrentDateTime() {
        mCurrentTime = new SimpleDateFormat("MM-dd hh:mm").format(new Date(System.currentTimeMillis()));
        return mCurrentTime;
    }

    public void setLocation(String address) {
        mCurrentLocation = address;
        if (mTvTimeAddress != null)
            mTvTimeAddress.setText(getCurrentDateTime() + "\n" + (TextUtils.isEmpty(address) ? "" : address));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
            case R.id.tv_trim:
                ((DailySignActivity) getActivity()).showBdLocationFragment();
                break;
            case R.id.tv_ok:
                ((DailySignActivity) getActivity()).addDailySignInfo(new DailySignInfo(mEditText.getText().toString(), mCurrentTime, mCurrentLocation));
                getActivity().onBackPressed();
                break;
        }
    }


    private LocationService locationService;

    @Override
    public void onStart() {
        super.onStart();
        locationService = ((MainApplication) getActivity().getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();// 定位SDK
    }

    @Override
    public void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String address = (String) msg.obj;
            setLocation(address);
        }
    };

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    public BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                Address address = location.getAddress();
                String addrStr = null;
                if (address != null) {
                    addrStr = address.address;
                    ((DailySignActivity)getActivity()).mMyAddress.setAddress(addrStr);
                    Message message = mHandler.obtainMessage();
                    message.obj = addrStr;
                    mHandler.sendMessage(message);
                }
                 List<String> mListLocations = new ArrayList<>();
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = location.getPoiList().get(i);
                        mListLocations.add(poi.getName());
                    }
                    ((DailySignActivity)getActivity()).mMyAddress.setListNearBy(mListLocations);
                }
                if (mListLocations.size() > 0 && !TextUtils.isEmpty(addrStr)) {
                    locationService.unregisterListener(mListener); //注销掉监听
                    locationService.stop(); //停止定位服务
                }
                /*StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                *//**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 *//*
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
           }
                Log.e("log", sb.toString());*/
            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }
    };

}
