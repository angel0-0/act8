package com.angel.act8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumberAdapter extends RecyclerView.Adapter<PhoneNumberAdapter.ViewHolder> {

    private List<PhoneNumber> phoneNumbers;
    private OnPhoneNumberDeleteListener deleteListener;

    public interface OnPhoneNumberDeleteListener {
        void onDelete(PhoneNumber phoneNumber);
    }

    public PhoneNumberAdapter() {
        this.phoneNumbers = new ArrayList<>();
    }

    public void setDeleteListener(OnPhoneNumberDeleteListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phone_number, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhoneNumber phoneNumber = phoneNumbers.get(position);
        holder.tvPhoneNumber.setText(phoneNumber.getNumber());

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(phoneNumber);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneNumbers.size();
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        if (!phoneNumbers.contains(phoneNumber)) {
            phoneNumbers.add(phoneNumber);
            notifyItemInserted(phoneNumbers.size() - 1);
        }
    }

    public void removePhoneNumber(PhoneNumber phoneNumber) {
        int position = phoneNumbers.indexOf(phoneNumber);
        if (position >= 0) {
            phoneNumbers.remove(position);
            notifyItemRemoved(position);
        }
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return new ArrayList<>(phoneNumbers);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPhoneNumber;
        Button btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
