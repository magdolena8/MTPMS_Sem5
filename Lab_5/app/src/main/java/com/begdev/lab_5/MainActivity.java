package com.begdev.lab_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
//            this.getSupportFragmentManager().beginTransaction().replace(R.id., fr).commit();
//        else
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ListContainer, fr).addToBackStack(null).commit();
//        EventsDBHelper dbHelper = new EventsDBHelper(this);

        NavigationView sideBar = findViewById(R.id.side_nav_bar);
        sideBar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_quit_app:{
                        finish();
                        System.exit(0);
                    }
                    case R.id.nav_add:{
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                            getFragmentManager().beginTransaction().replace(R.id.details_fragment_view, new AddFragment()).commit();
                        else
                            getFragmentManager().beginTransaction().replace(R.id.container_main, new AddFragment()).addToBackStack(null).commit();
                        return true;
                    }
                    case R.id.nav_list:{
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                            getFragmentManager().beginTransaction().replace(R.id.list_fragment_view, new ListFragment()).commit();
                        else
                            getFragmentManager().beginTransaction().replace(R.id.container_main, new ListFragment()).addToBackStack(null).commit();
                        return true;
                    }
                    case R.id.nav_change_view_type:{
                        RecyclerView recyclerView = findViewById(R.id.list);
//                        recyclerView.getLayoutManager();
                        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    }
                }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_mbtn:{
                return true;
            }
            case R.id.list_mbtn:
//                getFragmentManager().beginTransaction().replace(R.id.container_main, new ListFragment()).commit();
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    this.getFragmentManager().beginTransaction().replace(R.id.list_fragment_view, new ListFragment()).commit();
                else
                    this.getFragmentManager().beginTransaction().replace(R.id.container_main, new ListFragment()).addToBackStack(null).commit();

                return true;

            case R.id.add_mbtn:
//                getFragmentManager().beginTransaction().replace(R.id.container_main, new AddFragment()).commit();
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    this.getFragmentManager().beginTransaction().replace(R.id.details_fragment_view, new AddFragment()).commit();
                else
                    this.getFragmentManager().beginTransaction().replace(R.id.container_main, new AddFragment()).addToBackStack(null).commit();

                return true;
            default:
                    return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public void onSendData(String selectedItem) {
//        EventFragment eventFragment = (EventFragment)getFragmentManager().findFragmentById(R.id.fragment_event);
//
//        if (eventFragment != null) {
//            Bundle args = new Bundle();
//            args.putInt("ID", position);
//            eventFragment.setArguments(args);
//        }
//    }
    @Override
    protected void onStart() {
        super.onStart();
        try {
            Event.getEventsList(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}