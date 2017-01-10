package com.fedukova.task.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RssItemList {

    @SerializedName("item")
    private List<RssItem> rssItems;

    public List<RssItem> getRssItems() {
        return rssItems;
    }

}
