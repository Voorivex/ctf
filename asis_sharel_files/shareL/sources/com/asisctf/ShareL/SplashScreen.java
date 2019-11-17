package com.asisctf.ShareL;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings.Secure;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.asisctf.ShareL.models.NewDevice;
import com.scottyab.rootbeer.RootBeer;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    public int random;
    TextView state;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash_scren);
        this.state = (TextView) findViewById(R.id.mstate);
        final String string = getSharedPreferences("register", 0).getString("auth", null);
        AnonymousClass1 r2 = new CountDownTimer(2000, 1000) {
            public void onTick(long j) {
            }

            public void onFinish() {
                SharedPreferences sharedPreferences = SplashScreen.this.getSharedPreferences("register", 0);
                if (string != null) {
                    Intent intent = new Intent(SplashScreen.this.getApplicationContext(), MainActivity.class);
                    String str = "uid";
                    intent.putExtra(str, sharedPreferences.getInt(str, 0));
                    intent.addFlags(268468224);
                    SplashScreen.this.startActivity(intent);
                    return;
                }
                SplashScreen.this.registerNewDevice();
            }
        };
        r2.start();
    }

    /* access modifiers changed from: private */
    public void registerNewDevice() {
        Call call;
        APIInterFace aPIInterFace = (APIInterFace) new APIClient(getApplicationContext()).getClient().create(APIInterFace.class);
        this.state.setText("Registering New Device ...\n");
        final String string = Secure.getString(getContentResolver(), "android_id");
        this.random = Utils.getRandom();
        CryptoHandler.getPhonId(string);
        String str = "";
        if (new RootBeer(getApplicationContext()).isRooted()) {
            String sha1Hash = CryptoHandler.sha1Hash(string);
            this.random = new Random().nextInt(859) + 1;
            StringBuilder sb = new StringBuilder();
            sb.append(this.random);
            sb.append(str);
            call = aPIInterFace.regNewDev(sb.toString(), sha1Hash);
        } else {
            String md5 = CryptoHandler.md5(string);
            this.random = new Random().nextInt(900000000) + 100000000;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.random);
            sb2.append(str);
            call = aPIInterFace.regNewDev(md5, sb2.toString());
        }
        final SharedPreferences sharedPreferences = getSharedPreferences("register", 0);
        call.enqueue(new Callback<NewDevice>() {
            public void onResponse(Call<NewDevice> call, Response<NewDevice> response) {
                String str = "uid";
                NewDevice newDevice = (NewDevice) response.body();
                try {
                    if (newDevice.code == 200) {
                        Editor edit = SplashScreen.this.getSharedPreferences("register", 0).edit();
                        edit.putString("auth", newDevice.data.auth_hash);
                        edit.putString("phone_id", string);
                        edit.putInt(str, newDevice.data.user_id);
                        edit.putInt("rnd", SplashScreen.this.random);
                        edit.apply();
                        Intent intent = new Intent(SplashScreen.this.getApplicationContext(), MainActivity.class);
                        intent.putExtra(str, sharedPreferences.getInt(str, 0));
                        intent.addFlags(268468224);
                        SplashScreen.this.startActivity(intent);
                        return;
                    }
                    SplashScreen.this.state.setText("the app cannot register, sorry ...\n");
                    Context applicationContext = SplashScreen.this.getApplicationContext();
                    StringBuilder sb = new StringBuilder();
                    sb.append("code:  ");
                    sb.append(newDevice.code);
                    Toast.makeText(applicationContext, sb.toString(), 1).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SplashScreen.this.getApplicationContext(), "Failed", 0).show();
                }
            }

            public void onFailure(Call<NewDevice> call, Throwable th) {
                Toast.makeText(SplashScreen.this.getApplicationContext(), "Failed", 0).show();
            }
        });
    }
}
