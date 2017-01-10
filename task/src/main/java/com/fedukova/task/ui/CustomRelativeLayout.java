package com.fedukova.task.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fedukova.task.R;

public class CustomRelativeLayout extends PercentRelativeLayout {

    private LayoutInflater mInflater;
    private String mTextValue = "";
    private TextView mTextView;

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributesTypedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomRelativeLayout, 0, 0);
        try {
            mTextValue = attributesTypedArray.getString(R.styleable.CustomRelativeLayout_textValue);
        } finally {
            attributesTypedArray.recycle();
        }
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.r_layout, (ViewGroup) getRootView(), false);
        mTextView = (TextView) view.findViewById(R.id.titleViewId);
        mTextView.setText(mTextValue);
        this.addView(view);
    }

    public String getTitleText() {
        return mTextValue;
    }

    public void setTitleText(String title) {
        this.mTextValue = title;
        if (mTextView != null) {
            mTextView.setText(title);
        }
    }
}
