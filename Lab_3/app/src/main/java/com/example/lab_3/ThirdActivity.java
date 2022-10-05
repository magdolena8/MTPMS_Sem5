package com.example.lab_3;

//import static com.example.lab_3.ActivityThird.filmAray;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ThirdActivity extends Activity {
    private Film film;
    private ArrayList<Film> filmArray = new ArrayList<>();
    String imagePath;

    String fname = "database.json";

    EditText mailEdit, phoneEdit, vkEdit;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        film = (Film) getIntent().getSerializableExtra("film");

        mailEdit = findViewById(R.id.mailEdit);
        phoneEdit = findViewById(R.id.phoneEdit);
        vkEdit = findViewById(R.id.vkEdit);
        imageView = findViewById(R.id.imageView);
    }


    public void pickImage(View view){
        Intent imgPickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imgPickIntent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imagePath = cursor.getString(columnIndex);
                    film.image = imagePath;
                    cursor.close();
                    break;
            }
        }
    }

    public void saveBtnClick(View view){
        film.email = mailEdit.getText().toString();
        film.phone = phoneEdit.getText().toString();
        film.Vk = vkEdit.getText().toString();
        film.image = imagePath != null ? imagePath:"null";
        try {
            Gson gson = new Gson();
            File f = new File(super.getFilesDir(), fname);
            if (!ExistBase(fname)) {
                f.createNewFile();
            }

            if (f.length() != 0) {
                FileReader fr = new FileReader(f);
                BufferedReader bfr = new BufferedReader(fr);
                String str = bfr.readLine();
                filmArray = gson.fromJson(str, ArrayList.class);
                fr.close();
                bfr.close();
            }
            filmArray.add(film);
            String jsonString = gson.toJson(filmArray);
            FileWriter fw = new FileWriter(f, false);   //rewrite
            fw.write(jsonString);
            fw.close();
        }catch (IOException e){e.printStackTrace();}

        Intent intent = new Intent(this, ListActivity.class);
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
