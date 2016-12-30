package com.fedukova.task.UI;


import android.content.Context;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fedukova.task.R;
import com.fedukova.task.activities.RssActivity;

public class ActionModeCallback implements ActionMode.Callback{

    private Context context;
    private RecyclerViewAdapter rvAdapter;

    public ActionModeCallback(Context context, RecyclerViewAdapter rvAdapter) {
        this.context = context;
        this.rvAdapter = rvAdapter;
    }
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.action_mode_menu, menu);
        RssActivity.isActionModeOn = true;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        rvAdapter.deleteRows();
        mode.finish();
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mode = null;
        rvAdapter.removeSelection();
        RssActivity.isActionModeOn = false;


    }
}
