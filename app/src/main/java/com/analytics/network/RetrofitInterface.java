package com.analytics.network;

import com.analytics.model.ProjectList;
import com.analytics.model.Response;
import com.analytics.model.Stats;
import com.analytics.model.User;

import java.math.BigInteger;
import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("loginfromandroid")
    Observable<User> login(@Field("email") String email, @Field("password") String password );
    //Observable<Response> login();


    @FormUrlEncoded
    @POST("getprojectforandroid")
    Observable<ProjectList> getProjects(@Field("id") BigInteger id);

    @FormUrlEncoded
    @POST("getstatforandroid")
    Observable<List<Stats>> getAllStats(@Field("projectID") BigInteger projectID);

    @FormUrlEncoded
    @POST("getstatforandroid")
    Observable<Response> signUp(@Field("firstname") String firstname,
                                @Field("lastname") String lastname, @Field("email") String email,
                                @Field("password") String password, @Field("password2") String password2 );

    @FormUrlEncoded
    @POST("getstatforandroid")
    Observable<Response> resetPassword(@Field("email") String email);

}
