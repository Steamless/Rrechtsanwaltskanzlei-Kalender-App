package com.example.robin.rakalender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.robin.rakalender.Utils.ApiUtils;


public class MainMenu extends AppCompatActivity {

    TextView wilkommenText;

    Button kontaktenButton;
    Button kalenderButton;
    Button clientsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

      Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarMain);
       setSupportActionBar(toolbar);


        kontaktenButton = (Button)findViewById(R.id.kontaktenButton);
        kontaktenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toKontakten();
            }
        });

        clientsButton = (Button) findViewById(R.id.clientsButton);
        clientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClients();
            }
        });

        wilkommenText =(TextView) findViewById(R.id.wilkommenText);

        kalenderButton = (Button)findViewById(R.id.kalenderButton);
        kalenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toKalender();
            }
        });

        setTextViewWelcome();
        setupToolbar();

    }

    private void setupToolbar() {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_items_menu, menu);

        menu.findItem(R.id.terminEintragenItem).setVisible(false);
        menu.findItem(R.id.terminLöschenItem).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.aboutItem:
                toAbout();
                return true;

            case R.id.optionsItem:
                toOptions();
                return true;

            case R.id.logOutItem:
                logout();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();

        setTextViewWelcome();
    }

    public void setTextViewWelcome() {


        StringBuilder message = new StringBuilder();

        message.append(getString(R.string.welcome));

        //get the username
        //message.append("Benutzer");
        message.append(ApiUtils.getInstance().preferences.getString("name","unknow"));

        wilkommenText.setText(message.toString());
    }

    public boolean logout() {

        ApiUtils.getInstance().editor.putString("token", "");
        ApiUtils.getInstance().editor.putString("user", "");
        ApiUtils.getInstance().editor.putString("password", "");
        ApiUtils.getInstance().editor.commit();


        startActivity(new Intent(MainMenu.this, LoginActivity.class));
        return true;
    }

    public void toOptions (){
        startActivity(new Intent(MainMenu.this, Options.class));

    }

    public void toAbout (){
        startActivity(new Intent(MainMenu.this, About.class));

    }

    public void toKontakten (){
        startActivity(new Intent(MainMenu.this, Kontakten.class));

    }

    public void toKalender (){
        startActivity(new Intent(MainMenu.this, Kalender.class));

    }

    public void toClients (){
        startActivity(new Intent(MainMenu.this, ShowClientsActivity.class));

    }

}
