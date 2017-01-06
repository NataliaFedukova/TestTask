package com.fedukova.task.DAO;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class HelperFactory{

    private static DaoDBHelper sDatabaseHelper;

    public static DaoDBHelper getHelper(){
        return sDatabaseHelper;
    }
    public static void setHelper(Context context){
        sDatabaseHelper = OpenHelperManager.getHelper(context, DaoDBHelper.class);
    }
    public static void releaseHelper(){
        if(sDatabaseHelper != null) {
            OpenHelperManager.releaseHelper();
            sDatabaseHelper = null;
        }
    }
}