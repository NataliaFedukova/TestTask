package com.fedukova.task.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.fedukova.task.R;
import com.fedukova.task.UI.CustomRelativeLayout;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

@Fullscreen
@EActivity(R.layout.activity_main)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class DashboardActivity extends Activity {
    @Touch(R.id.go_to_rss)
    protected void goToRss(){
        RssActivity_.intent(this).start();
    }

    @Touch(R.id.go_to_restore)
    protected void restoreData() {
        Toast.makeText(getApplicationContext(), "Restore from sd, rewrite db",Toast.LENGTH_LONG).show();}


    @Touch(R.id.go_to_backup)
    protected void makeBackup(){
        Toast.makeText(getApplicationContext(), "Save .json from db to sd",Toast.LENGTH_LONG).show();
    }

    @Touch(R.id.go_to_exit)
    protected void exitApp(){
        Toast.makeText(getApplicationContext(), "Close app",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
