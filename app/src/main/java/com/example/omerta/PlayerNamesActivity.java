package com.example.omerta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayerNamesActivity extends AppCompatActivity {
    private LinearLayout namesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_names);

        namesContainer = findViewById(R.id.namesContainer);
        // Получаем общее количество игроков из GameState
        int totalPlayers = GameState.getInstance().getTotalPlayers();

        // Создаем поля ввода по количеству игроков
        for (int i = 0; i < totalPlayers; i++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView numberText = new TextView(this);
            numberText.setText((i + 1) + ". ");
            numberText.setTextSize(18);
            rowLayout.addView(numberText);

            EditText editText = new EditText(this);
            editText.setHint("Игрок " + (i + 1));
            editText.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));
            rowLayout.addView(editText);

            namesContainer.addView(rowLayout);
        }

        Button continueButton = new Button(this);
        continueButton.setText("Продолжить");
        continueButton.setOnClickListener(v -> collectNames());
        namesContainer.addView(continueButton);
    }

    private void collectNames() {
        GameState gameState = GameState.getInstance();
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < namesContainer.getChildCount() - 1; i++) {
            View view = namesContainer.getChildAt(i);
            if (view instanceof LinearLayout) {
                LinearLayout row = (LinearLayout) view;
                EditText editText = (EditText) row.getChildAt(1);
                String name = editText.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(this, "Пустое имя в строке " + (i + 1), Toast.LENGTH_SHORT).show();
                    return;
                }
                players.add(new Player(name));
            }
        }

        gameState.setPlayers(players);
        startActivity(new Intent(this, RoleAssignmentActivity.class));


        Toast.makeText(this, "Игроки: " + players, Toast.LENGTH_LONG).show();
    }

}
