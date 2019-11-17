package com.asisctf.ShareL;

import com.asisctf.ShareL.models.MyLinks;
import com.asisctf.ShareL.models.NewDevice;
import com.asisctf.ShareL.models.PreviewLink;
import com.asisctf.ShareL.models.ShareLink;
import com.asisctf.ShareL.models.SharePLinkUser;
import com.asisctf.ShareL.models.Top10Users;
import com.asisctf.ShareL.models.UserProfile;
import com.asisctf.ShareL.models.UsrPLink;
import com.asisctf.ShareL.models.Whoami;
import com.asisctf.ShareL.models.sShareLink;
import com.asisctf.ShareL.models.sSharePLink;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterFace {
    @GET("api/link/mylinks")
    @Headers({"Content-Type:application/json"})
    Call<MyLinks> MyLinks(@Header("auth-token") String str, @Header("device-id") String str2);

    @GET("api/link/preview/{user_id}/{rnd_num}/{link_name}")
    @Headers({"Content-Type:application/json"})
    Call<PreviewLink> PreviewLink(@Header("auth-token") String str, @Header("device-id") String str2, @Path("user_id") int i, @Path("rnd_num") int i2, @Path("link_name") String str3);

    @GET("api/user/{user_id}/public_links")
    @Headers({"Content-Type:application/json"})
    Call<UsrPLink> UserPubLink(@Header("auth-token") String str, @Header("device-id") String str2, @Path("user_id") int i);

    @GET("api/users/{user_id}")
    @Headers({"Content-Type:application/json"})
    Call<UserProfile> getUserProfile(@Header("auth-token") String str, @Header("device-id") String str2, @Path("user_id") int i);

    @GET("api/users/register/{md}/{rnd}")
    Call<NewDevice> regNewDev(@Path("md") String str, @Path("rnd") String str2);

    @POST("api/links/share")
    @Headers({"Content-Type:application/json"})
    Call<ShareLink> shareLink(@Header("auth-token") String str, @Header("device-id") String str2, @Body sShareLink ssharelink);

    @POST("api/links/share/private")
    @Headers({"Content-Type:application/json"})
    Call<SharePLinkUser> sharePLink(@Header("auth-token") String str, @Header("device-id") String str2, @Body sSharePLink sshareplink);

    @GET("api/users/top")
    @Headers({"Content-Type:application/json"})
    Call<Top10Users> top10user(@Header("auth-token") String str, @Header("device-id") String str2);

    @GET("api/users/me")
    @Headers({"Content-Type:application/json"})
    Call<Whoami> whoami(@Header("auth-token") String str, @Header("device-id") String str2);
}
