package io.github.grooters.libraryseat.model;

/**
 * Create by 李林浪 in 2018/10/23
 * Elegant Code...
 */
public class State {
    private String name,totalTime,leaveTime;
    int seatId;

    public State(String name, String totalTime, int seatId, String leaveTime) {
        this.name = name;
        this.totalTime = totalTime;
        this.seatId = seatId;
        this.leaveTime = leaveTime;
    }

    public State() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }
}

