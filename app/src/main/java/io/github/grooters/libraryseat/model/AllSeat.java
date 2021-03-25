package io.github.grooters.libraryseat.model;

import java.util.List;

/**
 * Create by 李林浪 in 2018/10/11
 * Elegant Code...
 */
public class AllSeat {
    private List<Seat> seats;

    public AllSeat(List<Seat> seats) {
        this.seats = seats;
    }

    public AllSeat() {
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
