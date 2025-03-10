package com.smlab.zapride.ui.history.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.smlab.zapride.R;
import com.smlab.zapride.ui.history.model.HistoryModel;

import java.util.List;

public class historyAdaptor extends RecyclerView.Adapter<historyAdaptor.ViewHolder> {
    private List<HistoryModel> bookingList;

    public historyAdaptor(List<HistoryModel> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel booking = bookingList.get(position);

        holder.tvDateTime.setText(booking.getDateTime());
        holder.tvPrice.setText("$" + booking.getPrice()); // Set price at top right
        holder.tvBookingId.setText("Booking ID: " + booking.getBookingId());
        holder.tvPickupLocation.setText(booking.getPickupLocation());
        holder.tvDropoffLocation.setText(booking.getDropoffLocation());
        holder.tvStatus.setText(booking.getStatus());

        // Change status color based on completion or cancellation
        if (booking.getStatus().equalsIgnoreCase("Completed")) {
            holder.tvStatus.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
        } else if (booking.getStatus().equalsIgnoreCase("Cancelled")) {
            holder.tvStatus.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDateTime, tvPrice, tvBookingId, tvPickupLocation, tvDropoffLocation, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateTime = itemView.findViewById(R.id.date_time);
            tvPrice = itemView.findViewById(R.id.price);
            tvBookingId = itemView.findViewById(R.id.booking_id);
            tvPickupLocation = itemView.findViewById(R.id.pickup_location);
            tvDropoffLocation = itemView.findViewById(R.id.dropoff_location);
            tvStatus = itemView.findViewById(R.id.status);
        }
    }
}
