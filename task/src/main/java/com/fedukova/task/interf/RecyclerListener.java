package com.fedukova.task.interf;

import android.view.View;

public interface RecyclerListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
