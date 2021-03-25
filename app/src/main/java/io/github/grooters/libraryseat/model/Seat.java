package io.github.grooters.libraryseat.model;

import org.litepal.crud.LitePalSupport;

public class Seat extends LitePalSupport{
    private int id,floor,number,idle;
    private String userNumber;
    private String leavetime;
    private int time;

    public Seat(int id, int floor, int number, int idle, String userNumber, String leavetime, int time) {
        this.id = id;
        this.floor = floor;
        this.number = number;
        this.idle = idle;
        this.userNumber = userNumber;
        this.leavetime = leavetime;
        this.time = time;
    }

    public Seat() {
    }

    public int getTime() {
        return time;
    }

    public String getLeavetime() {
        return leavetime;
    }

    public void setLeavetime(String leavetime) {
        this.leavetime = leavetime;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIdle() {
        return idle;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}
