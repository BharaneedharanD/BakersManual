package com.bharanee.android.bakersmanual;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import NetworkCall.NetworkTasks;


public class DetailsPage extends AppCompatActivity implements ArrayListFragment.onitemclickedListener {
private static int itemId=-1;
public static String fragmentType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        if (savedInstanceState==null) {
            Intent dataIntent = getIntent();
            if (dataIntent != null) {
                fragmentType = dataIntent.getStringExtra("fragment");
                if (fragmentType.equals("steps_short")) {
                    itemId = dataIntent.getIntExtra("position", 0);
                    String fragmentId = itemId + fragmentType;
                    ArrayListFragment fragment = (ArrayListFragment) getFragmentManager().findFragmentByTag(fragmentId);
                    if (fragment == null) {
                        fragment = new ArrayListFragment();
                        fragment.setData(NetworkTasks.items.get(itemId).getStepDescription(), itemId);
                        getFragmentManager().beginTransaction().add(R.id.fragment_recycler_views, fragment, fragmentId).commit();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentType.equals("ingredients")){
            fragmentType="steps_short";
            String fragmentId=itemId+fragmentType;
            ArrayListFragment fragment= (ArrayListFragment) getFragmentManager().findFragmentByTag(fragmentId);
            if (fragment==null){fragment=new ArrayListFragment();}
                fragment.setData(NetworkTasks.items.get(itemId).getStepDescription(),itemId);
                fragment.changeData();
                }
        else if (fragmentType.equals("Detailed_Steps")){
            fragmentType="steps_short";
            String fragmentId=itemId+fragmentType;
            ArrayListFragment fragment= (ArrayListFragment) getFragmentManager().findFragmentByTag(fragmentId);
            if (fragment==null){fragment=new ArrayListFragment();}
            fragment.setData(NetworkTasks.items.get(itemId).getStepDescription(),itemId);
            getFragmentManager().beginTransaction().replace(R.id.fragment_recycler_views,fragment,fragmentId).commit();
        }
        else
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
                super.onDestroy();
    }

    @Override
    public void onItemSelected(int itemId, int stepPosition) {
        //Load video step fragment
        fragmentType="Detailed_Steps";
        String fragmentId=itemId+fragmentType+stepPosition;
        DetailStepFragment detailStepFragment=new DetailStepFragment();
        detailStepFragment.setData(itemId,stepPosition,this);
        getFragmentManager().beginTransaction().replace(R.id.fragment_recycler_views,detailStepFragment,fragmentId).commit();

    }
}
