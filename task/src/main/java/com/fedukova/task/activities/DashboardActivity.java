package com.fedukova.task.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
;
import com.fedukova.task.DAO.DaoRss;
import com.fedukova.task.DAO.HelperFactory;
import com.fedukova.task.entity.RssItem;
import com.fedukova.task.gson.GsonParser;
import com.fedukova.task.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.WindowFeature;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/** first activity
 * goToRss - go to rss activity
 * restoreData write json file to database
 */

@Fullscreen
@EActivity(R.layout.activity_main)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class DashboardActivity extends AppCompatActivity {

    private String mPath ;
    private static final String FILE_NAME = "rss.json";
    //private RssCrud rssCrud;

    @Touch(R.id.go_to_rss)
    protected void goToRss(){
        RssActivity_.intent(this).extra("path", mPath + File.separator + FILE_NAME).start();
    }

    @Touch(R.id.go_to_restore)
    protected void restoreData() {
        try {
            List<RssItem> list = GsonParser.takeRssListFromJson(mPath + File.separator + FILE_NAME);
            DaoRss daoRss = HelperFactory.getHelper().getRSSDao();
            daoRss.deleteAllItems();
            int count = daoRss.setAllItems(list);
            //rssCrud.clear();
            //int c = rssCrud.create(GsonParser.takeRssListFromJson(mPath + File.separator + FILE_NAME));
            startDialog(String.valueOf(count) + " from 50 rows insertned");
            //HelperFactory.releaseHelper();
        } catch (SQLException e) {
            startDialog(getResources().getString(R.string.db_err));
        } catch (IOException e) {
            startDialog(getResources().getString(R.string.file_not_found));
        }
        //Toast.makeText(getApplicationContext(), "Restore from sd, rewrite db",Toast.LENGTH_SHORT).show();
        }


    @Touch(R.id.go_to_exit)
    protected void exitApp(){
        this.finish();        //Toast.makeText(getApplicationContext(), "Close app",Toast.LENGTH_SHORT).show();
    }

    @AfterViews
    protected void init(){
        HelperFactory.setHelper(this);
        mPath = Environment.getExternalStorageDirectory().toString();
        //rssCrud = new RssCrud(getApplicationContext());

    }
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/
   private void startDialog(String m) {
       final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
       confirmDialog.setMessage(m)
               .setCancelable(true).setNeutralButton("OK",new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
              dialog.dismiss();
           }
       });
       confirmDialog.show();
   }

    @Override
    public void onPause(){
        HelperFactory.releaseHelper();
        super.onPause();

    }
    @Override
    public void onResume(){
        HelperFactory.setHelper(this);
        super.onResume();

    }
}
