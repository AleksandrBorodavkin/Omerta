package com.example.omerta;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected boolean isGameOver() {
        for (Player player : GameState.getInstance().getPlayers()) {
            if (!player.isAlive()) return false;
        }
        return true;
    }

    protected void updateAlivePlayersCount(TextView playerQuantity) {
        List<Player> players = GameState.getInstance().getPlayers();
        long alivePlayersCount = players.stream()
                .filter(player -> player.isAlive())
                .count();
        playerQuantity.setText(String.format("Игроков: %d", alivePlayersCount));
    }
}
