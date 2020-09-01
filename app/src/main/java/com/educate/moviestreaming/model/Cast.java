package com.educate.moviestreaming.model;

import com.google.gson.annotations.SerializedName;

public class Cast {
    String name;
    @SerializedName("profile_path")
    String img_link;

    public Cast(String name, String img_link) {
        this.name = name;
        this.img_link = img_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }
}
