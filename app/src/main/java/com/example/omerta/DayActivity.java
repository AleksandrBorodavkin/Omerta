package com.example.omerta;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omerta.controller.BaseActivity;
import com.example.omerta.model.Player;

import java.util.List;
import java.util.Locale;


public class DayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        GameState gameState = GameState.getInstance();
        List<Player> players = gameState.getPlayers();

        TextView playerQuantityBlack = findViewById(R.id.playerQuantityBlack);
        updateAlivePlayersCountBlack(playerQuantityBlack);
        TextView tvDayCount = findViewById(R.id.tvDayCount);
        updatedayIncrementCount(tvDayCount);
        TextView playerQuantityRed = findViewById(R.id.playerQuantityRed);
        updateAlivePlayersCountRed(playerQuantityRed);

        // Настройка RecyclerView
        RecyclerView recyclerView = findViewById(R.id.dayRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DayAdapter adapter = new DayAdapter(players);
        recyclerView.setAdapter(adapter);

        // Кнопка завершения ночи
        findViewById(R.id.endDayButton).setOnClickListener(v -> {
            boolean gameOver = GameState.getInstance().isGameOver();
            Log.d("GameState", "Is game over? " + gameOver);
            if (gameOver) {
                GameState.getInstance().reset();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                showToast("Игра окончена!");
            } else {
                GameState.getInstance().incrementDayCount();
                startActivity(new Intent(this, NightActivity.class));
                finish();
            }
        });

    }


    public class DayAdapter extends BasePlayerAdapter {

        DayAdapter(List<Player> players) {
            super(players);
        }

        @Override
        protected int getLayoutResource() {
            return R.layout.item_player;
        }

        static class DayViewHolder extends ViewHolder {
            private TextView timerTextView;
            private CountDownTimer countDownTimer;
            private boolean isTimerRunning = false;

            DayViewHolder(View itemView) {
                super(itemView);
                timerTextView = itemView.findViewById(R.id.timerTextView);

                playerCardView.setOnClickListener(v -> toggleTimer());
            }

            @Override
            public void bind(Player player) {
                super.bind(player);
                timerTextView.setText("00:40");
                timerTextView.setVisibility(View.GONE);
            }

            private void toggleTimer() {
                if (isTimerRunning) {
                    stopTimer();
                } else {
                    startTimer();
                }
            }

            private void startTimer() {
                timerTextView.setVisibility(View.VISIBLE);
                countDownTimer = new CountDownTimer(40000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int seconds = (int) (millisUntilFinished / 1000);
//                        playerCardView.setCardBackgroundColor(Color.YELLOW);
                        timerTextView.setText(String.format(Locale.getDefault(), "00:%02d", seconds));
                    }

                    @Override
                    public void onFinish() {
                        timerTextView.setAlpha(0.5f);
                        timerTextView.setText("00:00");
                        timerTextView.setTextColor(Color.GRAY);
                        isTimerRunning = false;
                    }
                }.start();
                isTimerRunning = true;
            }

            private void stopTimer() {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    timerTextView.setVisibility(View.GONE);
                    playerCardView.setAlpha(1.0f);
                    isTimerRunning = false;
                }
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(getLayoutResource(), parent, false);
            return new DayViewHolder(view);
        }
    }

}
//public class DayActivity extends BaseActivity {
//    private DayAdapter adapter;
//    private TextView alivePlayersCount;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_day);
//        alivePlayersCount = findViewById(R.id.playerQuantity);
//
//        setupRecyclerView();
//        setupControls();
//    }
//
//    private void updateAlivePlayers() {
//        int alive = (int) GameState.getInstance().getPlayers()
//                .stream()
//                .filter(p -> !p.isKilled())
//                .count();
//        alivePlayersCount.setText("Живые игроки: " + alive);
//    }
//
//    private void setupRecyclerView() {
//        RecyclerView recyclerView = findViewById(R.id.dayRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new DayAdapter(GameState.getInstance().getPlayers());
//        recyclerView.setAdapter(adapter);
//    }
//
//    private void setupControls() {
//        findViewById(R.id.endDayButton).setOnClickListener(v -> handleGameEnd());
//        updateAlivePlayers();
//    }
//
//    private void handleGameEnd() {
//        for (Player player : GameState.getInstance().getPlayers()) {
//            player.setKilled(adapter.getKilledStatus(player.getId()));
//        }
//
//        if (isGameOver()) {
//            GameState.getInstance().reset();
//            startActivity(new Intent(this, MainActivity.class));
//        } else {
//            startActivity(new Intent(this, NightActivity.class));
//        }
//        finish();
//    }
//
//    class DayAdapter extends BasePlayerAdapter {
//        private final Map<String, Boolean> killedStatus = new HashMap<>();
//
//        @Override
//        protected int getLayoutResource() {
//            return R.layout.item_day;
//        }
//
//        DayAdapter(List<Player> players) {
//            super(players);
//            initKilledStatus();
//        }
//
//        private void initKilledStatus() {
//            for (Player player : players) {
//                killedStatus.put(player.getId(), player.isKilled());
//            }
//        }
//
//        @NonNull
//        @Override
//        public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_day, parent, false);
//            return new DayViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            super.onBindViewHolder(holder, position);
//            Player player = players.get(position);
//            DayViewHolder dayHolder = (DayViewHolder) holder;
//
//            dayHolder.bind(player);
//            dayHolder.setKilledStatus(killedStatus.getOrDefault(player.getId(), false));
//        }
//
//        boolean getKilledStatus(String playerId) {
//            return killedStatus.getOrDefault(playerId, false);
//        }
//
//        class DayViewHolder extends BasePlayerAdapter.ViewHolder {
//            private Player currentPlayer;
//
//            DayViewHolder(View itemView) {
//                super(itemView);
//                linearLayout = itemView.findViewById(R.id.playerLayout);
//                playerName = itemView.findViewById(R.id.playerName);
//                playerRole = itemView.findViewById(R.id.playerRole);
//                killCheckbox = itemView.findViewById(R.id.killCheckbox);
//            }
//
//            void bind(Player player) {
//                super.bind(player);
//                this.currentPlayer = player;
//                updateKilledUI();
//            }
//
//            private void setKilledStatus(boolean killed) {
//                if (currentPlayer != null) {
//                    currentPlayer.setKilled(killed);
//                    GameState.getInstance().updatePlayer(currentPlayer);
//                    ((DayActivity) itemView.getContext()).updateAlivePlayers();
//                    updateKilledUI();
//                }
//            }
//
//            private void updateKilledUI() {
//                boolean killed = currentPlayer.isKilled();
//                linearLayout.setAlpha(killed ? 0.5f : 1f);
//                killCheckbox.setChecked(killed);
//                killCheckbox.setEnabled(!killed);
//            }
//        }
//    }
//}
//
