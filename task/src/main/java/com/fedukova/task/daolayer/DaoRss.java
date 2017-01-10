package com.fedukova.task.daolayer;

import com.fedukova.task.entity.Item;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class DaoRss extends BaseDaoImpl<Item, Long> {

    protected DaoRss(ConnectionSource connectionSource,
                     Class<Item> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Item> getAllItems() throws SQLException {
        return this.queryForAll();
    }

    public int setAllItems(List<Item> items) throws SQLException {
        int count = 0;
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int res = this.create(item);
            if (res == 1)
                count++;
        }
        return count;
    }

    public int deleteAllItems() throws SQLException {
        List<Item> items = this.queryForAll();
        if (items.isEmpty()) return 0;
        return this.delete(items);
    }

    public int deleteItem(Item item) throws SQLException {
        return this.delete(item);

    }

}
