package com.example.omerta;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameState {
    private static GameState instance;
    private List<Player> players = new ArrayList<>();
    private int totalPlayers = 15;
    private int mafia = 3;
    private int mafiaDon = 1;
    private int commissar = 1;
    private int doctor = 1;

    // Приватный конструктор для реализации Singleton
    private GameState() {
    }

    // Получение единственного экземпляра GameState (Singleton)
    public static synchronized GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    // Обновление количества ролей при изменении у игрока
    public void updateRoleCount(Role oldRole, Role newRole) {
        if (oldRole != null) {
            decrementRoleCount(oldRole);
        }
        if (newRole != null) {
            incrementRoleCount(newRole);
        }
    }

    // Увеличение счетчика ролей
    private void incrementRoleCount(Role role) {
        if (role == null) return;

        switch (role) {
            case MAFIA -> mafia++;
            case MAFIA_DON -> mafiaDon++;
            case COMMISSAR -> commissar++;
            case DOCTOR -> doctor++;
            default -> {
                // Мирных жителей (CITIZEN) не учитываем явно
            }
        }
    }

    // Уменьшение счетчика ролей
    private void decrementRoleCount(Role role) {
        if (role == null) return;

        switch (role) {
            case MAFIA -> mafia = Math.max(0, mafia - 1);
            case MAFIA_DON -> mafiaDon = Math.max(0, mafiaDon - 1);
            case COMMISSAR -> commissar = Math.max(0, commissar - 1);
            case DOCTOR -> doctor = Math.max(0, doctor - 1);
            default -> {
                // Мирных жителей не уменьшаем вручную
            }
        }
    }

    // Максимальное количество мафии в игре
    public int getMaxMafia() {
        return 9;
    }

    // Максимальное количество комиссаров в игре
    public int getMaxCommissar() {
        return 1;
    }

    // Максимальное количество докторов в игре
    public int getMaxDoctor() {
        return 1;
    }

    // Проверка, доступна ли роль для назначения
    public boolean isRoleAvailable(Role role, String currentPlayerRole) {
        if (role.name().equals(currentPlayerRole)) return true;

        return switch (role) {
            case MAFIA -> mafia < getMaxMafia();
            case COMMISSAR -> commissar < getMaxCommissar();
            case DOCTOR -> doctor < getMaxDoctor();
            default -> true;
        };
    }

    // Проверка, окончена ли игра (если все игроки мертвы)
    public boolean isGameOver() {
        return players.stream().noneMatch(Player::isAlive);
    }

    // Сброс игры к дефолтным настройкам
    public void reset() {
        players.clear();
        mafiaDon = 1;
        mafia = 3;
        commissar = 1;
        doctor = 1;
        totalPlayers = 15;
    }

    // Получение количества мирных жителей
    public int getCitizenCount() {
        return totalPlayers - (mafia + commissar + doctor);
    }

    // Получение списка живых игроков
    public List<Player> getAlivePlayers() {
        return players.stream()
                .filter(Player::isAlive)
                .collect(Collectors.toList());
    }

    // Геттеры и сеттеры
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int total) {
        this.totalPlayers = total;
    }

    public int getMafia() {
        return mafia;
    }

    public int getCommissar() {
        return commissar;
    }

    public int getDoctor() {
        return doctor;
    }

    public void incrementMafia() {
        mafia++;
    }

    public void decrementMafia() {
        if (mafia > 0) mafia--;
    }

    public void incrementCommissar() {
        commissar++;
    }

    public void decrementCommissar() {
        if (commissar > 0) commissar--;
    }

    public void incrementDoctor() {
        doctor++;
    }

    public void decrementDoctor() {
        if (doctor > 0) doctor--;
    }

    public void incrementMafiaDon() {
        mafiaDon++;
    }

    public void decrementMafiaDon() {
        if (mafiaDon > 0) mafiaDon--;
    }

    public int getMafiaDon() {
        return mafiaDon;
    }

    public void setMafiaDon(int mafiaDon) {
        this.mafiaDon = mafiaDon;
    }
}
