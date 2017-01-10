package com.fedukova.task.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.fedukova.task.R;

public class RssItemView extends RecyclerView.ViewHolder{
    TextView titleTextView;
    TextView descriptionTextView;
    WebView snapshotView;
    View webTopView;

    public RssItemView(final View itemView) {
        super(itemView);
        titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
        descriptionTextView = (TextView) itemView.findViewById(R.id.description_text_view);
        snapshotView = (WebView) itemView.findViewById(R.id.preview_web_view);
        webTopView = itemView.findViewById(R.id.web_top_view);
        webTopView.setOnClickListener(null);
    }
}
