package com.fedukova.task.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * class represent a content of
 */
@DatabaseTable(tableName = "rss")
public class RssItem {

    @DatabaseField(id = true, dataType = DataType.STRING)
    private String title;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String link;

    @DatabaseField(dataType = DataType.STRING)
    private String description;

    public RssItem() {
    }

    public RssItem(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
