package com.fedukova.task.UI;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.fedukova.task.R;

//доступ к элементам пункта списка
public class RSSItemView extends RecyclerView.ViewHolder {
    TextView titleTextView;
    TextView descriptionTextView;
    WebView snapshotView;

    public RSSItemView(View itemView) {
        super(itemView);
        titleTextView = (TextView)itemView.findViewById(R.id.title_text_view);
        descriptionTextView = (TextView)itemView.findViewById(R.id.description_text_view);
        //snapshotView = (WebView)itemView.findViewById(R.id.preview_web_view);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClick", "open in browser");

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("OnLongClick", "open select mode");
                return false;
            }
        });
    }
}
