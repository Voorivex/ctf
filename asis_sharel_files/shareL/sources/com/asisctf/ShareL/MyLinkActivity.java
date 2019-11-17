package com.asisctf.ShareL;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.asisctf.ShareL.models.MyLinks;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLinkActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_my_links);
        UserLinks();
    }

    private void UserLinks() {
        ((APIInterFace) new APIClient(getApplicationContext()).getClient().create(APIInterFace.class)).MyLinks(Utils.getAuth(getApplicationContext()), Utils.getDeviceId(getApplicationContext())).enqueue(new Callback<MyLinks>() {
            public void onResponse(Call<MyLinks> call, Response<MyLinks> response) {
                MyLinks myLinks = (MyLinks) response.body();
                try {
                    if (myLinks.code == 200) {
                        if (myLinks.data.size() == 0) {
                            Toast.makeText(MyLinkActivity.this.getApplicationContext(), "EMPTY LIST !!", 1).show();
                        }
                        RecyclerView recyclerView = (RecyclerView) MyLinkActivity.this.findViewById(R.id.mylink_recycler);
                        MyLinkAdapter myLinkAdapter = new MyLinkAdapter(MyLinkActivity.this.getApplicationContext(), myLinks.data);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MyLinkActivity.this.getApplicationContext()));
                        recyclerView.setAdapter(myLinkAdapter);
                        return;
                    }
                    Context applicationContext = MyLinkActivity.this.getApplicationContext();
                    StringBuilder sb = new StringBuilder();
                    sb.append("code:  ");
                    sb.append(myLinks.code);
                    Toast.makeText(applicationContext, sb.toString(), 0).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<MyLinks> call, Throwable th) {
                Toast.makeText(MyLinkActivity.this.getApplicationContext(), th.getMessage(), 0).show();
            }
        });
    }
}
