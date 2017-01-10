package com.fedukova.task.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.fedukova.task.daolayer.DaoRss;
import com.fedukova.task.daolayer.HelperFactory;
import com.fedukova.task.entity.RssItem;
import com.fedukova.task.gson.GsonParser;
import com.fedukova.task.ui.ActionModeCallback;
import com.fedukova.task.ui.RecyclerItemTouchListener;
import com.fedukova.task.ui.RecyclerListener;
import com.fedukova.task.ui.RecyclerViewAdapter;
import com.fedukova.task.R;
import com.fedukova.task.services.DownloadService;
import com.fedukova.task.services.DownloadService_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Fullscreen
@EActivity(R.layout.activity_rss)

@WindowFeature(Window.FEATURE_ACTION_BAR)
public class RssActivity extends AppCompatActivity {

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private List<RssItem> mItems;
    private ActionMode mActionMode;

    private boolean mIsActionModeOn = false;

    @Extra
    String path;

    @ViewById(R.id.toolbar)
    protected Toolbar mToolbar;

    @ViewById(R.id.progress_refr)
    protected ProgressBar mRefreshProgress;

    @ViewById(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @ViewById(R.id.refresh_button)
    protected ImageButton mRefreshButton;

    @Click(R.id.refresh_button)
    public void refreshButton() {
        mRefreshProgress.setVisibility(View.VISIBLE);
        DownloadService_.intent(this).extra("path", path).start();
        mRefreshButton.setEnabled(false);
    }

    @Receiver(actions = "com.fedukova.task.services.result", registerAt = Receiver.RegisterAt.OnStartOnStop)
    void takeResult(@Receiver.Extra int result, Context context) {
        if (result == DownloadService.FILE_DOWNLOAD_SUCSESS) {
            mItems.clear();
            try {
                mItems.addAll(GsonParser.takeRssListFromJson(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mAdapter.setRssItems(mItems);
        } else startDialog(result);
        mRefreshButton.setEnabled(true);
        mRefreshProgress.setVisibility(View.INVISIBLE);
    }

    @AfterViews
    protected void init() {
        HelperFactory.setHelper(this);
        getSupportActionBar().hide();
        try {
            DaoRss daoRss = HelperFactory.getHelper().getRSSDao();
            mItems = daoRss.getAllItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(RssActivity.this, mRecyclerView, new RecyclerListener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mIsActionModeOn)
                    onListItemSelect(position);
                else {
                    Uri address = Uri.parse(mItems.get(position).getLink());
                    Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                    view.getContext().startActivity(openlinkIntent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                if (!mIsActionModeOn) onListItemSelect(position);
            }
        }));
        mAdapter.setRssItems(mItems);
    }

    private void onListItemSelect(int position) {
        mAdapter.toggleSelection(position);//Toggle the selection
        boolean hasCheckedItems = mAdapter.isAnyItemSelected();//Check if any items are already selected or not
        if (hasCheckedItems && !mIsActionModeOn) {// there are some selected items, start the actionMode
            mIsActionModeOn = true;
            mToolbar.setVisibility(View.GONE);
            mActionMode = startSupportActionMode(new ActionModeCallback(RssActivity.this) {
                @Override
                public void onFinishActionMode() {
                    mIsActionModeOn = false;
                    mAdapter.removeSelection();
                    mToolbar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onDeleteItems(Context context) {
                    AlertDialog.Builder confirmDialog = new AlertDialog.Builder(context);
                    confirmDialog.setTitle(R.string.delete)
                            .setMessage(R.string.delete_ask)
                            .setCancelable(true)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    List<Integer> positions = new ArrayList<Integer>();
                                    positions.addAll(mAdapter.getSelectedPositions());
                                    int count = deleteFromDb(positions);
                                    mAdapter.deleteItemsFromAdapter();
                                    deletedItemsDialog(count, positions.size());
                                    mActionMode.finish();

                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mActionMode.finish();
                                }
                            });
                    confirmDialog.create();
                    confirmDialog.show();
                }
            });
        } else if (!hasCheckedItems && mIsActionModeOn)  // there no selected items, finish the actionMode
            mActionMode.finish();
        if (mActionMode != null) //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(mAdapter.getSelectedCount()) + getResources().getString(R.string.selected));
    }

    @Override
    public void onPause() {
        HelperFactory.releaseHelper();
        super.onPause();
    }

    private int deleteFromDb(List<Integer> pos) {
        int count = 0;
        for (int i = 0; i < pos.size(); i++) {
            try {
                DaoRss daoRss = HelperFactory.getHelper().getRSSDao();
                count += daoRss.deleteItem(mItems.get(pos.get(i)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    private void startDialog(int var) {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setTitle(R.string.title);
        switch (var) {
            case DownloadService.INTERNET_CONNECTION_FAIL:
                confirmDialog.setMessage(R.string.no_internet);
                break;
            case DownloadService.FILE_DOWNLOAD_FAIL:
                confirmDialog.setMessage(R.string.write_file_err);
                break;
        }
        confirmDialog.setNeutralButton(R.string.ok, null);
        confirmDialog.create();
        confirmDialog.show();
    }

    private void deletedItemsDialog(int countDeleted, int countAll) {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setTitle(R.string.delete_dialog_title)
                .setMessage(String.valueOf(countDeleted) + " " + getResources().getString(R.string.from)
                        + " " + String.valueOf(countAll) + " " + getResources().getString(R.string.items_deleted));
        confirmDialog.setNeutralButton(R.string.ok, null);
        confirmDialog.create();
        confirmDialog.show();
    }
}
