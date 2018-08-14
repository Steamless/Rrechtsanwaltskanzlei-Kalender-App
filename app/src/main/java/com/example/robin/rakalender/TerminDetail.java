package com.example.robin.rakalender;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robin.rakalender.Models.SimpleResponse;
import com.example.robin.rakalender.Models.TerminModel;
import com.example.robin.rakalender.Utils.ApiUtils;
import com.example.robin.rakalender.Utils.KalenderService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TerminDetail extends AppCompatActivity {

    TerminModel model;

    Button deleteTerminButton;

    KalenderService tService;
    TerminDetail me = this;

    TextView datumTextShow;
    TextView uhrzeitextShow;
    TextView clientTextShow;
    TextView titelTerminTextEdit;
    TextView descriptionTerminText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTerminDetail);
        setSupportActionBar(toolbar);

        deleteTerminButton = (Button) findViewById(R.id.deleteTerminButton);
        deleteTerminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteTermin();

            }
        });



        datumTextShow = (TextView) findViewById(R.id.datumTextShow);
        uhrzeitextShow = (TextView) findViewById(R.id.uhrzeitextShow);


        titelTerminTextEdit = (TextView) findViewById(R.id.titelTerminTextEdit);
        descriptionTerminText = (TextView) findViewById(R.id.descriptionTerminText);
        clientTextShow = (TextView) findViewById(R.id.clientTextShow);


        tService = ApiUtils.getKalender();

        setupToolbar();

        Bundle p = getIntent().getExtras();
        Gson gson = new Gson();

        model = gson.fromJson(p.getString("model"), TerminModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_items_menu, menu);

        menu.findItem(R.id.terminEintragenItem).setVisible(false);
        menu.findItem(R.id.terminLöschenItem).setVisible(false);
        menu.findItem(R.id.optionsItem).setVisible(false);
        menu.findItem(R.id.logOutItem).setVisible(false);

        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (model!=null){

            android.text.format.DateFormat df = new android.text.format.DateFormat();

            datumTextShow.setText(df.format("yyyy-MM-dd", model.getDate_time_anfang()) );
            uhrzeitextShow.setText(df.format("hh:mm:ss a", model.getDate_time_anfang()) );
            clientTextShow.setText(model.getName() );
            titelTerminTextEdit.setText(model.getTermin_typ_description()  );
            descriptionTerminText.setText(model.getDescription() );

        }
    }



    public void toAbout() {
        startActivity(new Intent(TerminDetail.this, About.class));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void setupToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.aboutItem:
                toAbout();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void DeleteTermin() {

        //final  int photo = Integer.parseInt(Photo_id);
        AlertDialog.Builder alertDelete = new AlertDialog.Builder(this);
        alertDelete.setMessage("Möchten Sie den Termin wirklich löschen?")
                .setPositiveButton("ja", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        deleteTerminOk();


                    }
                })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDelete.show();

    }

    public void deleteTerminOk() {


        String token = ApiUtils.getInstance().preferences.getString("token", "");

        tService.deleteTermin( token,model).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {


                if (response.isSuccessful()) {

                    SimpleResponse result = response.body();
                    if (result.isOk()) {

                        finish();

                    }
                    else{

                        Toast.makeText(me, "Error deleting data, server error", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(me, "Error loading data, check internet conection", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Toast.makeText(me, "Error deleting data, check internet conection", Toast.LENGTH_LONG).show();
            }
        });

    }

}
