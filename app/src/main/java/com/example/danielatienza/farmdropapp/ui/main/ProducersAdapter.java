package com.example.danielatienza.farmdropapp.ui.main;

import android.content.Context;

import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.danielatienza.farmdropapp.R;
import com.example.danielatienza.farmdropapp.utils.listener.OnProducerTouchListener;
import com.example.danielatienza.farmdropapp.utils.manager.CallManager;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import greendao.ProducerData;

/**
 * Created by danielatienza on 16/12/2016.
 */

public class ProducersAdapter extends RecyclerView.Adapter<ProducersAdapter.ProducerViewHolder> {

    private List<ProducerData> mProducers;
    private OnProducerTouchListener mOnProducerTouchListener;

    @Inject
    public ProducersAdapter() {
        mProducers = new ArrayList<>();
    }

    public void setOnProducerTouchListener(OnProducerTouchListener onProducerTouchListener) {
        this.mOnProducerTouchListener = onProducerTouchListener;
    }

    public void addProducers(List<ProducerData> producers) {
        mProducers.addAll(producers);
    }

    @Override
    public ProducerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producer, parent, false);
        return new ProducerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProducerViewHolder holder, int position) {
        Context context = holder.imageView.getContext();
        final ProducerData producer = mProducers.get(position);
        holder.nameTextView.setText(producer.getProducerName());
        holder.emailTextView.setText(producer.getProducerDescription());


        if(mOnProducerTouchListener != null) {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /* Define Parameters to make transition (Image View) */
                    Pair<View, String> image =
                            new Pair<View, String>(holder.imageView,
                                    CallManager.IMAGE);

                    /* Calls the touch listener (main presenter) */
                    mOnProducerTouchListener.onProducerTouched(producer.getProducerId(), image);
                }
            });
        }

        /* Image */
        Glide.with(context)
                .load(producer.getProducerImageURL())
                .thumbnail(Glide.with(context).load(producer.getProducerImageURL()).centerCrop())
                .dontAnimate()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mProducers.size();
    }


    public void reset() {
        mProducers.clear();
    }

    public static class ProducerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView root;
        @BindView(R.id.image_view)
        ImageView imageView;
        @BindView(R.id.text_name)
        TextView nameTextView;
        @BindView(R.id.text_description)
        TextView emailTextView;

        public ProducerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}