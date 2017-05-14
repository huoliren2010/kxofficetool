package com.kx.officetool.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kx.officetool.AppConfig;
import com.kx.officetool.BaseActivity;
import com.kx.officetool.R;
import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.DepartMent;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.DensityUtil;
import com.kx.officetool.utils.SharedPreferencesUtil;

import java.util.List;


public class CompanyDepartMentFragment extends Fragment implements View.OnClickListener {
    ListView mListView = null;
    List<DepartMent> departMentList = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_companydepartment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View rootView = getView();
        rootView.findViewById(R.id.btn_add).setOnClickListener(this);
        if (AppConfig.getInstance().isManager()) {
            rootView.findViewById(R.id.btn_add).setVisibility(View.VISIBLE);
        } else rootView.findViewById(R.id.btn_add).setVisibility(View.GONE);
        CompanyInfo mCompanyInfo = AppConfig.getInstance().mCompanyInfo;
        if (mCompanyInfo != null) {
            departMentList = mCompanyInfo.getDepartment();
        }
        mListView = (ListView) rootView.findViewById(R.id.list);
        mListView.setAdapter(mAdapter);
    }

    BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return departMentList == null ? 0 : departMentList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = null;
            if (convertView == null) {
                textView = new TextView(getActivity());
                int padding = DensityUtil.dp2px(getActivity(), 12.0f);
                textView.setPadding(padding, padding, padding, padding);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                convertView = textView;
                convertView.setTag(textView);
            } else textView = (TextView) convertView.getTag();
            textView.setText(departMentList.get(position).getPartname());
            return convertView;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                showAddDialog();
                break;
            case R.id.btn_cancel:
                if (mAddDialog != null && mAddDialog.isShowing()) mAddDialog.dismiss();
                mAddDialog = null;
                break;
            case R.id.btn_ensure:
                EditText editText = (EditText) mAddDialog.findViewById(R.id.editText);
                final String departMent = editText.getText().toString();
                ((BaseActivity) getActivity()).showProgressDlg();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DepartMent departMent1 = WebService.getInstance().createDepartMent(departMent, AppConfig.getInstance().mCompanyInfo.getId(), AppConfig.getInstance().mUserInfo.getId());
                        if (departMent1 != null) {
                            departMentList.add(departMent1);
                            AppConfig.getInstance().mCompanyInfo.setDepartment(departMentList);
                            SharedPreferencesUtil.putObject(getActivity(), CompanyInfo.KEY_COMPANYINFO_OBJ, AppConfig.getInstance().mCompanyInfo);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((BaseActivity) getActivity()).hideProgressDlg();
                                    mAdapter.notifyDataSetChanged();
                                    if (mAddDialog != null && mAddDialog.isShowing())
                                        mAddDialog.dismiss();
                                    mAddDialog = null;
                                }
                            });
                        }
                    }
                }).start();
                break;
        }
    }

    Dialog mAddDialog = null;

    private void showAddDialog() {
        if (mAddDialog == null) {
            mAddDialog = new Dialog(getActivity());
        }
        mAddDialog.setContentView(R.layout.layout_dialog_adddepartment);
        mAddDialog.findViewById(R.id.btn_cancel).setOnClickListener(this);
        mAddDialog.findViewById(R.id.btn_ensure).setOnClickListener(this);
        mAddDialog.show();
    }
}
