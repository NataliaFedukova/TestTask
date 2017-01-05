package com.fedukova.task.DAO;

import com.fedukova.task.entity.RSSItem;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class DaoRss extends BaseDaoImpl<RSSItem, Integer> {
    protected DaoRss(ConnectionSource connectionSource,
                     Class<RSSItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

}
