package com.spendshare.dao.spendshare.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.spendshare.dao.spendshare.R;
import com.spendshare.dao.spendshare.model.Box;

import java.util.ArrayList;

/**
 *  光交箱RecyclerView的适配器
 */
public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.BoxHolder> {

    private ArrayList<Box> boxs;
    private BoxAdapter.ItemClickListener listener;

    public BoxAdapter(ArrayList<Box> boxs) {
        this.boxs = boxs;
    }

    @Override
    public BoxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box, parent, false);
        return new BoxHolder(view);
    }

    @Override
    public void onBindViewHolder(final BoxAdapter.BoxHolder holder, final int position) {
        holder.box_address.setText(boxs.get(position).getBoxAddress());
        holder.status.setText(boxs.get(position).getStatus());
        holder.check_man_name.setText(boxs.get(position).getCheckManName());
        holder.check_date.setText("日期：" + boxs.get(position).getCheckDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, position, v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return boxs.size();
    }

    public void setOnItemClickListener() {
        setOnItemClickListener();
    }

    public void setOnItemClickListener(BoxAdapter.ItemClickListener listener) {
        this.listener = listener;
    }

    class BoxHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView box_address;
        private TextView status;
        private AppCompatTextView check_man_name;
        private TextView check_date;

        public BoxHolder (View itemView) {
            super(itemView);
            box_address = itemView.findViewById(R.id.box_address);
            status = itemView.findViewById(R.id.status);
            check_man_name = itemView.findViewById(R.id.check_man_name);
            check_date = itemView.findViewById(R.id.check_date);
        }
    }

    interface ItemClickListener {
        void onItemClick(View v, int position, int id);
    }
}
