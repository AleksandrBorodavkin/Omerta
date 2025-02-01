package com.example.omerta.model;

import com.example.omerta.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Класс Player представляет игрока в игре "Мафия".
 * Содержит уникальный идентификатор, имя, роль и статус (жив/мертв).
 */
@Data
public class Player implements Serializable {
    private final UUID id;  // Уникальный идентификатор игрока
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
        this.id = UUID.randomUUID();  // Генерация уникального ID
        this.name = name;
        this.role = Role.CITIZEN;  // Установка роли по умолчанию
        this.alive = true;  // Игрок стартует живым
    }
}