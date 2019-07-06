package com.example.onlinestore.Interface;

import com.example.onlinestore.Model.ItemsDetail;
import com.example.onlinestore.Model.Tokenauth;
import com.example.onlinestore.Model.UserDetails;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UsersApi {

    @FormUrlEncoded
    @POST("user/userRegistration")
    Call<String> registerUser(@Field("userName") String name, @Field("userEmail") String email, @Field("userPassword") String password,@Field("userImage") String userImage, @Field("city") String city, @Field("postal") String postal, @Field("userAddress1") String address1, @Field("userAddress2") String address2);


    @FormUrlEncoded
    @POST("user/userLogin")
    Call<Tokenauth> userVerification(@Field("username") String username, @Field("password") String password);

    @GET("product/displayAllProduct")
    Call<List<ItemsDetail>> getItemDetail();

    @GET("product/displayProduct/{id}")
    Call<List<ItemsDetail>> getSpecificProduct();

    @GET("user/getUserById/{id}")
    Call<UserDetails> profiledata(@Path("id") String userid);

    @FormUrlEncoded
    @PUT("user/updateUserMobile")
    Call<String> updateUser(@Field("_id") String uid,@Field("userName") String name, @Field("userEmail") String email,@Field("userImage") String userImage, @Field("city") String city, @Field("postal") String postal, @Field("userAddress1") String address1, @Field("userAddress2") String address2);

    @Multipart
    @POST("uploadUserImage")
    Call<String> uploadImage(@Part MultipartBody.Part body);
}

