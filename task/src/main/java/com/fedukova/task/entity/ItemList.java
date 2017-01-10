package com.fedukova.task.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemList {

    @SerializedName("item")
    private List<Item> rssItems;

    public List<Item> getRssItems() {
        return rssItems;
    }

}
