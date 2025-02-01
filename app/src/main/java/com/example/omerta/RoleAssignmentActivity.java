package com.example.omerta;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omerta.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoleAssignmentActivity extends AppCompatActivity {
    private RoleAdapter adapter;
    private GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_assignment);

        gameState = GameState.getInstance();
        List<Player> players = gameState.getPlayers();

        RecyclerView recyclerView = findViewById(R.id.playersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RoleAdapter(players);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.confirmRolesButton).setOnClickListener(v -> {
            saveRoles();
            Toast.makeText(this, "Роли сохранены!", Toast.LENGTH_SHORT).show();
        });
    }

    private void saveRoles() {
        Intent nightIntent = new Intent(this, NightActivity.class);
        nightIntent.putExtra("PLAYERS", (Serializable) gameState.getPlayers());
        startActivity(nightIntent);
    }

    private class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.ViewHolder> {
        private final List<Player> players;
        private final List<Role> allRoles = Arrays.asList(Role.values());

        RoleAdapter(List<Player> players) {
            this.players = players;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_role, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(players.get(position));
        }

        @Override
        public int getItemCount() {
            return players.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView playerName;
            private final Spinner roleSpinner;
            private Role currentRole;

            ViewHolder(View itemView) {
                super(itemView);
                playerName = itemView.findViewById(R.id.playerName);
                roleSpinner = itemView.findViewById(R.id.roleSpinner);
            }

            void bind(Player player) {
                playerName.setText(player.getName());
                currentRole = player.getRole();
                setupSpinner(player);
            }

            private void setupSpinner(Player player) {
                List<Role> availableRoles = new ArrayList<>();

                if (player.getRole() != null) {
                    availableRoles.add(currentRole);
                }

                for (Role role : allRoles) {
                    if (role == Role.CITIZEN || gameState.isRoleAvailable(role, currentRole.name())) {
                        if (!availableRoles.contains(role)) {
                            availableRoles.add(role);
                        }
                    }
                }

                ArrayAdapter<Role> adapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item, availableRoles);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                roleSpinner.setAdapter(adapter);

                int position = availableRoles.indexOf(currentRole);
                roleSpinner.setSelection(position >= 0 ? position : 0);

                roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Role newRole = (Role) parent.getItemAtPosition(position);

                        if (currentRole != newRole) {
                            gameState.updateRoleCount(currentRole, newRole);
                            currentRole = newRole;
                            player.setRole(Role.valueOf(newRole.name()));
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        }
    }
}