package com.fedukova.task.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fedukova.task.entity.RSSItem;
import com.fedukova.task.R;

import java.util.ArrayList;

//работа со списком

public class RecyclerViewAdapter extends RecyclerView.Adapter<RSSItemView> {
    private ArrayList<RSSItem> content;

    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<RSSItem> arrayList) {
        this.context = context;
        this.content = arrayList;
    }

    @Override
    public RSSItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_list_item, parent, false);
        return new RSSItemView(view);
    }

    @Override
    public void onBindViewHolder(RSSItemView holder, int position) {
        final RSSItem item = content.get(position);
        holder.titleTextView.setText(Html.fromHtml(item.getTitle()));
        holder.descriptionTextView.setText(item.getDescription());
        holder.snapshotView.loadUrl(item.getLink());
    }

    public void update(ArrayList<RSSItem> newContent){
        content.clear();
        content.addAll(newContent);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return content.size();
    }
}

