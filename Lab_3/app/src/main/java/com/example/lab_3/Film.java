package com.example.lab_3;

import java.io.Serializable;
import java.util.Date;

public class Film implements Serializable {
    private String title;
    private String director;
    private String genre;
    private Date viewDate;
    private String country;
    private String tagline;
    private String description;
    private boolean isLocalized;
    private float rating;

    public Film(String title, String director, String genre, Date viewDate,
                String country, String tagline, String description,
                boolean isLocalized, float rating){
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.viewDate = viewDate;
        this.country = country;
        this.tagline = tagline;
        this.description = description;
        this.isLocalized = isLocalized;
        this.rating = rating;
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


}

