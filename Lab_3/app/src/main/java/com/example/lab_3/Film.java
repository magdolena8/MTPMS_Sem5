package com.example.lab_3;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Film implements Serializable {
    public String title = null;
    public String director = null;
    public String genre = null;
    public Date viewDate = null;
    public String country = null;
    public String tagline = null;
    public String description = null;
    public boolean isLocalized = true;
    public float rating = 0;
    public String email = null;
    public String phone = null;
    public String Vk = null ;
    public String image = null;

    public Film(String title, String director, String genre, Date viewDate,
                String country, String tagline, String description,
                boolean isLocalized, float rating, String email, String phone, String Vk, String image){
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.viewDate = viewDate;
        this.country = country;
        this.tagline = tagline;
        this.description = description;
        this.isLocalized = isLocalized;
        this.rating = rating;
        this.email = email;
        this.phone = phone;
        this.Vk = Vk;
        this.image = image;
    }
    public Film() {}

    public void setTitle(String title){this.title = title;}
    public void setDirector(String director){this.director = director;}
    public void setGenre(String genre){this.genre = genre;}
    public void setDate(Date date){this.viewDate = date;}
    public void setCountry(String country){this.country = country;}
    public void setTagline(String tagline){this.tagline = tagline;}
    public void setDescription(String description){this.description = description;}
    public void setIsLocalized(boolean flag){this.isLocalized = flag;}
    public void setRating(float rating){this.rating = rating;}

    public String getTitle(){return this.title;}
    public String getDirector(){return this.director;}
    public String getGenre(){return this.genre;}
    public Date getDate(){return this.viewDate;}
    public String getCountry(){return this.country;}
    public String getTagline(){return this.tagline;}
    public String getDescription(){return this.description;}
    public Boolean getIsLocalized(){return this.isLocalized;}
    public Float getRating(){return this.rating;}

//    public static void Serialize(File dir, ArrayList<Film> filmArray){
//        try{
//            Gson gson = new Gson();
//            FileWriter fw = new FileWriter(new File(dir, ".json"));
//            String json = gson.toJson(Film.collection);
//            gson.toJson(Film.collection, fw);
//            fw.close();
//        }
//        catch(Exception ex){
//
//        }
//
//    }


    @Override
    public String toString() {
        return title.toUpperCase() + "\n" + director;
    }

}

