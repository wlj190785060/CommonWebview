package com.commonwebview.commonwebview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    private TextView tv_click,tv_click_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        tv_click = findViewById(R.id.tv_click);
        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TextActivity.this, MainActivity.class).putExtra("key",1));
            }
        });
        tv_click_2 = findViewById(R.id.tv_click2);
        tv_click_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TextActivity.this, MainActivity.class).putExtra("key",2));
            }
        });

    }

}
