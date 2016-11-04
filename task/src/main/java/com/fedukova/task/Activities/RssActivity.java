package com.fedukova.task.Activities;

import android.app.Activity;
import android.view.Window;

import com.fedukova.task.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.WindowFeature;
//http://www.nasa.gov/rss/image_of_the_day.rss
/**
 * Created by Хозяюшка on 03.11.2016.
 */
@Fullscreen
@EActivity(R.layout.activity_rss)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class RssActivity extends Activity {

}
