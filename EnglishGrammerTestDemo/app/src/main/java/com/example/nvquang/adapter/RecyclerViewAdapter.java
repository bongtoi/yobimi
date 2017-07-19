package com.example.nvquang.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nvquang.englishgrammertestdemo.R;

import java.util.List;

/**
 * Created by NVQuang on 06/07/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Integer> integerList;
    CallBack callBack;

    public RecyclerViewAdapter(List<Integer> integerList, CallBack callBack) {
        this.integerList = integerList;
        this.callBack = callBack;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button btnSTT;

        public MyViewHolder(View v) {
            super(v);
            btnSTT = (Button) v.findViewById(R.id.btnSTT);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        Integer integer = integerList.get(position);
        holder.btnSTT.setText((integerList.get(position)) + "");
        holder.btnSTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.OnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return integerList.size();
    }

    public interface CallBack {
        void OnClick(int index);
    }
}
