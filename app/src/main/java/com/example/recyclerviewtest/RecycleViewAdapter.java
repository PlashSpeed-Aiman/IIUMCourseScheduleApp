package com.example.recyclerviewtest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private List<List<String>> mGroupBind;
    private Context mContext;
    private OnCardClickListener mOnCardClickListener;
    private Dialog mDialog;

    public RecycleViewAdapter(Context context, List<List<String>> GroupBind, OnCardClickListener onCardClickListener) {
        mContext = context;
        mGroupBind = GroupBind;
        mOnCardClickListener = onCardClickListener;
    }

    public void ClearData() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView subjectCode, subjectTitle, lecturerName, lecturerName2, venueVal, sectionVal;
        CardView cardView;
        OnCardClickListener onCardClickListener;

        public ViewHolder(@NonNull View itemView, OnCardClickListener mOnCardClickListener) {
            super(itemView);
            subjectCode = itemView.findViewById(R.id.textView2);
            subjectTitle = itemView.findViewById(R.id.textView);
            lecturerName = itemView.findViewById(R.id.textView3);
            lecturerName2 = itemView.findViewById(R.id.textView4);
            venueVal = itemView.findViewById(R.id.venue_val);
            sectionVal = itemView.findViewById(R.id.section_value);
            cardView = itemView.findViewById(R.id.cardView);
        }

        @Override
        public void onClick(View v) {
            onCardClickListener.onCardClickListener(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_adapter, parent, false);
        ViewHolder holder = new ViewHolder(view, mOnCardClickListener);
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_layout);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.subjectTitle
                .setText((mGroupBind.get(position).get(0)));
        holder.subjectCode
                .setText(mGroupBind.get(position).get(2));
        holder.lecturerName2
                .setText(mGroupBind.get(position).get(4));
        holder.lecturerName
                .setText(mGroupBind.get(position).get(3));

//****
        if (holder.lecturerName2.getText().equals(holder.lecturerName.getText())) {
            holder.lecturerName2
                    .setVisibility(View.INVISIBLE);

        } else {
            holder.lecturerName2
                    .setText(mGroupBind.get(position).get(4));
        }
        //****

        holder.venueVal
                .setText(mGroupBind.get(position).get(6));
        holder.sectionVal
                .setText(mGroupBind.get(position).get(1));
//        holder.cardView
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(mContext, mGroupBind.get(position).toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });
    }

    @Override
    public int getItemCount() {
        return mGroupBind.size();

    }

    public interface OnCardClickListener {
        void onCardClickListener(int position);
    }


}