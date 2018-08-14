package com.example.robin.rakalender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.robin.rakalender.Models.LoginRequest;
import com.example.robin.rakalender.Models.LoginResponse;
import com.example.robin.rakalender.Utils.ApiUtils;
import com.example.robin.rakalender.Utils.KalenderService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {

    EditText userText;
    EditText passText;
    KalenderService mService;

    ProgressBar progressBar;

    Button login_button;

    LoginActivity me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        userText = (EditText)findViewById(R.id.userText);
        passText = (EditText)findViewById(R.id.passText);

        login_button = (Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(VISIBLE);
                login(view);
            }
        });

        mService = ApiUtils.getKalender();


        String token = ApiUtils.getInstance().preferences.getString("token","");

        if (token.length()>0) {

                toManiMenu();
        }

//        if (BuildConfig.DEBUG) {
//            userText.setText("fer@gmail.com");
//            passText.setText("ferfer");
//        }

    }

    public void toManiMenu(){

        startActivity(new Intent(LoginActivity.this, MainMenu.class));

        //startActivity(new Intent(LoginActivity.this, MainMenu.class));
    }

    public void login(View view ) {

        final String user = userText.getText().toString();
        final String pass = passText.getText().toString();



      if (user.length()>0 && pass.length()>0){

          LoginRequest login = new LoginRequest(user,pass);

            mService.login(login).enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if (response.isSuccessful()) {

                        Log.d("", "");

                        LoginResponse loginResponse = response.body();

                        if (loginResponse.getOk()) {

                            //Toast.makeText(me, "Loading, bitte warten", Toast.LENGTH_SHORT).show();

                            String token = loginResponse.getToken();
                            ApiUtils.getInstance().editor.putString("token",token );

                            ApiUtils.getInstance().editor.putString("user", user);
                            ApiUtils.getInstance().editor.putString("password", pass);


                            ApiUtils.getInstance().editor.putString("name", loginResponse.getName());


                            ApiUtils.getInstance().editor.putString("userId", loginResponse.getUserId());

                            ApiUtils.getInstance().editor.commit();
                            ApiUtils.getInstance().loadData();

                            progressBar.setVisibility(View.GONE);

                            toManiMenu();

                        }else {
                            Toast.makeText(me, "Username or Password is falsch", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    } else {

                        // int statusCode = response.code();
                        Toast.makeText(me, "error with server", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }


                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    Toast.makeText(me, "Keine Internet Verbindung", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }else{
          Toast.makeText(me, "Bitte Username und Password eingeben", Toast.LENGTH_LONG).show();
          progressBar.setVisibility(View.GONE);

        }

    }

}
