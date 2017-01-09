package com.fedukova.task.services;

import android.app.IntentService;
import android.content.Intent;

import com.fedukova.task.business.ConnectionFailException;
import com.fedukova.task.business.DownloadByURL;

import org.androidannotations.annotations.EIntentService;

import java.io.IOException;

@EIntentService
public class DownloadService extends IntentService {

    public static final String URL = "http://scripting.com/rss.json";
    public final static int FILE_DOWNLOAD_SUCSESS = 1;
    public final static int FILE_DOWNLOAD_FAIL = 2;
    public final static int INTERNET_CONNECTION_FAIL = 3;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent backIntent = new Intent();
        backIntent.setAction("com.fedukova.task.services.result");
        String path = intent.getStringExtra("path");
        try {
            DownloadByURL.loadFileOnSD(URL, path);
            backIntent.putExtra("result",FILE_DOWNLOAD_SUCSESS);
        }catch (ConnectionFailException cfe){
            backIntent.putExtra("result",INTERNET_CONNECTION_FAIL);
        }
        catch (IOException e) {
            backIntent.putExtra("result",FILE_DOWNLOAD_FAIL);
        }
        finally {
            sendBroadcast(backIntent);
        }

    }
}
