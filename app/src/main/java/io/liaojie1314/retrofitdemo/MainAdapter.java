package io.liaojie1314.retrofitdemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<MainData> mDataArrayList;
    private Activity mActivity;

    public MainAdapter(Activity activity,ArrayList<MainData> dataArrayList) {
        this.mDataArrayList = dataArrayList;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainData data = mDataArrayList.get(position);
        Glide.with(mActivity).load(data.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImageView);
        holder.mTextView.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return mDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextView = itemView.findViewById(R.id.text_view);
        }
    }
}
