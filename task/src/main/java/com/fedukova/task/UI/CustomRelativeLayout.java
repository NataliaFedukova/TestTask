package com.fedukova.task.UI;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fedukova.task.R;

/**
 * Created by Хозяюшка on 02.11.2016.
 */

public class CustomRelativeLayout extends PercentRelativeLayout{

    private LayoutInflater mInflater;
    //private String mTextValue;

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomRelativeLayout(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context){
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.r_layout, (ViewGroup) getRootView(),false);
        this.addView(view);
    }
}
