package com.smlab.zapride.ui.locationBottomSheet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.smlab.zapride.R;
import java.util.List;

public class LocationSuggestionAdapter extends RecyclerView.Adapter<LocationSuggestionAdapter.ViewHolder> {

    private final List<String> suggestions;

    public LocationSuggestionAdapter(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String suggestion = suggestions.get(position);
        holder.suggestionTextView.setText(suggestion);
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView suggestionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            suggestionTextView = itemView.findViewById(R.id.suggestion_text);
        }
    }
}
