package com.fedukova.task.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fedukova.task.entity.RssItem;
import com.fedukova.task.R;

import java.util.ArrayList;
import java.util.List;

//работа со списком
public class RecyclerViewAdapter extends RecyclerView.Adapter<RssItemView> {

    private List<RssItem> mContent;
    private SparseBooleanArray mSelectedItemsIds;
    private Context context;


    public RecyclerViewAdapter() {
        mContent = new ArrayList<>();
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public RssItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_list_item, parent, false);
        return new RssItemView(view);
    }

    @Override
    public void onBindViewHolder(final RssItemView holder, final int position) {
        final RssItem item = mContent.get(position);
        holder.titleTextView.setText(Html.fromHtml(item.getTitle()));
        holder.descriptionTextView.setText(item.getDescription());
        holder.snapshotView.loadUrl(item.getLink());
        boolean isItemSelected = mSelectedItemsIds.get(position);
        holder.itemView.setSelected(isItemSelected);
    }

    public void setRssItems(List<RssItem> newContent) {
        if (!mContent.isEmpty()) {
            mContent.clear();
        }
        mContent.addAll(newContent);
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

    public boolean isAnyItemSelected() {
        return getSelectedCount() > 0;
    }

    //Return all selected ids
    private SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }

    //Delete selected rows
    public void deleteItemsFromAdapter() {
        SparseBooleanArray selected = getSelectedIds();//Get selected ids
        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                int location = selected.keyAt(i);
                mContent.remove(location);
                notifyItemRemoved(location);//notify adapter
            }
        }
    }

    public List<Integer> getSelectedPositions() {
        SparseBooleanArray selected = getSelectedIds();
        List<Integer> positions = new ArrayList<>();
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                positions.add(selected.keyAt(i));
            }
        }
        return positions;
    }

}