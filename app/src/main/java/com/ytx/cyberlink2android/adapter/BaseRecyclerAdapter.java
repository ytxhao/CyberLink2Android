package com.ytx.cyberlink2android.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by USER on 2015/6/16.
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter {

    private int mResourceId;
    private boolean isEnable = true;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;
    
    
    public BaseRecyclerAdapter(int resourceId) {
        super();
        mResourceId = resourceId;
    }

    public void setEnable(boolean isEnable){
        this.isEnable = isEnable;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(mResourceId,parent,false);
        return new AntsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int tempPosition = position;
        holder.itemView.setEnabled(isEnable);
        if(mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, tempPosition);
                }
            });
        }else {
            holder.itemView.setOnClickListener(null);
        }

        if(mItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    if(mItemLongClickListener != null) {
                        mItemLongClickListener.onItemLongClick(v, tempPosition);
                    }
                    return false;
                }
            });
        }else {
            holder.itemView.setOnLongClickListener(null);
        }
        onBindViewData((AntsViewHolder) holder, position);
    }

    public Object getItemData(int position){
        return null;
    }

    public OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener clickListener) {
        this.mItemClickListener = clickListener;
    }

    public OnItemLongClickListener getItemLongClickListener() {
        return mItemLongClickListener;
    }

    public void setItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.mItemLongClickListener = longClickListener;
    }
    
    
    public abstract void onBindViewData(AntsViewHolder holder, int position); 
    
    
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    
    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }
    
    
    public static final class AntsViewHolder extends RecyclerView.ViewHolder{
        
        private SparseArray<View> viewArray;

        public AntsViewHolder(View itemView) {
            super(itemView);
        }
        
        public <V extends View> V getView(int id){
            return (V)findView(id);
        }
        
        public TextView getTextView(int id){
            return getView(id);
        }        
        
        public Button getButton(int id){
            return getView(id);
        }
        
        public ImageView getImageView(int id){
            return getView(id);
        }

        public ProgressBar getProgressBar(int id){
            return getView(id);
        }
        
        private View findView(int id){
            if(viewArray == null){
                viewArray = new SparseArray<View>();
            }
            View view = viewArray.get(id);
            if(view == null){
                view = itemView.findViewById(id);
                viewArray.put(id,view);
            }
            return view;
        }
    }
}
