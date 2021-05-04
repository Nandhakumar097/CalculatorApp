package com.example.calculatorapp;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<HistoryData> main;
    private Activity context;
    private ItemListener mListener;

    public HistoryAdapter(List<HistoryData> main, Activity context,ItemListener mListener) {
        this.main = main;
        this.context = context;
        this.mListener = mListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.expression.setText(main.get(position).getExpression());
        holder.total.setText("= "+main.get(position).getTotal());
        holder.total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFSelected(main.get(position));
                holder.total.setTextColor(context.getResources().getColor(R.color.gold));
                android.os.Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    public void run() {
                        holder.total.setTextColor(context.getResources().getColor(R.color.white));
                    }
                }, 200);

            }
        });
    }

    @Override
    public int getItemCount() {
        return main.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView expression,total;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expression = itemView.findViewById(R.id.expression);
            total = itemView.findViewById(R.id.total);

        }
    }
    public interface ItemListener {
        void onFSelected(HistoryData data);
    }
}
