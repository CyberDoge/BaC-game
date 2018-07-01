package ru.game.entity.game;

import java.util.Date;

public class Attempt {
    Date datetime;
    int attempt;

    public Attempt(int attempt, Date datetime) {
        this.datetime = datetime;
        this.attempt = attempt;
    }
}
