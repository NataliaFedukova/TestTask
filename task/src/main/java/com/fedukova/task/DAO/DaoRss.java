package com.fedukova.task.DAO;

import com.fedukova.task.entity.RssItem;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class DaoRss extends BaseDaoImpl<RssItem, Long> {

    protected DaoRss(ConnectionSource connectionSource,
                     Class<RssItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
    public List<RssItem> getAllItems() throws SQLException {
        return this.queryForAll();
    }

    public int setAllItems(List<RssItem> items) throws SQLException {
        int count = 0;
        for(int i = 0; i < items.size(); i++){
            RssItem item = (RssItem) items.get(i);
            int res = this.create(item);
            if(res == 1 )
                count++;
        }
        return count;
    }

    public int deleteAllItems() throws SQLException {
        List<RssItem> items = this.queryForAll();
        if(items.isEmpty()) return 0;
        return this.delete(items);
    }

    public int deleteItem(RssItem item) throws SQLException {
        return this.delete(item);

    }

}
