package com.fedukova.task.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.fedukova.task.entityr.RSSItem;
import com.fedukova.task.R;

import java.util.ArrayList;
import java.util.List;

//работа со списком
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RSSItemView>{

    private List<RSSItem> content;
    private SparseBooleanArray mSelectedItemsIds;
    private Context context;


    public RecyclerViewAdapter() {
        content = new ArrayList<>();
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public RSSItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_list_item, parent, false);
        return new RSSItemView(view);
    }

    @Override
    public void onBindViewHolder(final RSSItemView holder, final int position) {
        final RSSItem item = content.get(position);
        holder.titleTextView.setText(Html.fromHtml(item.getTitle()));
        holder.descriptionTextView.setText(item.getDescription());
        holder.snapshotView.loadUrl(item.getLink());
        holder.itemView.setSelected(mSelectedItemsIds.get(position) ? true : false);
    }

    public void setRssItems(ArrayList<RSSItem> newContent){
        if(!content.isEmpty()){ content.clear();}
        content.addAll(newContent);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    private void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyItemChanged(position);
    }

    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public boolean isAnyItemSelected(){
        return getSelectedCount() > 0;
    }

    //Return all selected ids
    private SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    //Delete selected rows
    public void deleteRows() {
        SparseBooleanArray selected = getSelectedIds();//Get selected ids
        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                int location = selected.keyAt(i);
                content.remove(location);
                notifyItemRemoved(location);//notify adapter
            }
        }
    }

    public class RSSItemView extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        WebView snapshotView;
        View webTopView;

        public RSSItemView(final View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description_text_view);
            snapshotView = (WebView) itemView.findViewById(R.id.preview_web_view);
            webTopView = (View) itemView.findViewById(R.id.web_top_view);
            webTopView.setOnClickListener(null);
        }
    }
}