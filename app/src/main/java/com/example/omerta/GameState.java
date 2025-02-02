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

    // Текущие счетчики (начинаются с 0)
    private int currentMafia = 0;
    private int currentMafiaDon = 0;
    private int currentCommissar = 0;
    private int currentDoctor = 0;

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
            case MAFIA_DON -> currentMafiaDon++;
            case COMMISSAR -> currentCommissar++;
            case DOCTOR -> currentDoctor++;
        }
    }

    private void decrementRoleCount(Role role) {
        if (role == null) return;

        switch (role) {
            case MAFIA -> currentMafia = Math.max(0, currentMafia - 1);
            case MAFIA_DON -> currentMafiaDon = Math.max(0, currentMafiaDon - 1);
            case COMMISSAR -> currentCommissar = Math.max(0, currentCommissar - 1);
            case DOCTOR -> currentDoctor = Math.max(0, currentDoctor - 1);
        }
    }

    // Проверка, доступна ли роль для назначения
    public boolean isRoleAvailable(Role role) {
        return switch (role) {
            case MAFIA -> currentMafia < maxMafia;
            case MAFIA_DON -> currentMafiaDon < maxMafiaDon;
            case COMMISSAR -> currentCommissar < maxCommissar;
            case DOCTOR -> currentDoctor < maxDoctor;
            default -> true;
        };
    }

    // Проверка, окончена ли игра (если все игроки мертвы)
    public boolean isGameOver() {
        return players.stream().noneMatch(Player::isAlive);
    }

    // Сброс игры к дефолтным настройкам
    public void reset() {
        players.clear(); // Очищаем список игроков
        dayCount = 1;    // Сбрасываем счетчик дней к начальному значению

        // Сбрасываем максимальные лимиты ролей к дефолтным значениям
        maxMafiaDon = 1;
        maxMafia = 3;
        maxCommissar = 1;
        maxDoctor = 1;

        // Обнуляем текущие счетчики назначенных ролей
        currentMafiaDon = 0;
        currentMafia = 0;
        currentCommissar = 0;
        currentDoctor = 0;
        totalPlayers = 15; // Сбрасываем общее количество игроков к дефолтному
    }

    public void incrementDayCount() {
        dayCount++;
    }

}
