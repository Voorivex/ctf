package com.asisctf.ShareL;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class AllUsersLogActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_all_users_log);
        getLogRequest();
    }

    /* access modifiers changed from: 0000 */
    public void getLogRequest() {
        RequestQueue newRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.BASE_URL);
        sb.append("/logs/user_id/");
        sb.append(Utils.getUid(getApplicationContext()));
        AnonymousClass3 r3 = new JsonObjectRequest(0, sb.toString(), null, new Listener<JSONObject>() {
            public void onResponse(JSONObject jSONObject) {
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("auth_token", Utils.getAuth(AllUsersLogActivity.this.getApplicationContext()));
                hashMap.put("device_id", Utils.getDeviceId(AllUsersLogActivity.this.getApplicationContext()));
                return hashMap;
            }
        };
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Utils.BASE_URL);
        sb2.append("/logs/all/log_id/");
        sb2.append(Utils.getUid(getApplicationContext()));
        new JsonObjectRequest(0, sb2.toString(), null, new Listener<JSONObject>() {
            public void onResponse(JSONObject jSONObject) {
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("auth_token", Utils.getAuth(AllUsersLogActivity.this.getApplicationContext()));
                hashMap.put("device_id", Utils.getDeviceId(AllUsersLogActivity.this.getApplicationContext()));
                return hashMap;
            }
        };
        newRequestQueue.add(r3);
    }
}
