package com.asisctf.ShareL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        Button button = (Button) findViewById(R.id.mylink);
        Button button2 = (Button) findViewById(R.id.sharelink);
        Button button3 = (Button) findViewById(R.id.topuser);
        ((Button) findViewById(R.id.whoami)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), WhoamiActivity.class);
                String str = "uid";
                intent.putExtra(str, MainActivity.this.getIntent().getIntExtra(str, 0));
                MainActivity.this.startActivity(intent);
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), ShareLinkActivity.class);
                String str = "uid";
                intent.putExtra(str, MainActivity.this.getIntent().getIntExtra(str, 0));
                MainActivity.this.startActivity(intent);
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), MyLinkActivity.class);
                String str = "uid";
                intent.putExtra(str, MainActivity.this.getIntent().getIntExtra(str, 0));
                MainActivity.this.startActivity(intent);
            }
        });
        button3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), TopTenUserActivity.class);
                String str = "uid";
                intent.putExtra(str, MainActivity.this.getIntent().getIntExtra(str, 0));
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
