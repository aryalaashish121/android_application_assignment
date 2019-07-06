package com.example.onlinestore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinestore.Interface.UsersApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity implements View.OnClickListener {
EditText name,email,password,cpassword,city,postal,address1,address2;
    Button bt_signup;
    UsersApi usersApi;
    TextView linkLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.input_Name);
        email = findViewById(R.id.input_Email);
        password = findViewById(R.id.input_Password);
        cpassword = findViewById(R.id.input_Cpassword);
        city = findViewById(R.id.city);
        postal = findViewById(R.id.postal);
        address1 = findViewById(R.id.input_address1);
        address2 = findViewById(R.id.input_address2);
        linkLogin = findViewById(R.id.linkLogin);


        bt_signup = findViewById(R.id.btn_signup);
        String userimage="image here";

        bt_signup.setOnClickListener(this);
        linkLogin.setOnClickListener(this);
    }

    private void createInstance(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        usersApi = retrofit.create(UsersApi.class);
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_signup){
            validation_Registration();
            createInstance();
            Call<String> usersCall = usersApi.registerUser(name.getText().toString(),email.getText().toString(), password.getText().toString(),"userimage",city.getText().toString(),postal.getText().toString(),address1.getText().toString(),address2.getText().toString());
            usersCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(Register.this, "data"+response.body(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Register.this,"Registration Successful!",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println("Error "+t.getLocalizedMessage());
                    Toast.makeText(Register.this, "Error "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else if(v.getId()==R.id.linkLogin){
            Intent login = new Intent(Register.this,Login.class);
            startActivity(login);
        }
    }
    public void validation_Registration(){
        if (TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError("Please enter username");
            password.requestFocus();
        }
        if (TextUtils.isEmpty(cpassword.getText().toString()))
        {
            cpassword.setError("Please Confirm your Password");
            cpassword.requestFocus();
        }
        if(password.getText().toString().length()<8){
            password.setError("Password should contain at least 8 characters");
            password.requestFocus();
        }
        if(!(password.getText().toString().equals(cpassword.getText().toString()))){
            password.setError("Password and Confirm Password should be same");
            cpassword.setError("Password and Confirm Password should be same");
            password.requestFocus();
            cpassword.requestFocus();
        }
//        if (TextUtils.isEmpty(username.getText().toString()))
//        {
//            username.setError("Please enter username");
//            username.requestFocus();
//        }
//        if(TextUtils.isEmpty(fname.getText().toString()))
//        {
//            fname.setError("Please enter password");
//            fname.requestFocus();
//        }
//        if (TextUtils.isEmpty(lname.getText().toString()))
//        {
//            lname.setError("Please enter username");
//            lname.requestFocus();
//        }
    }
}
