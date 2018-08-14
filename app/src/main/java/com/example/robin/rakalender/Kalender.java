package com.example.robin.rakalender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.robin.rakalender.Models.TerminModel;
import com.example.robin.rakalender.Models.TerminReadResponse;
import com.example.robin.rakalender.Utils.ApiUtils;
import com.example.robin.rakalender.Utils.KalenderService;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Kalender extends AppCompatActivity implements CalendarPickerController {

    KalenderService tService;

    Kalender me = this;

    ArrayList<TerminModel> data;
    AgendaCalendarView mAgendaCalendarView;

    List<CalendarEvent> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarKalender);
        setSupportActionBar(toolbar);

        tService = ApiUtils.getKalender();

        mAgendaCalendarView = (AgendaCalendarView) findViewById(R.id.agenda_calendar_view);

        setupToolbar();
    }


    @Override
    public void onResume() {
        super.onResume();
        // put your code here...
        getTermin();
    }

    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public void getTermin() {

        try {

            String token = ApiUtils.getInstance().preferences.getString("token", "");

            tService.readTermin(token).enqueue(new Callback<TerminReadResponse>() {

                @Override
                public void onResponse(Call<TerminReadResponse> call, Response<TerminReadResponse> response) {
                    if (response.isSuccessful()) {

                        TerminReadResponse result = response.body();
                        if (result.isOk()) {

                            eventList = new ArrayList<>();
                            data = result.getData();
                            for (TerminModel model : data) {

                                BaseCalendarEvent event1 = new BaseCalendarEvent(model.getDescription(), model.getName(), model.getAdresse(),
                                        ContextCompat.getColor(me, R.color.blue_selected), toCalendar(model.getDate_time_anfang()), toCalendar(model.getDate_time_ende()), false);
                                event1.setId(model.getTermin_id());
                                eventList.add(event1);

                            }

                            Calendar minDate = Calendar.getInstance();
                            Calendar maxDate = Calendar.getInstance();

                            minDate.add(Calendar.MONTH, -2);
                            minDate.set(Calendar.DAY_OF_MONTH, 1);
                            maxDate.add(Calendar.YEAR, 1);
                            mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), me);

                        }

                    }
                }

                @Override
                public void onFailure(Call<TerminReadResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }


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

            case R.id.logOutItem:
                logout();

        }

        return super.onOptionsItemSelected(item);
    }

    public void toAbout (){
        startActivity(new Intent(Kalender.this, About.class));

    }

    public boolean logout() {

        ApiUtils.getInstance().editor.putString("token", "");
        ApiUtils.getInstance().editor.putString("user", "");
        ApiUtils.getInstance().editor.putString("password", "");
        ApiUtils.getInstance().editor.commit();


        startActivity(new Intent(Kalender.this, LoginActivity.class));
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_items_menu, menu);

        menu.findItem(R.id.optionsItem).setVisible(false);
        menu.findItem(R.id.terminLöschenItem).setVisible(false);
        menu.findItem(R.id.terminEintragenItem).setVisible(false);

        return true;
    }

    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {



        if(event.getId() == 0 ) {

            toTerminEintragen();

        }
        else {

            TerminModel model = null;

            for (TerminModel m : data) {
                if (m.getTermin_id() == event.getId()) {
                    model = m;
                }

            }

            if (model != null) {

                Gson gson = new Gson();

                Intent intent = new Intent(Kalender.this, TerminDetail.class);

                intent.putExtra("model", gson.toJson(model));
                Kalender.this.startActivity(intent);
            }

            else{

                Toast.makeText(me, "Error loading event", Toast.LENGTH_LONG).show();
                //
            }
        }


    }

    @Override
    public void onScrollToDate(Calendar calendar) {

    }

    public void toTerminEintragen() {


        startActivity(new Intent(Kalender.this, TerminEintragen.class));
    }

}
