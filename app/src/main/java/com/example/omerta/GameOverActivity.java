package com.example.omerta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        TextView gameOver = findViewById(R.id.gameOver);
        gameOver.setText(String.format("Игра Окончена!\nКоманда  %s выиграла!", GameState.getInstance().getWinner()));
        // Кнопка "Ночать заново"
        findViewById(R.id.playAgain).setOnClickListener(v -> {

            GameState.getInstance().reset();
            startActivity(new Intent(this, MainActivity.class));
            finish();


        });
    }
}