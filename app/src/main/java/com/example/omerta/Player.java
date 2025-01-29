package com.example.omerta;

import java.io.Serializable;
import java.util.UUID;

/**
 * Класс Player представляет игрока в игре "Мафия".
 * Содержит уникальный идентификатор, имя, роль и статус (жив/мертв).
 */
public class Player implements Serializable {
    private final String id;  // Уникальный идентификатор игрока
    private final String name;  // Имя игрока
    private Role role;  // Роль игрока в игре
    private boolean alive;  // Статус игрока: жив или мертв

    /**
     * Конструктор создает нового игрока с заданным именем.
     * По умолчанию каждому игроку присваивается роль "Мирный житель" (CITIZEN).
     * Игрок изначально считается живым.
     *
     * @param name Имя игрока
     */
    public Player(String name) {
        this.id = UUID.randomUUID().toString();  // Генерация уникального ID
        this.name = name;
        this.role = Role.CITIZEN;  // Установка роли по умолчанию
        this.alive = true;  // Игрок стартует живым
    }

    // Геттеры
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    // Устанавливает новую роль игрока
    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAlive() {
        return alive;
    }

    /**
     * Устанавливает статус жизни игрока.
     * @param alive true - игрок жив, false - игрок мертв
     */
    public void setAlive(boolean alive) {
        this.alive = !alive;
    }
}
