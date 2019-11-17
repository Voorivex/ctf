package com.asisctf.ShareL;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.asisctf.ShareL.models.SharePLinkUser;
import com.asisctf.ShareL.models.sSharePLink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareLink2UserActivity extends AppCompatActivity {
    EditText shareuserId;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_share_link2_user);
        this.shareuserId = (EditText) findViewById(R.id.uid);
        ((Button) findViewById(R.id.send)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ShareLink2UserActivity.this.shareuserId.getText().length() != 0) {
                    ShareLink2UserActivity.this.share2User();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void share2User() {
        APIInterFace aPIInterFace = (APIInterFace) new APIClient(getApplicationContext()).getClient().create(APIInterFace.class);
        SharedPreferences sharedPreferences = getSharedPreferences("register", 0);
        String string = sharedPreferences.getString("auth", null);
        int i = sharedPreferences.getInt("uid", 0);
        int i2 = sharedPreferences.getInt("rnd", 0);
        sSharePLink sshareplink = new sSharePLink();
        sshareplink.link_name = getIntent().getStringExtra("lname");
        sshareplink.random_number = i2;
        sshareplink.user_id = i;
        sshareplink.share_user_id = Integer.parseInt(this.shareuserId.getText().toString());
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(".");
        sb.append(string);
        aPIInterFace.sharePLink(sb.toString(), Utils.getDeviceId(getApplicationContext()), sshareplink).enqueue(new Callback<SharePLinkUser>() {
            public void onResponse(Call<SharePLinkUser> call, Response<SharePLinkUser> response) {
                SharePLinkUser sharePLinkUser = (SharePLinkUser) response.body();
                try {
                    if (sharePLinkUser.code == 200) {
                        Toast.makeText(ShareLink2UserActivity.this.getApplicationContext(), sharePLinkUser.data.msg, 0).show();
                        return;
                    }
                    Context applicationContext = ShareLink2UserActivity.this.getApplicationContext();
                    StringBuilder sb = new StringBuilder();
                    sb.append("code:  ");
                    sb.append(sharePLinkUser.code);
                    Toast.makeText(applicationContext, sb.toString(), 0).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<SharePLinkUser> call, Throwable th) {
                Toast.makeText(ShareLink2UserActivity.this.getApplicationContext(), th.getMessage(), 0).show();
            }
        });
    }
}
