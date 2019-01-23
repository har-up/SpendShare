package com.spendshare.dao.spendshare.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.spendshare.dao.spendshare.R;
import com.spendshare.dao.spendshare.model.Task;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {

    private ArrayList<Task> tasks;
    private ItemClickListener listener;
    public TasksAdapter(ArrayList<Task> tasks){
        this.tasks = tasks;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent,false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskHolder holder, final int position) {
        holder.taskName.setText(tasks.get(position).getTaskName());
        holder.status.setText(tasks.get(position).getStatus());
        holder.groupName.setText(tasks.get(position).getGroupName());
        holder.boxNum.setText("光交箱数：" + tasks.get(position).getBoxNum());
        holder.progress.setText(tasks.get(position).getProgress()+ "%");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,position,v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setOnItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    class TaskHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView taskName;
        private TextView status;
        private AppCompatTextView groupName;
        private TextView boxNum;
        private TextView progress;
        public TaskHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            status = itemView.findViewById(R.id.status);
            groupName = itemView.findViewById(R.id.groupName);
            boxNum = itemView.findViewById(R.id.boxNum);
            progress = itemView.findViewById(R.id.progress);
        }
    }

    interface ItemClickListener{
        void onItemClick(View v,int position,int id);
    }
}
