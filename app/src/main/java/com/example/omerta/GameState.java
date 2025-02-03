package com.example.omerta;

import com.example.omerta.model.Player;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class GameState {
    private static GameState instance;
    private int dayCount = 1;
    private List<Player> players = new ArrayList<>();
    private int totalPlayers = 15;

    private int maxMafia = 3;
    private int maxMafiaDon = 1;
    private int maxCommissar = 1;
    private int maxDoctor = 1;
    private int maxManiac = 1;
    private int maxYakuza = 0;
    private int maxSheriff = 0;

    // Текущие счетчики (начинаются с 0)
    private int currentMafia = 0;
    private int currentMafiaDon = 0;
    private int currentCommissar = 0;
    private int currentDoctor = 0;
    private int currentManiac = 0;
    private int currentYakuza = 0;
    private int currentSheriff = 0;


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

    private void incrementRoleCount(Role role) {
        if (role == null) return;

        switch (role) {
            case MAFIA -> currentMafia++;
            case DON -> currentMafiaDon++;
            case COMMISSAR -> currentCommissar++;
            case DOCTOR -> currentDoctor++;
            case MANIAC -> currentManiac++;
            case YAKUZA -> currentYakuza++;
            case SHERIFF -> currentSheriff++;
        }
    }

    private void decrementRoleCount(Role role) {
        if (role == null) return;

        switch (role) {
            case MAFIA -> currentMafia = Math.max(0, currentMafia - 1);
            case DON -> currentMafiaDon = Math.max(0, currentMafiaDon - 1);
            case COMMISSAR -> currentCommissar = Math.max(0, currentCommissar - 1);
            case DOCTOR -> currentDoctor = Math.max(0, currentDoctor - 1);
            case MANIAC -> currentManiac = Math.max(0, currentManiac - 1);
            case YAKUZA -> currentYakuza = Math.max(0, currentYakuza - 1);
            case SHERIFF -> currentSheriff = Math.max(0, currentSheriff - 1);
        }
    }

    // Проверка, доступна ли роль для назначения
    public boolean isRoleAvailable(Role role) {
        return switch (role) {
            case MAFIA -> currentMafia < maxMafia;
            case DON -> currentMafiaDon < maxMafiaDon;
            case COMMISSAR -> currentCommissar < maxCommissar;
            case DOCTOR -> currentDoctor < maxDoctor;
            case MANIAC -> currentManiac < maxManiac;
            case YAKUZA -> currentYakuza < maxYakuza;
            case SHERIFF -> currentSheriff < maxSheriff;
            default -> true;
        };
    }

    public boolean isGameOver() {
        // Подсчет живых красных игроков
        long redAliveCount = players.stream()
                .filter(Player::isAlive)
                .filter(player -> player.getRole().getTeam() == Role.Team.RED)
                .count();

        // Подсчет живых черных игроков
        long blackAliveCount = players.stream()
                .filter(Player::isAlive)
                .filter(player -> player.getRole().getTeam() == Role.Team.BLACK)
                .count();
        // Игра заканчивается, если количество живых красных равно количеству живых черных
        return blackAliveCount >= redAliveCount;
    }

    // Сброс игры к дефолтным настройкам
    public void reset() {
        players.clear(); // Очищаем список игроков
        dayCount = 1;    // Сбрасываем счетчик дней к начальному значению

        // Сбрасываем максимальные лимиты ролей к дефолтным значениям
        maxMafia = 3;
        maxMafiaDon = 1;
        maxCommissar = 1;
        maxDoctor = 1;
        maxManiac = 1;
        maxYakuza = 0;
        maxSheriff = 0;

        // Текущие счетчики (начинаются с 0)
        currentMafia = 0;
        currentMafiaDon = 0;
        currentCommissar = 0;
        currentDoctor = 0;
        currentManiac = 0;
        currentYakuza = 0;
        currentSheriff = 0;
    }

    public void incrementDayCount() {
        dayCount++;
    }

}
