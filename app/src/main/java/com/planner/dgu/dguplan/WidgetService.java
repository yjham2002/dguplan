package com.planner.dgu.dguplan;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new WidgetViewFactory(this.getApplicationContext(), intent));
    }
}