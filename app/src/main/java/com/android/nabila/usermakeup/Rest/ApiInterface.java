package com.android.nabila.usermakeup.Rest;

import com.android.nabila.usermakeup.Model.GetLogin;
import com.android.nabila.usermakeup.Model.GetProduk;
import com.android.nabila.usermakeup.Model.GetUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/login")
    Call<GetLogin> loginUser
            (
             @Field("username") String username,
             @Field("password") String password);

    @GET("produk/produk_by_jenis")
    Call<GetProduk> getProdukByJenis(@Query("jenisProduk") String jenisProduk);
}
