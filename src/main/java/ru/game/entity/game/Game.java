package ru.game.entity.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
    private int gameId;
    private List<Attempt> list;

    public Game(int gameId, int secretNum) {
        list = new ArrayList<>();
        this.gameId = gameId;
        this.secretNum = String.valueOf(secretNum);
    }

    public void insertAttempt(int attempt, Date date) {
        list.add(new Attempt(attempt, date));
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    private String secretNum;
    private boolean leave;
    private int attemptNumber = 0;

    public int incrementAttemptNumber() {
        return ++attemptNumber;
    }

    public Game() {
        leave = true;
        secretNum = String.valueOf((int) (Math.random() * 10000));
        for (int i = secretNum.length(); i !=  4; i++)
            secretNum = "0".concat(secretNum);
        System.out.println("secretNum " + secretNum);
    }

    public String getSecretNum() {
        return secretNum;
    }

    public boolean isLeave() {
        return leave;
    }

    public void setLeave(boolean leave) {
        this.leave = leave;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public Response newResponse(String text) {
        return new Response(text);
    }

    public List<Attempt> getList() {
        return list;
    }


}