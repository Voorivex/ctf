package com.asisctf.ShareL;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.asisctf.ShareL.models.UsrPLink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPublicLinkActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_user_public_link);
        UserPubLinks();
    }

    private void UserPubLinks() {
        ((APIInterFace) new APIClient(getApplicationContext()).getClient().create(APIInterFace.class)).UserPubLink(Utils.getAuth(getApplicationContext()), Utils.getDeviceId(getApplicationContext()), getIntent().getIntExtra("uid", 0)).enqueue(new Callback<UsrPLink>() {
            public void onResponse(Call<UsrPLink> call, Response<UsrPLink> response) {
                UsrPLink usrPLink = (UsrPLink) response.body();
                try {
                    if (usrPLink.code == 200) {
                        if (usrPLink.data.size() == 0) {
                            Toast.makeText(UserPublicLinkActivity.this.getApplicationContext(), "EMPTY LIST !!", 1).show();
                        }
                        RecyclerView recyclerView = (RecyclerView) UserPublicLinkActivity.this.findViewById(R.id.uplink_recycler);
                        UserPubLinkAdapter userPubLinkAdapter = new UserPubLinkAdapter(UserPublicLinkActivity.this.getApplicationContext(), usrPLink.data);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(UserPublicLinkActivity.this.getApplicationContext()));
                        recyclerView.setAdapter(userPubLinkAdapter);
                        return;
                    }
                    Context applicationContext = UserPublicLinkActivity.this.getApplicationContext();
                    StringBuilder sb = new StringBuilder();
                    sb.append("code:  ");
                    sb.append(usrPLink.code);
                    Toast.makeText(applicationContext, sb.toString(), 0).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<UsrPLink> call, Throwable th) {
                Toast.makeText(UserPublicLinkActivity.this.getApplicationContext(), th.getMessage(), 0).show();
            }
        });
    }
}
