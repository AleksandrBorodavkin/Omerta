package com.example.omerta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.omerta.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerNamesActivity extends AppCompatActivity {
    private LinearLayout namesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_names);

        namesContainer = findViewById(R.id.namesContainer);
        Button continueButton = findViewById(R.id.continueButton);

        int totalPlayers = GameState.getInstance().getTotalPlayers();
        for (int i = 0; i < totalPlayers; i++) {
            addPlayerInputField(i + 1);
        }

        continueButton.setOnClickListener(v -> collectNames());
    }

    private void addPlayerInputField(int playerNumber) {
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView numberText = new TextView(this);
        numberText.setText(playerNumber + ". ");
        numberText.setTextSize(18);
        rowLayout.addView(numberText);

        EditText editText = new EditText(this);
        editText.setHint("Игрок " + playerNumber);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));
        rowLayout.addView(editText);

        namesContainer.addView(rowLayout);
    }

    private void collectNames() {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < namesContainer.getChildCount(); i++) {
            LinearLayout row = (LinearLayout) namesContainer.getChildAt(i);
            EditText editText = (EditText) row.getChildAt(1);
            String name = editText.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Пустое имя в строке " + (i + 1), Toast.LENGTH_SHORT).show();
                return;
            }
            players.add(new Player(name));
        }

        GameState.getInstance().setPlayers(players);
        startActivity(new Intent(this, RoleAssignmentActivity.class));
    }
}

