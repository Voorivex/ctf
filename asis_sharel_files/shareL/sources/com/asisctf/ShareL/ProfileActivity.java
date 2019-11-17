package com.asisctf.ShareL;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.asisctf.ShareL.models.UserProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView links;
    TextView uId;
    TextView uType;
    int userId = -100;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_profile);
        ProfileRequest();
        this.links = (TextView) findViewById(R.id.links);
        this.uId = (TextView) findViewById(R.id.user_id);
        this.uType = (TextView) findViewById(R.id.user_type);
        ((Button) findViewById(R.id.uplink)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ProfileActivity.this.userId != -100) {
                    Intent intent = new Intent(ProfileActivity.this.getApplicationContext(), UserPublicLinkActivity.class);
                    intent.putExtra("uid", ProfileActivity.this.userId);
                    ProfileActivity.this.startActivity(intent);
                }
            }
        });
    }

    private void ProfileRequest() {
        APIInterFace aPIInterFace = (APIInterFace) new APIClient(getApplicationContext()).getClient().create(APIInterFace.class);
        SharedPreferences sharedPreferences = getSharedPreferences("register", 0);
        String string = sharedPreferences.getString("auth", null);
        String str = "uid";
        int i = sharedPreferences.getInt(str, 0);
        int intExtra = getIntent().getIntExtra(str, 0);
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(".");
        sb.append(string);
        aPIInterFace.getUserProfile(sb.toString(), Utils.getDeviceId(getApplicationContext()), intExtra).enqueue(new Callback<UserProfile>() {
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                String str = "";
                UserProfile userProfile = (UserProfile) response.body();
                try {
                    if (userProfile.code == 200) {
                        TextView textView = ProfileActivity.this.links;
                        StringBuilder sb = new StringBuilder();
                        sb.append(userProfile.data.links);
                        sb.append(str);
                        textView.setText(sb.toString());
                        TextView textView2 = ProfileActivity.this.uId;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(userProfile.data.user_id);
                        sb2.append(str);
                        textView2.setText(sb2.toString());
                        ProfileActivity.this.userId = userProfile.data.user_id;
                        ProfileActivity.this.uType.setText(userProfile.data.user_type);
                        return;
                    }
                    Context applicationContext = ProfileActivity.this.getApplicationContext();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("code:  ");
                    sb3.append(userProfile.code);
                    Toast.makeText(applicationContext, sb3.toString(), 0).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<UserProfile> call, Throwable th) {
                Toast.makeText(ProfileActivity.this.getApplicationContext(), th.getMessage(), 0).show();
            }
        });
    }
}
