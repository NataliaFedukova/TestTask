package com.fedukova.task.DAO;

import android.content.Context;

import com.fedukova.task.entity.RSSItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RssCrud implements Crud{

    private DaoDBHelper helper;

    public RssCrud(Context context) {
        helper = new DaoDBHelper(context);
    }
    @Override
    public int create(ArrayList<?> list) throws SQLException {
        int count = 0;
        for(int i = 0; i < list.size(); i++){
            RSSItem item = (RSSItem) list.get(i);
            int res = helper.getRSSDao().create(item);
            if(res == 1 )
                count++;
        }
        return count;
    }

    @Override
    public ArrayList<?> read() throws SQLException {
        ArrayList<RSSItem> list = new ArrayList<>();
        List<RSSItem> rs = helper.getRSSDao().queryForAll();
        list.addAll(rs);
        return list;
    }

    @Override
    public int update(Objects ob) {
        return 0;
    }

    @Override
    public int delete(ArrayList<?> list) throws SQLException {
        int count = 0;
        for(int i = 0; i < list.size(); i++){
            RSSItem item = (RSSItem) list.get(i);
            int res = helper.getRSSDao().delete(item);
            count++;
        }
        return count;
    }
    public int clear() throws SQLException {
        ArrayList<RSSItem> list = new ArrayList<>();
        List<RSSItem> rs = helper.getRSSDao().queryForAll();
        list.addAll(rs);
        if (list.size() != 0) {
            int count = helper.getRSSDao().delete(list);
            return count;
        }
        return 0;
    }

}
