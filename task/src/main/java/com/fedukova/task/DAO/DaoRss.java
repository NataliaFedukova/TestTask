package com.fedukova.task.DAO;

import com.fedukova.task.entity.RSSItem;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class DaoRss extends BaseDaoImpl<RSSItem, Long> {

    protected DaoRss(ConnectionSource connectionSource,
                     Class<RSSItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
    public List<RSSItem> getAllItems() throws SQLException {
        return this.queryForAll();
    }

    public int setAllItems(List<RSSItem> items) throws SQLException {
        int count = 0;
        for(int i = 0; i < items.size(); i++){
            RSSItem item = (RSSItem) items.get(i);
            int res = this.create(item);
            if(res == 1 )
                count++;
        }
        return count;
    }

    public int deleteAllItems() throws SQLException {
        List<RSSItem> items = this.queryForAll();
        if(items.isEmpty()) return 0;
        return this.delete(items);
    }

    public int deleteItem(RSSItem item) throws SQLException {
        return this.delete(item);

    }

}
