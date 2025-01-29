package com.example.omerta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        GameState gameState = GameState.getInstance();
        List<Player> players = gameState.getPlayers();

        TextView playerQuantity = findViewById(R.id.playerQuantity);
        updateAlivePlayersCount(playerQuantity);

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
            return R.layout.item_day;
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
