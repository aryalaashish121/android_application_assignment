package com.example.onlinestore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinestore.Interface.UsersApi;
import com.example.onlinestore.Model.Tokenauth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    Button bt_login;
    UsersApi api;
    TextView linkRegister;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        linkRegister = findViewById(R.id.linkRegister);

        bt_login = findViewById(R.id.btn_login);
        bt_login.setOnClickListener(this);
        linkRegister.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
           // validation_Login();
           login();

        }

        if(v.getId() == R.id.linkRegister){
            Intent register = new Intent(Login.this,Register.class);
            startActivity(register);
        }

    }

    private void createInstance(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(UsersApi.class);
    }
    public void login() {
        createInstance();
        Call<Tokenauth> checkUSer = api.userVerification(username.getText().toString(), password.getText().toString());
       checkUSer.enqueue(new Callback<Tokenauth>() {
           @Override
           public void onResponse(Call<Tokenauth> call, Response<Tokenauth> response) {
              if(!response.isSuccessful()){
                  Toast.makeText(Login.this, "Not sucessful"+response.code(), Toast.LENGTH_SHORT).show();
                  return;

              }
              
               preferences = (Login.this).getSharedPreferences("UserData",0);
               editor = preferences.edit();
              Tokenauth res = response.body();
              Toast.makeText(Login.this, ",Logged in", Toast.LENGTH_SHORT).show();
               editor.putString("token",res.getToken());
               editor.putString("uid",res.getUsers().get_id());
               Toast.makeText(Login.this, "userid displayed: "+res.getUsers().get_id(), Toast.LENGTH_SHORT).show();
               editor.commit();

               Toast.makeText(Login.this, "Logged in"+response.body().getToken(), Toast.LENGTH_SHORT).show();

               Intent intent = new Intent(Login.this,MainActivity.class);
               startActivity(intent);
               finish();
           }

           @Override
           public void onFailure(Call<Tokenauth> call, Throwable t) {

               Toast.makeText(Login.this, "Error 0"+t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });

    }
    public void validation_Login() {
        if (TextUtils.isEmpty(username.getText().toString())) {
            username.setError("Please enter username");
            username.requestFocus();
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Please enter password");
            password.requestFocus();
        }
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "You havenot Logged in Yet.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Login.this,MainActivity.class);
        startActivity(intent);
    }

}
