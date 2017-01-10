package com.fedukova.task.ui;

import android.content.Context;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fedukova.task.R;

public abstract class ActionModeCallback implements ActionMode.Callback {

    private Context mContext;

    public ActionModeCallback(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.action_mode_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        onDeleteItems(mContext);
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mode = null;
        onFinishActionMode();
    }

    public abstract void onFinishActionMode();

    public abstract void onDeleteItems(Context context);
}
