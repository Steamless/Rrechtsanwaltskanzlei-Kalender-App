package com.example.robin.rakalender;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.robin.rakalender.Models.ClientModel;
import com.example.robin.rakalender.Models.ClientReadResponse;
import com.example.robin.rakalender.Models.SimpleResponse;
import com.example.robin.rakalender.Models.TerminModel;
import com.example.robin.rakalender.Models.TerminTypModel;
import com.example.robin.rakalender.Models.TerminTypReadResponse;
import com.example.robin.rakalender.Utils.ApiUtils;
import com.example.robin.rakalender.Utils.KalenderService;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TerminEintragen extends AppCompatActivity implements   View.OnFocusChangeListener {


    KalenderService tService;
    TerminEintragen me = this;
    EditText datumTextEdit;
    EditText uhrzeitTextEdit;
    EditText editTextDescription;

    Button speichernButton;

    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    Spinner clientDropdown;
    Spinner terminTypDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_eintragen);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        tService = ApiUtils.getKalender();


        clientDropdown = (Spinner) findViewById(R.id.clientDropdown);


        terminTypDropdown = (Spinner) findViewById(R.id.terminTypDropdown);

        speichernButton = (Button) findViewById(R.id.speichernButton);
        speichernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Guardar();

            }
        });

        editTextDescription = (EditText) findViewById(R.id.editTextDescription);


        datumTextEdit = (EditText) findViewById(R.id.datumTextEdit);
        // datumTextEdit.setOnClickListener(this);


        uhrzeitTextEdit = (EditText) findViewById(R.id.uhrzeitTextEdit);
        //    uhrzeitTextEdit.setOnClickListener(this);


        datumTextEdit.setOnFocusChangeListener(this);
        uhrzeitTextEdit.setOnFocusChangeListener(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarTerminEintragen);
        setSupportActionBar(toolbar);

        setupToolbar();

    }

    public void toAbout() {
        startActivity(new Intent(TerminEintragen.this, About.class));

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

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            switch (view.getId()) {
                case R.id.datumTextEdit:
                    obtenerFecha();
                    break;
                case R.id.uhrzeitTextEdit:
                    obtenerHora();
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        String token = ApiUtils.getInstance().preferences.getString("token", "");

        tService.readClients(token).enqueue(new Callback<ClientReadResponse>() {
            @Override
            public void onResponse(Call<ClientReadResponse> call, Response<ClientReadResponse> response) {

                if (response.isSuccessful()) {

                    ClientReadResponse data = response.body();
                    if (data.isOk()) {

                        ArrayAdapter<ClientModel> adapter = new ArrayAdapter<ClientModel>(me, android.R.layout.simple_spinner_item, data.getData());
                        clientDropdown.setAdapter(adapter);

                    }else{
                        Toast.makeText(me, "Error loading clients", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(me, "Error loading clients", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ClientReadResponse> call, Throwable t) {
                Toast.makeText(me, "Error loading clients", Toast.LENGTH_LONG).show();
            }
        });


        tService.readTerminTyp(token).enqueue(new Callback<TerminTypReadResponse>() {
            @Override
            public void onResponse(Call<TerminTypReadResponse> call, Response<TerminTypReadResponse> response) {
                if (response.isSuccessful()) {

                    TerminTypReadResponse data = response.body();
                    if (data.isOk()) {

                        ArrayAdapter<TerminTypModel> adapter = new ArrayAdapter<TerminTypModel>(me, android.R.layout.simple_spinner_item, data.getData());
                        terminTypDropdown.setAdapter(adapter);

                    }
                    else{
                        Toast.makeText(me, "Error loading Termin typ", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(me, "Error loading Termin typ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<TerminTypReadResponse> call, Throwable t) {
                Toast.makeText(me, "Error loading Termin typ", Toast.LENGTH_LONG).show();
            }
        });

    }


    private void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                datumTextEdit.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }


    private void obtenerHora() {
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if (hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                uhrzeitTextEdit.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
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

    public static Date getDate(int year, int month, int day , int hour , int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    public void Guardar() {


        String token = ApiUtils.getInstance().preferences.getString("token", "");
        String userId = ApiUtils.getInstance().preferences.getString("userId", "");

        Date fechaHoraInicio = getDate(anio, mes, dia , hora,minuto);

        Date fechaHoraFinal = getDate(anio, mes, dia , hora,minuto + 90);
        ClientModel clientModel = (ClientModel) clientDropdown.getSelectedItem();


        TerminTypModel terminTypModel = (TerminTypModel) terminTypDropdown.getSelectedItem();


        TerminModel modelo = new TerminModel(Integer.parseInt(userId), editTextDescription.getText().toString(), fechaHoraInicio, fechaHoraFinal, terminTypModel.getTermin_id_typ(), clientModel.getClient_id());

        tService.createTermin(token, modelo).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {


                if (response.isSuccessful()) {

                    SimpleResponse result = response.body();
                    if (result.isOk()) {

                        finish();

                    } else {

                        Toast.makeText(me, "Error deleting data, server error", Toast.LENGTH_LONG).show();
                    }
                } else {
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
