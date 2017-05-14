package com.kx.officetool;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hh.timeselector.timeutil.datedialog.DateListener;
import com.hh.timeselector.timeutil.datedialog.TimeConfig;
import com.hh.timeselector.timeutil.datedialog.TimeSelectorDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateApprovalActivity extends BaseActivity implements View.OnClickListener {
    Spinner mSpinnerType;
    TextView startTime, endTime, timeLength;
    EditText message;
    Date startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_approval);
        mSpinnerType = (Spinner) findViewById(R.id.spinner_type);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.starttime_root).setOnClickListener(this);
        findViewById(R.id.endtime_root).setOnClickListener(this);
        startTime = (TextView) findViewById(R.id.starttime);
        endTime = (TextView) findViewById(R.id.endtime);
        timeLength = (TextView) findViewById(R.id.length);
        message = (EditText) findViewById(R.id.message);
        startDate = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh");
        String strStartTime = simpleDateFormat.format(startDate);
        startTime.setText(strStartTime);
        endDate = new Date(startDate.getTime());
        endDate.setHours(endDate.getHours() + 8);
        String strEndTime = simpleDateFormat.format(endDate);
        endTime.setText(strEndTime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.starttime_root:
                showTimePickerDialog(startTime);
                break;
            case R.id.endtime_root:
                showTimePickerDialog(endTime);
                break;
        }
    }

    public void showTimePickerDialog(final TextView textView) {
        TimeSelectorDialog dialog = new TimeSelectorDialog(this);
        //设置标题
        dialog.setTimeTitle("选择时间:");
        //显示类型
        dialog.setIsShowtype(TimeConfig.YEAR_MONTH_DAY_HOUR);
        //默认时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh");
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        dialog.setCurrentDate(textView.getText().toString());
        //隐藏清除按钮
        dialog.setEmptyIsShow(false);
        //设置起始时间
        dialog.setStartYear(2017);
        dialog.setDateListener(new DateListener() {
            @Override
            public void onReturnDate(String time, int year, int month, int day, int hour, int minute, int isShowType) {
                Toast.makeText(CreateApprovalActivity.this, time, Toast.LENGTH_LONG).show();
                textView.setText(time);
                if (textView.getId() == R.id.starttime) {
                    startDate = new Date(year, month, day, hour, 0);
                } else endDate = new Date(year, month, day, hour, 0);

            }

            @Override
            public void onReturnDate(String empty) {
                Toast.makeText(CreateApprovalActivity.this, empty, Toast.LENGTH_LONG).show();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                timeLength.setText(String.valueOf(endDate.getHours() - startDate.getHours()));
            }
        });
        dialog.show();
    }
}
