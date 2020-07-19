package com.app.behavior.behavior.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.behavior.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ListBottomSheetDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView rvContent = contentView.findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        StringAdapter stringAdapter = new StringAdapter(getContext());
        rvContent.setAdapter(stringAdapter);
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) getDialog();
//        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = bottomSheetDialog.getBehavior();
//        bottomSheetBehavior.setPeekHeight(600, true);
    }

    private static class StringAdapter extends RecyclerView.Adapter<StringViewHolder> {

        private Context context;

        private StringAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StringViewHolder(LayoutInflater.from(context).inflate(R.layout.item_string, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
            holder.tvContent.setText("Item" + position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    private static class StringViewHolder extends RecyclerView.ViewHolder {

        private TextView tvContent;

        StringViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}