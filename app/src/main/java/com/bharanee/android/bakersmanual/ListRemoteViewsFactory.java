package com.bharanee.android.bakersmanual;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context context;
    public static int itemId=0;

    private static final String TAG="ON DATA CHANGED";
    private  static ArrayList<String> data=new ArrayList<>();

    public  static void setData(ArrayList<String> d,int item) {
        data=null;
        data=d;
        itemId=item;

    }


    ListRemoteViewsFactory(Context context) {
        this.context = context;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        Log.v(TAG,data.size()+" size ");
    }


    @Override
    public void onDestroy() {
    if (data!=null)
    {data.clear();data=null;}
    }

    @Override
    public int getCount() {
        Log.v(TAG,itemId+" ITEMID");
        if (data==null)return 0;
        else
            return data.size();

    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews= new RemoteViews(context.getPackageName(),R.layout.widget_list_item);
        String info=data.get(position);
        remoteViews.setTextViewText(R.id.ingredient_item,info);
        Intent activityIntent=new Intent();
        activityIntent.putExtra("position",ListRemoteViewsFactory.itemId);
        activityIntent.putExtra("fragment",context.getString(R.string.type_steps_short));
        remoteViews.setOnClickFillInIntent(R.id.widget_item_container,activityIntent);

       // Log.v(TAG,data.get(position));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
