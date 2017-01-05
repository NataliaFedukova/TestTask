package com.fedukova.task.gsonm;

import android.annotation.TargetApi;
import android.os.Build;
import com.fedukova.task.entity.RSSItem;
import com.fedukova.task.entity.RSSItemList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TypeAdapters;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GsonParser {

    public static List<RSSItem> takeRssListFromJson(String path) throws IOException {
        List<RSSItem> items = new ArrayList<>();
        Gson gson = new Gson();
        RSSItemList jsonElements = null;
        try {
            JsonObject jo = extractJsonFromFile(new File(path));
            jsonElements = gson.fromJson(jo, RSSItemList.class);
        }
        finally {
        }
        items.addAll(jsonElements.getRssItems());
        return items;
    }


    /*@TargetApi(Build.VERSION_CODES.KITKAT)
    public static void writeRssListToFile(List<RSSItem> list, String path) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Writer writer = null;
        try {
            writer = new FileWriter(path);
            writer.write("{\"item\":[/n");
            for (RSSItem ri : list) {
                String temp = gson.toJson(ri);
                writer.write(temp);
            }
            writer.write("]}");
        }
        finally {
            Log.d("WRITING FILE", "WRITING FILE");
            if(writer != null)
            {
                writer.flush();
                writer.close();
            }
        }
    }
*/
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
            if(i > 1) {
                sb.replace(0, i - 1, "{\n");
                i = sb.lastIndexOf("]");
                sb.replace(i + 1, sb.length() - 1, "\n}");
            }
            bufferedReader.close();
        }
        JsonObject jo = (JsonObject) TypeAdapters.JSON_ELEMENT.fromJson(sb.toString());
        return jo;
    }

}