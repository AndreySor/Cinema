package edu.school21.cinema.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cinema_halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    private Long hallId;

    @Column(name = "serial_number")
    private Integer serialNumber;

    @Column(name = "seats_number")
    private Integer seatsNumber;

    @OneToMany(mappedBy = "hall")
    private Set<Session> sessions;

    public Hall() {
    }

    public Hall(Long hallId, Integer serialNumber, Integer seatsNumber) {
        this.hallId = hallId;
        this.serialNumber = serialNumber;
        this.seatsNumber = seatsNumber;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
    }
}
