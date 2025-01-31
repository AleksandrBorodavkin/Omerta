package com.example.omerta;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BasePlayerAdapter extends RecyclerView.Adapter<BasePlayerAdapter.ViewHolder> {
    protected final List<Player> players;

    BasePlayerAdapter(List<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getLayoutResource(), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player player = players.get(position);
        holder.bind(player);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    protected abstract int getLayoutResource();

    static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView playerName;
        protected TextView playerRole;
        protected CheckBox killCheckbox;
        protected CardView playerCardView;

        ViewHolder(View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            playerRole = itemView.findViewById(R.id.playerRole);
            killCheckbox = itemView.findViewById(R.id.killCheckbox);
            playerCardView = itemView.findViewById(R.id.playerCardView);
        }

        void bind(Player player) {
            // Устанавливаем цвет фона в зависимости от команды
            if (player.getRole().getTeam() == Role.Team.RED) {
                playerCardView.setCardBackgroundColor(Color.RED);
                playerName.setTextColor(Color.BLACK);
                playerRole.setTextColor(Color.BLACK);
                killCheckbox.setTextColor(Color.BLACK);
            } else if (player.getRole().getTeam() == Role.Team.BLACK) {
                playerCardView.setCardBackgroundColor(Color.BLACK);
                playerName.setTextColor(Color.WHITE);
                playerRole.setTextColor(Color.WHITE);
                killCheckbox.setTextColor(Color.WHITE);
            }

            playerName.setText(player.getName());
            playerRole.setText(player.getRole().getDisplayName());

            killCheckbox.setEnabled(player.isAlive());
            killCheckbox.setChecked(!player.isAlive());

            if (!player.isAlive()) {
                playerCardView.setAlpha(0.3f);
            }

            killCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                player.setAlive(isChecked);
            });
        }
    }
}
