package com.asisctf.ShareL;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.asisctf.ShareL.models.Top10Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopTenUserActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_top_ten_user);
        TopUserRequset();
    }

    private void TopUserRequset() {
        ((APIInterFace) new APIClient(getApplicationContext()).getClient().create(APIInterFace.class)).top10user(Utils.getAuth(getApplicationContext()), Utils.getDeviceId(getApplicationContext())).enqueue(new Callback<Top10Users>() {
            public void onResponse(Call<Top10Users> call, Response<Top10Users> response) {
                Top10Users top10Users = (Top10Users) response.body();
                try {
                    if (top10Users.code == 200) {
                        if (top10Users.data.size() == 0) {
                            Toast.makeText(TopTenUserActivity.this.getApplicationContext(), "EMPTY LIST !!", 1).show();
                        }
                        RecyclerView recyclerView = (RecyclerView) TopTenUserActivity.this.findViewById(R.id.topuser_recycler);
                        TopUserAdapter topUserAdapter = new TopUserAdapter(TopTenUserActivity.this.getApplicationContext(), top10Users.data);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(TopTenUserActivity.this.getApplicationContext()));
                        recyclerView.setAdapter(topUserAdapter);
                        return;
                    }
                    Context applicationContext = TopTenUserActivity.this.getApplicationContext();
                    StringBuilder sb = new StringBuilder();
                    sb.append("code:  ");
                    sb.append(top10Users.code);
                    Toast.makeText(applicationContext, sb.toString(), 0).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<Top10Users> call, Throwable th) {
                Toast.makeText(TopTenUserActivity.this.getApplicationContext(), th.getMessage(), 0).show();
            }
        });
    }
}
