package com.el.mkoba;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RetrofitInterface {

    @POST("/stk")
    Call<Void> executestk (@Body Map<String ,String > HashMap);

}
