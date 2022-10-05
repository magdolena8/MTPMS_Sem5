package com.example.lab_3;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListActivity extends Activity {
    private ArrayList<Film> filmArray;
    private Film selectedFilm;
    ListView filmListView;

    String fname = "database.json";



    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        filmListView = findViewById(R.id.listView);
        verifyStoragePermissions(this);
        try {
            Gson gson = new Gson();
            File f = new File(super.getFilesDir(), fname);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String str = bfr.readLine();
            filmArray = gson.fromJson(str, new TypeToken<ArrayList<Film>>(){}.getType());
            fr.close();
            bfr.close();
        }catch (IOException e){e.printStackTrace();}

        ArrayAdapter<Film> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, filmArray);
        filmListView.setAdapter(adapter);

        filmListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Film selectedFilm = filmArray.get(position);
                openFilmActivity((Film)selectedFilm);
            }
        });
    }
    public void openFilmActivity(Film film){
        Intent intent = new Intent(this, FilmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("film", film);
        startActivity(intent);
    }
}
