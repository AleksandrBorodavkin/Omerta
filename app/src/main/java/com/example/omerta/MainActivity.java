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
    private TextView tvCitizenCount;
    private EditText etTotalPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameState = GameState.getInstance();

        // Инициализация View
        etTotalPlayers = findViewById(R.id.etTotalPlayers);
        tvMafiaCount = findViewById(R.id.tvMafiaCount);
        tvMafiaDonCount = findViewById(R.id.tvMafiaDonCount);
        tvCommissarCount = findViewById(R.id.tvCommissarCount);
        tvDoctorCount = findViewById(R.id.tvDoctorCount);
        tvCitizenCount = findViewById(R.id.tvCitizenCount);

        // Обновляем UI с начальными значениями из GameState
        updateUI();

        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> {
            int total = Integer.parseInt(etTotalPlayers.getText().toString());
            gameState.setTotalPlayers(total);
            startActivity(new Intent(this, PlayerNamesActivity.class));
        });
    }

    // Обработчики для кнопок +/-
    public void onMafiaDonIncrement(View view) {
        gameState.incrementMafiaDon();
        updateUI();
    }

    public void onMafiaDonDecrement(View view) {
        gameState.decrementMafiaDon();
        updateUI();
    }

    public void onMafiaIncrement(View view) {
        gameState.incrementMafia();
        updateUI();
    }

    public void onMafiaDecrement(View view) {
        gameState.decrementMafia();
        updateUI();
    }

    public void onCommissarIncrement(View view) {
        gameState.incrementCommissar();
        updateUI();
    }

    public void onCommissarDecrement(View view) {
        gameState.decrementCommissar();
        updateUI();
    }

    public void onDoctorIncrement(View view) {
        gameState.incrementDoctor();
        updateUI();
    }

    public void onDoctorDecrement(View view) {
        gameState.decrementDoctor();
        updateUI();
    }

    private void updateUI() {
        etTotalPlayers.setText(String.valueOf(gameState.getTotalPlayers()));
        tvMafiaDonCount.setText(String.valueOf(gameState.getMafiaDon()));
        tvMafiaCount.setText(String.valueOf(gameState.getMafia()));
        tvCommissarCount.setText(String.valueOf(gameState.getCommissar()));
        tvDoctorCount.setText(String.valueOf(gameState.getDoctor()));
        tvCitizenCount.setText("Мирные жители: " + gameState.getCitizenCount());
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
