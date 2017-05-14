package com.kx.officetool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HoleActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_post_hol).setOnClickListener(this);
        findViewById(R.id.need_hol).setOnClickListener(this);
        findViewById(R.id.my_hol).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_post_hol:
                startActivity(new Intent(this, CreateApprovalActivity.class));
                break;
            case R.id.need_hol:
                break;
            case R.id.my_hol:
                break;
        }
    }
}
