package com.example.omerta;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

public enum Role implements Serializable {
    MAFIA_DON("Дон мафии", Team.BLACK),
    MAFIA("Мафия", Team.BLACK),
    MANIAC("Маньяк", Team.BLACK),
    COMMISSAR("Комиссар", Team.RED),
    DOCTOR("Доктор", Team.RED),
    CITIZEN("Мирный житель", Team.RED);

//    public static Map<String, Team> roles = Map.of("Дон мафии", Team.BLACK);
//
////    public static void addRole(String role, String color){
////        roles.put(role, color);
////    };

    private final String displayName;
    private final Team team;

    public enum Team {
        RED("Красные"),
        BLACK("Черные");

        private final String name;

        Team(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    Role(String displayName, Team team) {
        this.displayName = displayName;
        this.team = team;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Team getTeam() {
        return team;
    }

}