package com.bharanee.android.bakersmanual;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import NetworkCall.NetworkTasks;


public class ArrayListFragment extends ListFragment {
    private String[] listArray=null;
    private int itemId=-1;
    private String type;
    private onitemclickedListener mCallback;
    public ArrayListFragment() {
    }
    public interface onitemclickedListener{
        void onItemSelected(int itemId,int stepPosition);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback= (onitemclickedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement the method");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_lists,container,false);
        return view;
    }
    public void setData(ArrayList<String> listItems,int itemId){
    listArray=listItems.toArray(new String[listItems.size()]);
    this.itemId=itemId;
    type="steps_short";
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!=null)
        {listArray=savedInstanceState.getStringArray("listItems");
        itemId=savedInstanceState.getInt("itemId");
        type=savedInstanceState.getString("type");
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listArray);
        setListAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArray("listItems",listArray);
        outState.putInt("itemId",itemId);
        outState.putString("type",type);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (type.equals("steps_short")){
            if (position==0 ){
            type="ingredients";
            DetailsPage.fragmentType="ingredients";
            listArray=null;
            ArrayList<String> ingredients=NetworkTasks.items.get(itemId).getIngredients();
            listArray= ingredients.toArray(new String[ingredients.size()]);
            changeData();
            }
            else{
                //contact activity
                mCallback.onItemSelected(itemId,position);
            }
        }
    }

    public void changeData(){
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listArray);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



}
