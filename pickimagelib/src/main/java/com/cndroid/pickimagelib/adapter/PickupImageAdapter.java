package com.cndroid.pickimagelib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cndroid.pickimagelib.Intents;
import com.cndroid.pickimagelib.PickupImageDisplay;
import com.cndroid.pickimagelib.R;
import com.cndroid.pickimagelib.bean.PickupImageItem;
import com.cndroid.pickimagelib.views.SelectableImageView;

import java.util.List;

/**
 * Created by jinbangzhu on 1/8/16.
 */
public class PickupImageAdapter extends RecyclerView.Adapter<PickupImageAdapter.ViewHolder> {

    private PickupImageDisplay imageDisplay;
    private List<PickupImageItem> imageItemList;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pi_layout_pickup_image_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PickupImageItem item = imageItemList.get(position);

        if (position == 0 && Intents.ImagePicker.SHOWCAMERA.equals(item.getAlbumName())) {
            holder.imageViewCheckState.setVisibility(View.GONE);
            holder.imageView.setImageResource(R.mipmap.ic_camera_alt_white_48dp);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageDisplay.displayCameraImage(holder.imageView);
        } else {
            holder.imageViewCheckState.setVisibility(View.VISIBLE);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageDisplay.displayImage(holder.imageView, item.getImagePath());
            holder.imageView.isSelected(item.isSelected());

            if (item.isSelected()) {
                holder.imageViewCheckState.setImageResource(R.mipmap.pi_icon_check_on);
            } else {
                holder.imageViewCheckState.setImageResource(R.mipmap.pi_icon_check_off);
            }
        }

        holder.imageViewCheckState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener)
                    onItemClickListener.onCheckBoxImageChecked(item, position);
            }
        });
        holder.imageView.setTapListener(new SelectableImageView.ITapListener() {
            @Override
            public void onTaped() {
                if (null != onItemClickListener)
                    onItemClickListener.onImageViewTaped(position);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onImageViewTaped(int position);

        void onCheckBoxImageChecked(PickupImageItem item, int position);
    }

    @Override
    public int getItemCount() {
        return imageItemList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        private SelectableImageView imageView;
        private ImageView imageViewCheckState;

        public ViewHolder(View v) {
            super(v);
            imageView = (SelectableImageView) v.findViewById(R.id.iv_image);
            imageViewCheckState = (ImageView) v.findViewById(R.id.iv_check_state);
        }
    }

    public void setImageItemList(List<PickupImageItem> imageItemList) {
        this.imageItemList = imageItemList;
    }

    public void setImageDisplay(PickupImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }
}
