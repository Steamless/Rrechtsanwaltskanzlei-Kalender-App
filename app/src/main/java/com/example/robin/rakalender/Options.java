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
import android.widget.EditText;

public class Options extends AppCompatActivity {

    EditText usernameTextOptions;
    EditText oldPasswordTextOptions;
    EditText neuPasswordTextOptions;
    EditText wiederNeuPasswordTextOptions;

    Button optionsSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        usernameTextOptions = (EditText)findViewById(R.id.usernameTextOptions);
        oldPasswordTextOptions = (EditText)findViewById(R.id.oldPasswordTextOptions);
        neuPasswordTextOptions = (EditText)findViewById(R.id.neuPasswordTextOptions);
        wiederNeuPasswordTextOptions = (EditText)findViewById(R.id.wiederNeuPasswordTextOptions);

        optionsSaveButton = (Button)findViewById(R.id.optionsSaveButton);
        optionsSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SaveSettings();
            }
        });


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarOptions);
        setSupportActionBar(toolbar);

        setupToolbar();
    }

    public void SaveSettings() {
//Send Änderung to server bzw. Databank
        moveToMain();
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.aboutItem:
                toAbout();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void toAbout (){
        startActivity(new Intent(Options.this, About.class));

    }

    public void moveToMain(){
        startActivity(new Intent(Options.this, MainMenu.class));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        moveToMain();
        return true;
    }

    private void setupToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }
}
