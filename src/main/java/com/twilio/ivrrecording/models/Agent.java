package com.twilio.ivrrecording.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "extension")
    private String extension;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "agent")
    private List<Recording> recordings;

    public Agent() {
    }

    public Agent(String extension, String phoneNumber) {
        this.extension = extension;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void addReservation(Recording reservation) {

        if (this.recordings.add(reservation) && reservation.getAgent() != this) {
            reservation.setAgent(this);
        }
    }

    public void removeReservation(Recording reservation) {
        if (this.recordings.remove(reservation) && reservation.getAgent() == this) {
            reservation.setAgent(null);
        }
    }
}