package com.fedukova.task.GSon;

import android.annotation.TargetApi;
import android.os.Build;
import com.fedukova.task.entity.RSSItem;
import com.fedukova.task.entity.RSSItemList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TypeAdapters;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GsonParser {
    public GsonParser() {
    }

    public static ArrayList<RSSItem> takeRssListFromJson(String path)
    {
        ArrayList<RSSItem> items = new ArrayList<>();
        Gson gson = new Gson();
        RSSItemList jsonElements = null;
        try {
            JsonObject jo = extractJsonFromFile(new File(path));
            jsonElements = gson.fromJson(jo, RSSItemList.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        items.addAll(jsonElements.getRssItems());
        return items;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void writeRssListToFile(ArrayList<RSSItem> list, String path) {
        JsonArray arr = new JsonArray();
        JsonObject obj;
        for(RSSItem ri: list) {
            obj = new JsonObject();
            obj.addProperty("title", ri.getTitle());
            obj.addProperty("description", ri.getDescription());
            obj.addProperty("link", ri.getLink());
            arr.add(obj);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static JsonObject extractJsonFromFile(File jsonFile) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(jsonFile)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            int i = sb.indexOf("item");
            sb.replace(0, i - 1, "{\n");
            i = sb.lastIndexOf("]");
            sb.replace(i + 1, sb.length() - 1, "\n}");
        }
        return (JsonObject) TypeAdapters.JSON_ELEMENT.fromJson(sb.toString());
    }

}