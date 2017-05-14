package com.kx.officetool;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kx.officetool.infos.Notice;

public class NoticeDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        Notice notice = (Notice) getIntent().getSerializableExtra("notice");
        TextView title = (TextView) findViewById(R.id.title);
        TextView message = (TextView) findViewById(R.id.message);
        TextView time = (TextView) findViewById(R.id.time);
        title.setText(notice.getTitle());
        message.setText(notice.getMessage());
        time.setText(notice.getTime());
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
