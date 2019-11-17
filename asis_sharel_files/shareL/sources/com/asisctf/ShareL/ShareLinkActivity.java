package com.asisctf.ShareL;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.asisctf.ShareL.models.ShareLink;
import com.asisctf.ShareL.models.sShareLink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareLinkActivity extends AppCompatActivity {
    int privat = 0;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_share_link);
        final EditText editText = (EditText) findViewById(R.id.name);
        final EditText editText2 = (EditText) findViewById(R.id.link);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.privat);
        ((Button) findViewById(R.id.send)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    ShareLinkActivity.this.privat = 1;
                }
                if (editText.getText().length() != 0 && editText2.getText().length() != 0) {
                    ShareLinkActivity.this.shareLinkRequest(editText.getText().toString(), editText2.getText().toString(), ShareLinkActivity.this.privat);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void shareLinkRequest(String str, String str2, int i) {
        APIInterFace aPIInterFace = (APIInterFace) new APIClient(getApplicationContext()).getClient().create(APIInterFace.class);
        long currentTimeMillis = System.currentTimeMillis() % 1000;
        sShareLink ssharelink = new sShareLink();
        ssharelink.link = str2;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_");
        sb.append(currentTimeMillis);
        ssharelink.link_name = sb.toString();
        ssharelink.pr = i;
        aPIInterFace.shareLink(Utils.getAuth(getApplicationContext()), Utils.getDeviceId(getApplicationContext()), ssharelink).enqueue(new Callback<ShareLink>() {
            public void onResponse(Call<ShareLink> call, Response<ShareLink> response) {
                ShareLink shareLink = (ShareLink) response.body();
                try {
                    if (shareLink.code == 200) {
                        Toast.makeText(ShareLinkActivity.this.getApplicationContext(), "success", 0).show();
                        Toast.makeText(ShareLinkActivity.this.getApplicationContext(), shareLink.data.msg, 1).show();
                        return;
                    }
                    Context applicationContext = ShareLinkActivity.this.getApplicationContext();
                    StringBuilder sb = new StringBuilder();
                    sb.append("code:  ");
                    sb.append(shareLink.code);
                    Toast.makeText(applicationContext, sb.toString(), 0).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ShareLink> call, Throwable th) {
                Toast.makeText(ShareLinkActivity.this.getApplicationContext(), th.getMessage(), 0).show();
            }
        });
    }
}
