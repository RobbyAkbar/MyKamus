package es.esy.android_inyourhand.mykamus.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import es.esy.android_inyourhand.mykamus.CustomOnItemClickListener;
import es.esy.android_inyourhand.mykamus.DetailActivity;
import es.esy.android_inyourhand.mykamus.models.KamusEngModel;

import static es.esy.android_inyourhand.mykamus.DetailActivity.EXTRA_DETAIL;
import static es.esy.android_inyourhand.mykamus.DetailActivity.EXTRA_WORD;

/**
  Created by robby on 31/12/17.
 */

public class EnglishAdapter extends RecyclerView.Adapter<EnglishAdapter.EnglishHolder> {
    private ArrayList<KamusEngModel> kamusEngModels = new ArrayList<>();
    private Activity activity;

    public EnglishAdapter(Activity activity) {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public EnglishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new EnglishHolder(view);
    }

    public void addItem(ArrayList<KamusEngModel> kamusEngModels){
        this.kamusEngModels = kamusEngModels;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(EnglishHolder holder, int position) {
        holder.tvWord.setText(kamusEngModels.get(position).getWord());
        holder.tvWord.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra(EXTRA_WORD, kamusEngModels.get(position).getWord());
                intent.putExtra(EXTRA_DETAIL, kamusEngModels.get(position).getDescription());
                activity.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return kamusEngModels.size();
    }

    class EnglishHolder extends RecyclerView.ViewHolder {
        private TextView tvWord;
        EnglishHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(android.R.id.text1);
        }
    }
}
