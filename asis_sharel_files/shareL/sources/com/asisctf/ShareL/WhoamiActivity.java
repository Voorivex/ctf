package com.asisctf.ShareL;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.asisctf.ShareL.models.Whoami;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WhoamiActivity extends AppCompatActivity {
    TextView links;
    TextView uId;
    TextView uType;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_whoami);
        whoAmiRequest();
        this.links = (TextView) findViewById(R.id.links);
        this.uId = (TextView) findViewById(R.id.user_id);
        this.uType = (TextView) findViewById(R.id.user_type);
    }

    private void whoAmiRequest() {
        ((APIInterFace) new APIClient(getApplicationContext()).getClient().create(APIInterFace.class)).whoami(Utils.getAuth(getApplicationContext()), Utils.getDeviceId(getApplicationContext())).enqueue(new Callback<Whoami>() {
            public void onResponse(Call<Whoami> call, Response<Whoami> response) {
                String str = "";
                Whoami whoami = (Whoami) response.body();
                try {
                    if (whoami.code == 200) {
                        TextView textView = WhoamiActivity.this.links;
                        StringBuilder sb = new StringBuilder();
                        sb.append(whoami.data.links);
                        sb.append(str);
                        textView.setText(sb.toString());
                        TextView textView2 = WhoamiActivity.this.uId;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(whoami.data.user_id);
                        sb2.append(str);
                        textView2.setText(sb2.toString());
                        WhoamiActivity.this.uType.setText(whoami.data.user_type);
                        return;
                    }
                    Context applicationContext = WhoamiActivity.this.getApplicationContext();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("code:  ");
                    sb3.append(whoami.code);
                    Toast.makeText(applicationContext, sb3.toString(), 0).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<Whoami> call, Throwable th) {
                Toast.makeText(WhoamiActivity.this.getApplicationContext(), th.getMessage(), 0).show();
            }
        });
    }
}
