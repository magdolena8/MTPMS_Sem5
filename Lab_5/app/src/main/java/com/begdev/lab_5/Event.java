package com.begdev.lab_5;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Date;

public class Event implements Serializable {
    static ArrayList<Event> eventsList = new ArrayList<>();
    public String title;
    public String description;
    public Date date;
    public String image;
    public Boolean isChecked;

    public Event (String _title, String _description, Date _date, String _image, Boolean _isChecked){
        this.title = _title;
        this.date = _date;
        this.description = _description;
        this.image = _image;
        this.isChecked = _isChecked;
    }
    public Event (){
        this.title = null;
        this.date = null;
        this.description = null;
        this.image = null;
        this.isChecked = false;
    }

    public static ArrayList<Event> getEventsList(@NonNull Context context) throws IOException {
        Gson gson = new Gson();
        File f = new File(context.getFilesDir(), "data.json");
        if(!f.exists()){
            f.createNewFile();
            return eventsList;
        }
        BufferedReader bfr = new BufferedReader(new FileReader(f));
        eventsList = gson.fromJson( bfr.readLine(), new TypeToken<ArrayList<Event>>(){}.getType());
        bfr.close();
        return eventsList;
    }

    public static boolean serializeEventsList(@NonNull Context context) throws IOException{
        Gson gson = new Gson();
        File f = new File(context.getFilesDir(), "data.json");
        if(!f.exists()){
            f.createNewFile();
        }
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(f));
            String result = gson.toJson(eventsList);
            bfw.write(result);
            bfw.close();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addEvent(Event e, Context context) throws IOException{
        if(e.date != null || e.title != null){
            if (eventsList == null){
                eventsList = new ArrayList<Event>();
            }
            eventsList.add(e);
            return serializeEventsList(context);
        }
        else return false;
    }

}
