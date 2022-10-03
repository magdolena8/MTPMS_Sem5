package com.example.lab_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityThird extends AppCompatActivity {
    Film film;
    TextView titleTW, directoorTW, genreTW,
            dateTW, countryTW, tagLineTW,
            descriptionTW, isLocalizedTW, raitingTW;
    static final String fname = "database.json";
    static ArrayList<Film> filmAray = new ArrayList<Film>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        film = (Film) getIntent().getSerializableExtra("film");
        findElements();

        titleTW.setText(film.getTitle());
        directoorTW.setText(film.getDirector());
        genreTW.setText(film.getGenre());
        dateTW.setText(film.getDate().toString());
        countryTW.setText(film.getCountry());
        tagLineTW.setText(film.getTagline());
        descriptionTW.setText(film.getDescription());
        isLocalizedTW.setText(film.getIsLocalized().toString());
        raitingTW.setText(film.getRating().toString());
    }

    private void findElements() {
        titleTW = findViewById(R.id.titleTW);
        directoorTW = findViewById(R.id.directoorTW);
        genreTW = findViewById(R.id.genreTW);
        dateTW = findViewById(R.id.dateTW);
        countryTW = findViewById(R.id.countryTW);
        tagLineTW = findViewById(R.id.tagLineTW);
        descriptionTW = findViewById(R.id.descriptionTW);
        isLocalizedTW = findViewById(R.id.isLocalizedTW);
        raitingTW = findViewById(R.id.raitingTW);
    }

    public void backBtnClick(View view) {
        Intent intent = new Intent(this, ActivitySecond.class);
        startActivity(intent);
    }

    public void saveBtnClick(View view) throws IOException {
        Gson gson = new Gson();
        File f = new File(super.getFilesDir(), fname);
        if (!ExistBase(fname)) {
            f.createNewFile();
        }

        if (f.length() != 0) {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String str = bfr.readLine();
            filmAray = gson.fromJson(str, ArrayList.class);
            fr.close();
            bfr.close();
        }
        filmAray.add(film);
        String jsonString = gson.toJson(filmAray);
        FileWriter fw = new FileWriter(f, false);   //rewrite
        fw.write(jsonString);
        fw.close();

    }

    public void restartBtnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NO_HISTORY);
//        ActivityCompat.finishAffinity(Activity MainActivity.class);
//        ActivityCompat.finishAffinity(MainActivity.class);
        startActivity(intent);
    }

    private boolean ExistBase(String fname) {
        boolean rc = false;
        File f = new File(super.getFilesDir(), fname);
        if (rc = f.exists()) Log.d("Log_05", "Файл " + fname + " существует");
        else Log.d("Log_05", "Файл" + fname + " не найден");
        return rc;
    }
}
