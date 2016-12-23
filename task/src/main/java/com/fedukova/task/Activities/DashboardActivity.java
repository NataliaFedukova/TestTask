package com.fedukova.task.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.fedukova.task.DAO.RssCrud;
import com.fedukova.task.GSon.GsonParser;
import com.fedukova.task.R;
import com.fedukova.task.entity.RSSItem;
import com.fedukova.task.services.DownloadService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.WindowFeature;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@Fullscreen
@EActivity(R.layout.activity_main)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class DashboardActivity extends Activity {

    private static final int FILE_NOT_FOUND = 1;


    private String PATH ;
    private static final String FILE_NAME = "rss.json";
    public static RssCrud rssCrud;

    @Touch(R.id.go_to_rss)
    protected void goToRss(){
        RssActivity_.intent(this).extra("path", PATH + File.separator + FILE_NAME).start();
    }

    @Touch(R.id.go_to_restore)
    protected void restoreData() {
        try {
            rssCrud.clear();
            int c = rssCrud.create(GsonParser.takeRssListFromJson(PATH + File.separator + FILE_NAME));
        } catch (SQLException e) {
            startDialog(e.getMessage(), e.getLocalizedMessage());
        } catch (IOException e) {
            startDialog(e.getMessage(), e.getLocalizedMessage());
        }
        Toast.makeText(getApplicationContext(), "Restore from sd, rewrite db",Toast.LENGTH_SHORT).show();}


    @Touch(R.id.go_to_backup)
    protected void makeBackup()  {
        try {
            GsonParser.writeRssListToFile((ArrayList< RSSItem>)rssCrud.read(),PATH + File.separator + FILE_NAME);
        } catch (SQLException e) {
            startDialog(e.getMessage(), e.getLocalizedMessage());
        } catch (IOException e) {
            startDialog(e.getMessage(), e.getLocalizedMessage());
        }
        Toast.makeText(getApplicationContext(), "Save .json from db on sd",Toast.LENGTH_SHORT).show();
    }

    @Touch(R.id.go_to_exit)
    protected void exitApp(){
        Toast.makeText(getApplicationContext(), "Close app",Toast.LENGTH_SHORT).show();
    }

    @AfterViews
    protected void getExtras(){
        PATH = Environment.getExternalStorageDirectory().toString();
        rssCrud = new RssCrud(this);

    }
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/
   private void startDialog(String s, String  t) {
       AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
       confirmDialog.setTitle(t).setMessage(s)
               .setCancelable(true);
       confirmDialog.show();
   }
}
