package com.bharanee.android.bakersmanual;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import Adapters.ItemListAdapter;
import NetworkCall.NetworkTasks;

public class HomeScreen extends AppCompatActivity implements ItemListAdapter.item_click {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        RecyclerView items_lists=findViewById(R.id.items_rv);
        NetworkTasks networkObject=new NetworkTasks(this);
        View v=findViewById(R.id.screenView);
        String screenType= (String) v.getTag();
        ItemListAdapter adapter=new ItemListAdapter(this,this);
        networkObject.parse(adapter);
        if (screenType.equals(getString(R.string.screenType_tablet))||screenType.equals("phone_landscape")){
            GridLayoutManager manager=new GridLayoutManager(HomeScreen.this,2,GridLayoutManager.VERTICAL,false);
            items_lists.setLayoutManager(manager);
        }else if (screenType.equals("tablet_landscape")){
            GridLayoutManager manager=new GridLayoutManager(HomeScreen.this,3,GridLayoutManager.VERTICAL,false);
            items_lists.setLayoutManager(manager);
        }
        else {
            LinearLayoutManager manager = new LinearLayoutManager(HomeScreen.this, LinearLayoutManager.VERTICAL, false);
            items_lists.setLayoutManager(manager);
        }
        items_lists.setHasFixedSize(true);
        items_lists.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        Class activityClass=DetailsPage.class;
        Intent detailsPageActivity=new Intent(this,activityClass);
        detailsPageActivity.putExtra("position",position);
        detailsPageActivity.putExtra("fragment",getString(R.string.type_steps_short));
        startActivity(detailsPageActivity);
    }
}
