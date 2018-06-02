package com.bharanee.android.bakersmanual;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import NetworkCall.NetworkTasks;


public class DetailsPage extends AppCompatActivity implements ArrayListFragment.onitemclickedListener {
private static int itemId=-1;
public static String screenType;
public static String fragmentType;
    private DetailStepFragment detailStepFragment;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.itemIdParam),itemId);
        outState.putString(getString(R.string.screenTypeParam),screenType);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        if (savedInstanceState==null) {
            Intent dataIntent = getIntent();
            if (dataIntent != null) {
                View v=findViewById(R.id.screenView);
                screenType= (String) v.getTag();
                fragmentType=dataIntent.getStringExtra(getString(R.string.fragmentParam));
                itemId=dataIntent.getIntExtra(getString(R.string.positionParam),0);
                if (screenType.equals(getString(R.string.screenType_tablet))){
                    //single page master layout
                    ArrayListFragment ingredientsFragmet = new ArrayListFragment();
                        ingredientsFragmet.setData(NetworkTasks.items.get(itemId).getIngredients(), itemId,getString(R.string.type_ingredients));
                        getFragmentManager().beginTransaction().add(R.id.ingredients_frame, ingredientsFragmet).commit();
                    ArrayListFragment shortStepsFragmet = new ArrayListFragment();
                    shortStepsFragmet.setData(NetworkTasks.items.get(itemId).getStepDescription(), itemId,getString(R.string.type_steps_short));
                    getFragmentManager().beginTransaction().add(R.id.steps_frame, shortStepsFragmet).commit();
                     detailStepFragment=new DetailStepFragment();
                    detailStepFragment.setData(itemId,1,this);
                    getFragmentManager().beginTransaction().add(R.id.videoFrame,detailStepFragment).commit();
                }else {
                    if (fragmentType.equals(getString(R.string.type_steps_short))) {
                        String fragmentId = itemId + fragmentType;
                        ArrayListFragment fragment = (ArrayListFragment) getFragmentManager().findFragmentByTag(fragmentId);
                        if (fragment == null) {
                            fragment = new ArrayListFragment();
                            fragment.setData(NetworkTasks.items.get(itemId).getStepDescription(), itemId,fragmentType);
                            getFragmentManager().beginTransaction().add(R.id.fragment_recycler_views, fragment, fragmentId).commit();
                        }
                    }
                }
            }
        }else{
            screenType=savedInstanceState.getString(getString(R.string.screenTypeParam));
            itemId=savedInstanceState.getInt(getString(R.string.itemIdParam));
            if (screenType.equals(getString(R.string.screenType_tablet))){

                detailStepFragment=new DetailStepFragment();
                detailStepFragment.setData(itemId,1,this);
                getFragmentManager().beginTransaction().replace(R.id.videoFrame,detailStepFragment).commit();
            }
        }
        android.app.ActionBar bar=getActionBar();
        bar.setTitle(NetworkTasks.items.get(itemId).getName());
    }


    @Override
    public void onBackPressed() {
        if (screenType.equals(getString(R.string.screenType_tablet))){
            super.onBackPressed();return;
        }
        if (fragmentType.equals(getString(R.string.type_ingredients))){
            fragmentType=getString(R.string.type_steps_short);
            String fragmentId=itemId+fragmentType;
            ArrayListFragment fragment= (ArrayListFragment) getFragmentManager().findFragmentByTag(fragmentId);
            if (fragment==null){fragment=new ArrayListFragment();}
                fragment.setData(NetworkTasks.items.get(itemId).getStepDescription(),itemId,fragmentType);
                fragment.changeData();
                }
        else if (fragmentType.equals(getString(R.string.type_Detailed_Steps))){
            fragmentType=getString(R.string.type_steps_short);
            String fragmentId=itemId+fragmentType;
            ArrayListFragment fragment= (ArrayListFragment) getFragmentManager().findFragmentByTag(fragmentId);
            if (fragment==null){fragment=new ArrayListFragment();}
            fragment.setData(NetworkTasks.items.get(itemId).getStepDescription(),itemId,fragmentType);
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
    public void onItemSelected(int itemId, int stepPosition,String type) {
        //Load video step fragment
        if (type.equals(getString(R.string.type_ingredients)))return;
        fragmentType=getString(R.string.type_Detailed_Steps);
        if (screenType.equals(getString(R.string.screenType_tablet))){
            detailStepFragment.setData(itemId,stepPosition,this);
            detailStepFragment.releasePlayer();
            detailStepFragment.initializePlayer(detailStepFragment.videouri);
        }else {
            String fragmentId = itemId + fragmentType + stepPosition;
            DetailStepFragment detailStepFragment = new DetailStepFragment();
            detailStepFragment.setData(itemId, stepPosition, this);
            getFragmentManager().beginTransaction().replace(R.id.fragment_recycler_views, detailStepFragment, fragmentId).commit();
        }
    }
}
