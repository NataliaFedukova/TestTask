package com.fedukova.task.activities;

import android.app.AlertDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.fedukova.task.daolayer.DaoRss;
import com.fedukova.task.daolayer.HelperFactory;
import com.fedukova.task.entity.RssItem;
import com.fedukova.task.gson.GsonParser;
import com.fedukova.task.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.WindowFeature;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * first activity
 * goToRss - go to rss activity
 * restoreData write json file to database
 */

@Fullscreen
@EActivity(R.layout.activity_main)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class DashboardActivity extends AppCompatActivity {

    private String mPath;
    private static final String FILE_NAME = "rss.json";

    @Click(R.id.go_to_rss)
    protected void goToRss() {
        RssActivity_.intent(this).extra("path", mPath + File.separator + FILE_NAME).start();
    }

    @Click(R.id.go_to_restore)
    protected void restoreData() {
        try {
            List<RssItem> list = GsonParser.takeRssListFromJson(mPath + File.separator + FILE_NAME);
            DaoRss daoRss = HelperFactory.getHelper().getRSSDao();
            daoRss.deleteAllItems();
            int count = daoRss.setAllItems(list);

            startDialog(String.valueOf(count) + " " + getResources().getString(R.string.db_insert));

        } catch (SQLException e) {
            startDialog(getResources().getString(R.string.db_err));
        } catch (IOException e) {
            startDialog(getResources().getString(R.string.file_not_found));
        }
    }


    @Click(R.id.go_to_exit)
    protected void exitApp() {
        this.finish();
    }

    @AfterViews
    protected void init() {
        HelperFactory.setHelper(this);
        mPath = Environment.getExternalStorageDirectory().toString();
    }

    private void startDialog(String m) {
        final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setMessage(m)
                .setCancelable(true).setNeutralButton("OK", null);
        AlertDialog dialog = confirmDialog.create();
        dialog.show();
    }

    @Override
    public void onPause() {
        HelperFactory.releaseHelper();
        super.onPause();

    }

    @Override
    public void onResume() {
        HelperFactory.setHelper(this);
        super.onResume();

    }
}
