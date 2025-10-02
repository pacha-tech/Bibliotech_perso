package com.tp.model.generateID;

import java.util.UUID;

public class GenerateReservationID {

    public String generateID() {
        String rand = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "RES" + rand;
    }
}
