package ru.game.entity.game;

public class Response {
    private boolean end = false;

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    private String text;
    private String result;
    private int bulls = 0;
    private int cows = 0;

    public Response(String text) {
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
