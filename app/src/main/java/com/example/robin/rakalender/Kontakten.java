package com.example.robin.rakalender;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robin.rakalender.Models.ClientModel;
import com.example.robin.rakalender.Models.ClientReadResponse;
import com.example.robin.rakalender.Models.UserModel;
import com.example.robin.rakalender.Models.UserReadResponse;
import com.example.robin.rakalender.Utils.ApiUtils;
import com.example.robin.rakalender.Utils.KalenderService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Kontakten extends AppCompatActivity {


    ListView listViewClients;



    UserReadResponse data;
    KalenderService mService;
 UsersListAdapter listViewAdapter;
    Kontakten me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakten);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarKontakten);
        setSupportActionBar(toolbar);

        setupToolbar();

        listViewAdapter = new UsersListAdapter(this, data);

// get the ListView and attach the adapter

        listViewClients = (ListView) findViewById(R.id.listViewKontakten);
        listViewClients.setAdapter(listViewAdapter);



        mService = ApiUtils.getKalender();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_items_menu, menu);

        menu.findItem(R.id.terminEintragenItem).setVisible(false);
        menu.findItem(R.id.terminLöschenItem).setVisible(false);
        menu.findItem(R.id.optionsItem).setVisible(false);

        return true;
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

    public boolean logout() {

        ApiUtils.getInstance().editor.putString("token", "");
        ApiUtils.getInstance().editor.putString("user", "");
        ApiUtils.getInstance().editor.putString("password", "");
        ApiUtils.getInstance().editor.commit();


        startActivity(new Intent(Kontakten.this, LoginActivity.class));
        return true;
    }

    public void toAbout (){
        startActivity(new Intent(Kontakten.this, About.class));

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
    public void onResume() {
        super.onResume();
        loadUsers();
    }

    public void loadUsers() {


        String token = ApiUtils.getInstance().preferences.getString("token", "");

        mService.readUsers(token).enqueue(new Callback<UserReadResponse>() {
            @Override
            public void onResponse(Call<UserReadResponse> call, Response<UserReadResponse> response) {
                if (response.isSuccessful()) {

                    UserReadResponse result = response.body();
                    if (result.isOk()) {

                        data = result;
                        listViewAdapter.updateData(data);
                    }else{

                        Toast.makeText(me, "Error loading data, server error", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(me, "Error loading data, check internet conection", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserReadResponse> call, Throwable t) {
                Toast.makeText(me, "Error loading data, check internet conection", Toast.LENGTH_LONG).show();
            }
        });



    }




    public class UsersListAdapter extends BaseAdapter {

        private Context context; //context
        private UserReadResponse response; //data source of the list adapter


        public UsersListAdapter(Context context, UserReadResponse data) {

            this.context = context;
            this.response = data;
        }

        public void updateData(UserReadResponse data){
            response = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if(response!=null) {
                if(response.isOk()){
                    return response.getData().size();
                }else{
                    return  0;
                }
            }
            else{
                return 0;
            }
        }

        @Override
        public Object getItem(int i) {
            return response.getData().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.layout_list_view_row_items, parent, false);
            }

            // get current item to be displayed
            UserModel currentItem = (UserModel) getItem(position);

            // get the TextView for item name and item description
            TextView textViewItemName = (TextView)
                    convertView.findViewById(R.id.text_view_item_name);
            TextView textViewItemDescription = (TextView)
                    convertView.findViewById(R.id.text_view_item_description);

            //sets the text for item name and item description from the current item object
            textViewItemName.setText(currentItem.getName());
            textViewItemDescription.setText(currentItem.getE_mail());

            // returns the view for the current row
            return convertView;
        }
    }
}