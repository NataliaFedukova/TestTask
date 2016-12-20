package com.fedukova.task.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**class represent a content of
 *
 */
@DatabaseTable(tableName = "rss")
public class RSSItem {

    /*@DatabaseField(generatedId = true)
    private Long id;*/

    @DatabaseField(id = true, dataType = DataType.STRING)
    private String title;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String link;

    @DatabaseField(dataType = DataType.STRING)
    private String description;

    public RSSItem(){ }

    public RSSItem(String title, String link,String description){
        this.title = title;
        this.link = link;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setLink(String link) {this.link = link;}
    public String getLink(){ return link;}

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
}