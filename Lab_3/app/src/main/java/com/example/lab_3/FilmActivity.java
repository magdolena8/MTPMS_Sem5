package com.example.lab_3;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

public class FilmActivity extends Activity {
    private Film film;
    TextView titleTW, directorTW, raitingTW;
    ImageView filmImageView;

//    EditText mailEdit, phoneEdit, vkEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        verifyPhonePermissons(this);
        film = (Film) getIntent().getSerializableExtra("film");

        titleTW = findViewById(R.id.titleTW);
        directorTW = findViewById(R.id.directorTW);
        raitingTW = findViewById(R.id.raitingTW);
        filmImageView = findViewById(R.id.filmImageView);

        titleTW.setText(film.title);
        directorTW.setText(film.director);
        raitingTW.setText(Float.toString(film.rating));
        filmImageView.setImageBitmap(BitmapFactory.decodeFile(film.image));

    }
    public void onListBtnClick(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
    public void onAddNewBtnCLick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onEmailBtnClick(View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + film.email));
        startActivity(intent);
    }
    public void onVKBtnClick(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://vk.com/"+film.Vk));
        startActivity(intent);
    }
    public void onPhoneBtnClick(View view){
        try{
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:" + film.phone));
            startActivity(phoneIntent);
        }
        catch (Exception ex){
            verifyPhonePermissons(this);
        }
    }


    public static void verifyPhonePermissons(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1
            );
        }
    }
}
