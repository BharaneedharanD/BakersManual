package Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharanee.android.bakersmanual.R;

import java.util.ArrayList;

import Modal.BakeItem;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {
ArrayList<BakeItem> listItems=null;
private final item_click handle_click;
    public ItemListAdapter(item_click item_click_handler) {
        handle_click=item_click_handler;
    }
    public interface item_click{
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        boolean shouldattachImmeadiately=false;
        View view=inflater.inflate(R.layout.item_image_home,parent,shouldattachImmeadiately);
        ItemListViewHolder holder=new ItemListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        if (listItems.size()>0){
            String itemName=listItems.get(position).getName();
            holder.itemName.setText(itemName);
            itemName=itemName.toLowerCase();
            if (itemName.contains("pie"))
                holder.itemImage.setImageResource(R.drawable.pie);
            else if (itemName.contains("cheesecake"))
                holder.itemImage.setImageResource(R.drawable.cheesecake);
            else if (itemName.contains("brown"))
                holder.itemImage.setImageResource(R.drawable.brownies);
            else
                holder.itemImage.setImageResource(R.drawable.cake);
        }
    }

    @Override
    public int getItemCount() {
        return (listItems==null)?0:listItems.size();
    }

    public void setData(ArrayList<BakeItem> data) {
        if (data!=null&&data.size()>0)
        {
            listItems = data;
        notifyDataSetChanged();
        }

    }

    public class ItemListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final ImageView itemImage;
    public final TextView itemName;
        public ItemListViewHolder(View itemView) {
            super(itemView);
        itemImage=itemView.findViewById(R.id.item_image);
        itemName=itemView.findViewById(R.id.item_txt);
        itemImage.setOnClickListener(this);
        itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clicked_position=getAdapterPosition();
            handle_click.onItemClick(clicked_position);
        }
    }
}
