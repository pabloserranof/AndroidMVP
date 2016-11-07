package com.pabloserrano.androidmvp.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String id;
    private String description;
    private String date;
    private List<String> tags = new ArrayList<>();
    private String title;

    @SerializedName("image")
    private String imageURL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", tags=" + tags +
                ", title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
