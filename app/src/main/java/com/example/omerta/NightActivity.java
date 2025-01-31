package com.example.omerta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class NightActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);

        GameState gameState = GameState.getInstance();
        List<Player> players = gameState.getPlayers();

        // Настройка RecyclerView
        RecyclerView recyclerView = findViewById(R.id.nightRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NightAdapter adapter = new NightAdapter(players);
        recyclerView.setAdapter(adapter);

        // Кнопка завершения ночи
        findViewById(R.id.confirmNightButton).setOnClickListener(v -> {
            boolean gameOver = GameState.getInstance().isGameOver();
            Log.d("GameState", "Is game over? " + gameOver);
            if (gameOver) {
                GameState.getInstance().reset();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                showToast("Игра окончена!");
            } else {
                startActivity(new Intent(this, DayActivity.class));
                finish();
            }
        });
        TextView playerQuantity = findViewById(R.id.playerQuantity);
        updateAlivePlayersCount(playerQuantity);

    }

    public class NightAdapter extends BasePlayerAdapter {

        NightAdapter(List<Player> players) {
            super(players);
        }

        @Override
        protected int getLayoutResource() {
            return R.layout.item_player;
        }
    }
}