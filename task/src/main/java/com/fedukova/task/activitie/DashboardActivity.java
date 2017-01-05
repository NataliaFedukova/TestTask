package com.fedukova.task.activitie;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.fedukova.task.DAO.RssCrud;
import com.fedukova.task.GSon.GsonParser;
import com.fedukova.task.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.WindowFeature;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Fullscreen
@EActivity(R.layout.activity_main)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class DashboardActivity extends AppCompatActivity {

    private String PATH ;
    private static final String FILE_NAME = "rss.json";
    private RssCrud rssCrud;

    @Touch(R.id.go_to_rss)
    protected void goToRss(){
        RssActivity_.intent(this).extra("path", PATH + File.separator + FILE_NAME).start();
    }

    @Touch(R.id.go_to_restore)
    protected void restoreData() {
        try {

            rssCrud.clear();
            int c = rssCrud.create(GsonParser.takeRssListFromJson(PATH + File.separator + FILE_NAME));
            startDialog(String.valueOf(c) + " from 50 rows insertned");
        } catch (SQLException e) {
            //startDialog(e.getMessage());
        } catch (IOException e) {
            startDialog(e.getMessage());
        }
        Toast.makeText(getApplicationContext(), "Restore from sd, rewrite db",Toast.LENGTH_SHORT).show();}


    @Touch(R.id.go_to_exit)
    protected void exitApp(){
        Toast.makeText(getApplicationContext(), "Close app",Toast.LENGTH_SHORT).show();
    }

    @AfterViews
    protected void getExtras(){
        PATH = Environment.getExternalStorageDirectory().toString();
        rssCrud = new RssCrud(getApplicationContext());

    }
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/
   private void startDialog(String m) {
       final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
       confirmDialog.setMessage(m)
               .setCancelable(true).setPositiveButton("OK",new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
              dialog.dismiss();
           }
       });
       confirmDialog.show();

   }
}
