package com.example.onlinestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.onlinestore.Interface.UsersApi;
import com.example.onlinestore.Model.UserDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateProfile extends AppCompatActivity implements View.OnClickListener {

    EditText username,useremail,userImageName,city,postal,address1,address2;
    ImageView userImage;
    String userid,tok;
    Button btn_update;
    UsersApi usersApi;
    FloatingActionButton editprofileButton;
    public static final String BASE_URL = "http://10.0.2.2:3000/";
    private static final int PICK_IMAGE = 1;

    Bitmap bitmap;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        userImageName = findViewById(R.id.userImageName);
        username = findViewById(R.id.userName);
        useremail = findViewById(R.id.userEmail);
        city = findViewById(R.id.city);
        postal = findViewById(R.id.postal);
        address1 = findViewById(R.id.userAddress1);
        address2 = findViewById(R.id.userAddress2);
        userImage = findViewById(R.id.updateuserprofile);
        userImageName.setVisibility(View.INVISIBLE);

        //edit profile button
        editprofileButton = findViewById(R.id.editprofile);
        editprofileButton.setOnClickListener(this);
        //installizing button
        btn_update = findViewById(R.id.btn_update);
        //adding click listner in button btn_update

        btn_update.setOnClickListener(this);
        loaduserdata();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_update){
            updateUser();
        }
        else if(view.getId()==R.id.editprofile){
            Opengallery();
        }
    }
    private void loaduserdata(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        usersApi = retrofit.create(UsersApi.class);
        SharedPreferences preferences=getSharedPreferences("UserData",0);
        userid = preferences.getString("uid",null);
        Toast.makeText(UpdateProfile.this, "Update user here... "+userid, Toast.LENGTH_LONG).show();
        final Call<UserDetails> userdata= usersApi.profiledata(userid);
        userdata.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                UserDetails userdetails = response.body();
                Toast.makeText(UpdateProfile.this, "Returned values are "+userdetails, Toast.LENGTH_LONG).show();
                username.setText(userdetails.getUserName());
                useremail.setText(userdetails.getUserEmail());
                city.setText(userdetails.getCity());
                postal.setText(userdetails.getPostal());
                address1.setText(userdetails.getUserAddress1());
                address2.setText(userdetails.getUserAddress2());
                userImageName.setText(userdetails.getUserImage());

                Toast.makeText(UpdateProfile.this, "city value +"+userdetails.getCity(), Toast.LENGTH_SHORT).show();
                StrictMode();
                try{
                    String imgurl = BASE_URL+userdetails.getUserImage();
                    URL url = new URL(imgurl);
                    userImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(UpdateProfile.this, "Failure here : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateUser(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        usersApi = retrofit.create(UsersApi.class);

        String uName,uEmail,uImage,uCity,uPostal,uAddress1,uAddress2;
        uName = username.getText().toString();
        uEmail = useremail.getText().toString();
        uImage = userImageName.getText().toString();
        uCity = city.getText().toString();
        uPostal = postal.getText().toString();
        uAddress1 = address1.getText().toString();
        uAddress2 = address2.getText().toString();

        SharedPreferences preferences=(UpdateProfile.this).getSharedPreferences("UserData",0);
        String userid = preferences.getString("uid",null);
        Toast.makeText(this, "User id is : "+userid, Toast.LENGTH_SHORT).show();
        Call<String> updateProfile = usersApi.updateUser(userid,uName,uEmail,uImage,uCity,uPostal,uAddress1,uAddress2);
        updateProfile.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(UpdateProfile.this, "Updated..", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UpdateProfile.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }

    private void Opengallery() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Choose Image"), PICK_IMAGE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                userImage.setImageBitmap(bitmap);
                uploadImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        try {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            usersApi = retrofit.create(UsersApi.class);
            File file = new File(getCacheDir(), "image.jpeg");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();

            RequestBody rb = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("imageName", file.getName(), rb);

//            PostAPI heroAPI = RetrofitHelper.instance().create(PostAPI.class);

            Call<String> imageModelCall = usersApi.uploadImage(body);
            imageModelCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(UpdateProfile.this, response.body(), Toast.LENGTH_SHORT).show();
                    userImageName.setText(response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(UpdateProfile.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
