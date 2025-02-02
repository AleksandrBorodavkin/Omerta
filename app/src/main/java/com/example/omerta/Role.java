package com.example.omerta;

import lombok.Getter;

@Getter
public enum Role   {
    // Черные (Мафия, убийцы, преступники)
    DON("Дон мафии", Team.BLACK, "Глава мафии, решает, кого убить"),
    MAFIA("Мафия", Team.BLACK, "Обычный член мафии, участвует в голосовании на убийство"),
    MANIAC("Маньяк", Team.BLACK, "Действует в одиночку, убивает каждую ночь"),
    YAKUZA("Якудза", Team.BLACK, "Может пожертвовать собой, чтобы завербовать мирного жителя"),

    // Красные (Граждане, защитники, правоохранители)
    COMMISSAR("Комиссар", Team.RED, "Проверяет людей на принадлежность к мафии"),
    SHERIFF("Шериф", Team.RED, "Имеет единственный выстрел за игру, может ликвидировать подозреваемого"),
    DOCTOR("Доктор", Team.RED, "Может лечить одного человека за ночь, предотвращая убийство"),
    CITIZEN("Мирный житель", Team.RED, "Обычный житель без особых способностей");

    private final String displayName;
    private final Team team;
    private final String description;

    // Перечисление команд (мафия и граждане)
    @Getter
    public enum Team {
        RED("Красные"), // Мирные жители
        BLACK("Черные"); // Преступники

        private final String name;

        Team(String name) {
            this.name = name;
        }

    }

    Role(String displayName, Team team, String description) {
        this.displayName = displayName;
        this.team = team;
        this.description = description;
    }

}
