package com.fedukova.task.DAO;

import android.content.Context;

import com.fedukova.task.entity.RssItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** NOT USED
 *
 */
public class RssCrud implements Crud{

    private DaoDBHelper mHelper;

    public RssCrud(Context context) {
        mHelper = new DaoDBHelper(context);
    }
    @Override
    public int create(List<?> list) throws SQLException {
        int count = 0;
        for(int i = 0; i < list.size(); i++){
            RssItem item = (RssItem) list.get(i);
            int res = mHelper.getRSSDao().create(item);
            if(res == 1 )
                count++;
        }
        return count;
    }

    @Override
    public List<?> read() throws SQLException {
        ArrayList<RssItem> list = new ArrayList<>();
        List<RssItem> rs = mHelper.getRSSDao().queryForAll();
        list.addAll(rs);
        return list;
    }

    @Override
    public int update(Objects ob) {
        return 0;
    }

    @Override
    public int delete(List<?> list) throws SQLException {
        int count = 0;
        for(int i = 0; i < list.size(); i++){
            RssItem item = (RssItem) list.get(i);
            int res = mHelper.getRSSDao().delete(item);
            count++;
        }
        return count;
    }
    public int clear() throws SQLException {
        List<RssItem> rs = mHelper.getRSSDao().queryForAll();
        int count = 0;
        if (rs.size() != 0) {
            count = mHelper.getRSSDao().delete(rs);
        }
        return count;
    }

}
