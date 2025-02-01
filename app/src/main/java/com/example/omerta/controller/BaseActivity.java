package com.example.omerta.controller;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.omerta.GameState;
import com.example.omerta.Role;
import com.example.omerta.model.Player;

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

    protected void updateAlivePlayersCountBlack(TextView playerQuantityBlack) {
        List<Player> players = GameState.getInstance().getPlayers();

        // Считаем количество живых игроков в каждой команде
        long aliveRedPlayersCountBlack = players.stream()
                .filter(player -> player.isAlive() && player.getRole().getTeam() == Role.Team.BLACK)
                .count();

        // Обновляем текстовое поле с количеством живых игроков
        playerQuantityBlack.setText(String.format("%d",aliveRedPlayersCountBlack));
    }
    protected void updateAlivePlayersCountRed(TextView playerQuantityRed) {
        Log.d("updateAlivePlayersCountRed", "Method called");
        List<Player> players = GameState.getInstance().getPlayers();


        long aliveRedPlayersCountRed = players.stream()
                .filter(player -> player.isAlive() && player.getRole().getTeam() == Role.Team.RED)
                .count();

        // Обновляем текстовое поле с количеством живых игроков
        playerQuantityRed.setText(String.format("%d",aliveRedPlayersCountRed));
        Log.d("updateAlivePlayersCountRed", "Text set to: " + aliveRedPlayersCountRed);
    }
}
