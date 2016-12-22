package com.fedukova.task.UI;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.fedukova.task.R;

//доступ к элементам пункта списка
public class RSSItemView extends RecyclerView.ViewHolder {
    TextView titleTextView;
    TextView descriptionTextView;
    WebView snapshotView;
    View topView;
    View webTopView;

    public RSSItemView(View itemView) {
        super(itemView);
        titleTextView = (TextView)itemView.findViewById(R.id.title_text_view);
        descriptionTextView = (TextView)itemView.findViewById(R.id.description_text_view);
        snapshotView = (WebView)itemView.findViewById(R.id.preview_web_view);
        topView = (View)itemView.findViewById(R.id.top_view);
        webTopView = (View)itemView.findViewById(R.id.web_top_view);
        webTopView.setOnClickListener(null);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri address = Uri.parse(snapshotView.getOriginalUrl());
                Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                v.getContext().startActivity(openlinkIntent);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        topView.setOnClickListener(null);


    }

}
