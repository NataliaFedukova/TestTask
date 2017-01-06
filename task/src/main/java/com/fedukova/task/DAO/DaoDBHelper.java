package com.fedukova.task.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fedukova.task.R;
import com.fedukova.task.entity.RSSItem;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DaoDBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "RSSdb";
    private static final int DB_VERSION = 1;

    private DaoRss rssDao = null;

    DaoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RSSItem.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, RSSItem.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DaoRss getRSSDao() throws SQLException {
        if(rssDao == null)  rssDao = new DaoRss(getConnectionSource(), RSSItem.class);;
        return rssDao;
    }

    @Override
    public void close() {
        super.close();
        rssDao = null;
    }

}
