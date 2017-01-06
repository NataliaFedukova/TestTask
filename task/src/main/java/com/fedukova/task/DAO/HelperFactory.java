package com.fedukova.task.DAO;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class HelperFactory{

    private static DaoDBHelper databaseHelper;

    public static DaoDBHelper getHelper(){
        return databaseHelper;
    }
    public static void setHelper(Context context){
        databaseHelper = OpenHelperManager.getHelper(context, DaoDBHelper.class);
    }
    public static void releaseHelper(){
        if(databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}