package com.fedukova.task.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RSSItemList {
    @SerializedName("item")
    private List<RSSItem> rssItems;
    public List<RSSItem> getRssItems(){ return rssItems; }

}
