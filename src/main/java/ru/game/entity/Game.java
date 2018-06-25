package ru.game.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
    private int gameId;
    private List<Attempt> list;

    public Game(int gameId, int secretNum) {
        list = new ArrayList<>();
        this.gameId = gameId;
        this.secretNum = secretNum + "";
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
        secretNum = (int) (Math.random() * 10000) + "";
        for (int i = secretNum.length(); i !=  4; i++)
            secretNum = "0" + secretNum;
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

    public class Response {
        private boolean end = false;

        public boolean isEnd() {
            return end;
        }

        public void setEnd(boolean end) {
            this.end = end;
            setLeave(false);
        }

        private String text;
        private String result;
        private int bulls = 0;
        private int cows = 0;

        private Response(String text) {
            this.text = text;
        }

        public void incrementBulls() {
            bulls++;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getBulls() {
            return bulls;
        }

        public int getCows() {
            return cows;
        }

        public void incrementCows() {
            cows++;
        }
    }

    public class Attempt {
        Date datetime;
        int attempt;

        public Attempt(int attempt, Date datetime) {
            this.datetime = datetime;
            this.attempt = attempt;
        }

        public Attempt(String attempt) throws NumberFormatException {
            this.attempt = Integer.parseInt(attempt);
            datetime = new Date();
        }
    }

}