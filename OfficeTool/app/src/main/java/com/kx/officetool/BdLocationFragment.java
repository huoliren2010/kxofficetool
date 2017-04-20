package com.kx.officetool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.kx.officetool.infos.MyAddress;

import java.util.List;

public class BdLocationFragment extends Fragment implements View.OnClickListener {
    MyAddress mMyAddress = null;
    MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    // 定位相关
    LocationClient mLocClient;

    private MyLocationConfiguration.LocationMode mCurrentMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bdlocation, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View viewRoot = getView();
        viewRoot.findViewById(R.id.iv_back).setOnClickListener(this);
        ListView mListView = (ListView) viewRoot.findViewById(R.id.listview);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((DailySignActivity)getActivity()).mMyAddress.setAddress((String) mListAdapter.getItem(position));
                getActivity().onBackPressed();
            }
        });
        // 地图初始化
        mMapView = (MapView) viewRoot.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 开启室内图
        mBaiduMap.setIndoorEnable(true);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(3000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true,
                null));
    }

    boolean isFirstLoc = true;
    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    };


    BaseAdapter mListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            if (mMyAddress == null) return 0;
            return 1 + mMyAddress.getListNearBy().size();
        }

        @Override
        public Object getItem(int position) {
            String address = mMyAddress.getAddress();
            if(position == 0 && TextUtils.isEmpty(address)){
                address = mMyAddress.getListNearBy().get(position);
            }else if(position != 0){
                address = mMyAddress.getListNearBy().get(position-1);
            }
            return address;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_myaddress_item, null);
                viewHolder.mTitle = (TextView) convertView.findViewById(R.id.title);
                viewHolder.mDesc = (TextView) convertView.findViewById(R.id.desc);
                convertView.setTag(viewHolder);
            } else viewHolder = (ViewHolder) convertView.getTag();
            if (TextUtils.isEmpty(mMyAddress.getAddress())) {
                List<String> listNearBy = mMyAddress.getListNearBy();
                viewHolder.mTitle.setText(listNearBy.get(position));
                viewHolder.mDesc.setVisibility(View.GONE);
            } else {
                if (position == 0) {
                    viewHolder.mTitle.setText("[位置]");
                    viewHolder.mDesc.setText(mMyAddress.getAddress());
                } else {
                    List<String> listNearBy = mMyAddress.getListNearBy();
                    viewHolder.mTitle.setText(listNearBy.get(position - 1));
                    viewHolder.mDesc.setText("在 " + mMyAddress.getAddress() + " 附近");
                }

            }
            return convertView;
        }
    };

    public void setMyAddress(MyAddress mMyAddress) {
        this.mMyAddress = mMyAddress;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
        }
    }

    class ViewHolder {
        TextView mTitle, mDesc;
    }


    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
