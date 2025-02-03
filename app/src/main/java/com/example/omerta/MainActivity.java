package com.example.omerta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private GameState gameState;
    private TextView tvMafiaDonCount;
    private TextView tvMafiaCount;
    private TextView tvCommissarCount;
    private TextView tvDoctorCount;
    private TextView tvManiacCount;
    private TextView tvYakuzaCount;
    private TextView tvSheriffCount;
    private TextView tvCitizenCount;
    private TextView tvTotalPlayersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameState = GameState.getInstance();

        // Инициализация View
        tvTotalPlayersCount = findViewById(R.id.tvTotalPlayersCount);
        tvMafiaCount = findViewById(R.id.tvMafiaCount);
        tvMafiaDonCount = findViewById(R.id.tvMafiaDonCount);
        tvCommissarCount = findViewById(R.id.tvCommissarCount);
        tvDoctorCount = findViewById(R.id.tvDoctorCount);
        tvManiacCount = findViewById(R.id.tvManiacCount);
        tvYakuzaCount = findViewById(R.id.tvYakuzaCount);
        tvSheriffCount = findViewById(R.id.tvSheriffCount);
        tvCitizenCount = findViewById(R.id.tvCitizenCount);

        // Обновляем UI с начальными значениями из GameState
        updateUI();

        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> {
            startActivity(new Intent(this, PlayerNamesActivity.class));
        });
    }

    // Обработчики для кнопок +/-
    public void onTotalPlayersIncrement(View view) {
        gameState.setTotalPlayers(gameState.getTotalPlayers() + 1);
        updateUI();
    }
    public void onTotalPlayersDecrement(View view) {
        gameState.setTotalPlayers(Math.max(10, gameState.getTotalPlayers() - 1)); // Минимум 10 игроков
        updateUI();
    }

    public void onMafiaDonIncrement(View view) {
        gameState.setMaxMafiaDon(gameState.getMaxMafiaDon() + 1);
        updateUI();
    }

    public void onMafiaDonDecrement(View view) {
        gameState.setMaxMafiaDon(Math.max(0, gameState.getMaxMafiaDon() - 1));
        updateUI();
    }

    public void onMafiaIncrement(View view) {
        gameState.setMaxMafia(gameState.getMaxMafia() + 1);
        updateUI();
    }

    public void onMafiaDecrement(View view) {
        gameState.setMaxMafia(Math.max(0, gameState.getMaxMafia() - 1));
        updateUI();
    }

    public void onCommissarIncrement(View view) {
        gameState.setMaxCommissar(gameState.getMaxCommissar() + 1);
        updateUI();
    }

    public void onCommissarDecrement(View view) {
        gameState.setMaxCommissar(Math.max(0, gameState.getMaxCommissar() - 1));
        updateUI();
    }

    public void onDoctorIncrement(View view) {
        gameState.setMaxDoctor(gameState.getMaxDoctor() + 1);
        updateUI();
    }

    public void onDoctorDecrement(View view) {
        gameState.setMaxDoctor(Math.max(0, gameState.getMaxDoctor() - 1));
        updateUI();
    }

    public void onManiacIncrement(View view) {
        gameState.setMaxManiac(gameState.getMaxManiac() + 1);
        updateUI();
    }

    public void onManiacDecrement(View view) {
        gameState.setMaxManiac(Math.max(0, gameState.getMaxManiac() - 1));
        updateUI();
    }

    public void onYakuzaIncrement(View view) {
        gameState.setMaxYakuza(gameState.getMaxYakuza() + 1);
        updateUI();
    }

    public void onYakuzaDecrement(View view) {
        gameState.setMaxYakuza(Math.max(0, gameState.getMaxYakuza() - 1));
        updateUI();
    }

    public void onSheriffIncrement(View view) {
        gameState.setMaxSheriff(gameState.getMaxSheriff() + 1);
        updateUI();
    }

    public void onSheriffDecrement(View view) {
        gameState.setMaxSheriff(Math.max(0, gameState.getMaxSheriff() - 1));
        updateUI();
    }

    private void updateUI() {
        // Устанавливаем общее количество игроков
        tvTotalPlayersCount.setText(String.valueOf(gameState.getTotalPlayers()));

        // Максимальные значения ролей (лимиты)
        tvMafiaDonCount.setText(String.valueOf(gameState.getMaxMafiaDon()));
        tvMafiaCount.setText(String.valueOf(gameState.getMaxMafia()));
        tvCommissarCount.setText(String.valueOf(gameState.getMaxCommissar()));
        tvDoctorCount.setText(String.valueOf(gameState.getMaxDoctor()));
        tvManiacCount.setText(String.valueOf(gameState.getMaxManiac()));
        tvYakuzaCount.setText(String.valueOf(gameState.getMaxYakuza()));
        tvSheriffCount.setText(String.valueOf(gameState.getMaxSheriff()));

        // Мирные жители = Общее число - (Макс. мафия + Дон + Комиссар + Доктор)
        int citizenCount = gameState.getTotalPlayers()
                - (
                gameState.getMaxMafiaDon()
                        + gameState.getMaxMafia()
                        + gameState.getMaxCommissar()
                        + gameState.getMaxDoctor()
                        + gameState.getMaxManiac()
                        + gameState.getMaxYakuza()
                        + gameState.getMaxSheriff()
        );

        tvCitizenCount.setText("Мирные жители: " + citizenCount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

}
