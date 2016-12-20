package com.fedukova.task.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.ImageButton;

import com.fedukova.task.GSon.GsonParser;
import com.fedukova.task.entity.RSSItem;
import com.fedukova.task.R;
import com.fedukova.task.UI.RecyclerViewAdapter;

import com.fedukova.task.services.DownloadService;
import com.fedukova.task.services.DownloadService_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import java.sql.SQLException;
import java.util.ArrayList;

@Fullscreen
@EActivity(R.layout.activity_rss)
@WindowFeature(Window.FEATURE_ACTION_BAR)
public class RssActivity extends Activity {

    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    ArrayList<RSSItem> items;

    @Extra
    String path;

    @ViewById(R.id.recycler_view)
    protected RecyclerView listRecycleView;

    @ViewById(R.id.refresh_button)
    public ImageButton refButton;

    @Click(R.id.refresh_button)
    public void refreshButton(){
        DownloadService_.intent(this).extra("path", path).start();
        refButton.setEnabled(false);
    }

    @Receiver(actions = "com.fedukova.task.services.result",registerAt = Receiver.RegisterAt.OnStartOnStop)
    void takeResult(@Receiver.Extra int result, Context context){
        if(result == DownloadService.FILE_DOWNLOAD_SUCSESS) {
            items.clear();
            items.addAll(GsonParser.takeRssListFromJson(path));
            listAdapter.notifyDataSetChanged();
        }
        else
            startDialog(result);
        refButton.setEnabled(true);
    }

    private void startDialog(int var) {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        switch (var) {
            case DownloadService.INTERNET_CONNECTION_FAIL:
                confirmDialog.setTitle(R.string.no_internet)
                        .setMessage(R.string.internet_message)
                        .setCancelable(true)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                wifi.setWifiEnabled(true);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                case DownloadService.FILE_DOWNLOAD_FAIL:
                    confirmDialog.setTitle(R.string.write_file_err)
                            .setCancelable(true)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                    wifi.setWifiEnabled(true);
                                }
                            });
        }
        confirmDialog.show();

    }

    @AfterViews
    protected void initList(){
        items = new ArrayList<>();
                try {
            items.addAll((ArrayList<RSSItem>)DashboardActivity.rssCrud.read());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listLayoutManager = new LinearLayoutManager(this);
        listRecycleView.setLayoutManager(listLayoutManager);
        listAdapter = new RecyclerViewAdapter(items);
        listRecycleView.setAdapter(listAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
