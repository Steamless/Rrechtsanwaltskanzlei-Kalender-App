package com.example.robin.rakalender.Utils;

import com.example.robin.rakalender.Models.ClientModel;
import com.example.robin.rakalender.Models.ClientReadResponse;
import com.example.robin.rakalender.Models.LoginRequest;
import com.example.robin.rakalender.Models.LoginResponse;
import com.example.robin.rakalender.Models.SimpleResponse;
import com.example.robin.rakalender.Models.TerminModel;
import com.example.robin.rakalender.Models.TerminReadResponse;
import com.example.robin.rakalender.Models.TerminTypReadResponse;
import com.example.robin.rakalender.Models.UserReadResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface KalenderService {

    //login
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest body);

    //El que da un arreglo de citas
    @GET("/termin")
    Call<TerminReadResponse> readTermin (@Query("token") String token );
    @POST("/termin")
    Call<SimpleResponse> createTermin(@Query("token") String token, @Body TerminModel model );
    @PUT("/termin")
    Call<SimpleResponse> updateTermin(@Query("token") String token, @Body TerminModel model );
    @POST("/terminDelete")
    Call<SimpleResponse> deleteTermin(@Query("token") String token, @Body TerminModel model );



    //El que da un arreglo de clientes
    @GET("/clients")
    Call<ClientReadResponse> readClients (@Query("token") String token );
    @POST("/clients")
    Call<SimpleResponse> createClient(@Query("token") String token, @Body ClientModel model );
    @PUT("/clients")
    Call<SimpleResponse> updateClient(@Query("token") String token, @Body ClientModel model );
    @DELETE("/clients")
    Call<SimpleResponse> deleteClient(@Query("token") String token, @Body ClientModel model );





    //El que da un arreglo de clientes
    @GET("/termin_typ")
    Call<TerminTypReadResponse> readTerminTyp(@Query("token") String token );



    //El que da un arreglo de clientes
    @GET("/users")
    Call<UserReadResponse> readUsers (@Query("token") String token );


}

