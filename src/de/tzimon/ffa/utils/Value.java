package de.tzimon.ffa.utils;

public enum Value {

    DEATH("death", 0),
    GAME("game", 180),
    BUILD("build", 100);

    public final String name;
    public final int defaultValue;

    Value(String name, int defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

}
