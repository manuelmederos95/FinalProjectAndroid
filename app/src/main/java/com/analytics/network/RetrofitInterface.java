package com.analytics.network;

import com.analytics.model.Project;
import com.analytics.model.Response;
import com.analytics.model.User;

import java.math.BigInteger;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("loginfromandroid")
    Observable<User> login(@Field("email") String email, @Field("password") String password );
    //Observable<Response> login();


    @FormUrlEncoded
    @POST("getprojectforandroid")
    Observable< List<Project> > getProjects(@Field("id") BigInteger id);

    @POST("users")
    Observable<Response> register(@Body User user);

    /**@POST("authenticate")
    Observable<Response> login();*/

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);
}
