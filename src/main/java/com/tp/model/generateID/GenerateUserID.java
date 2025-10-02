package com.tp.model.generateID;

import java.util.UUID;

public class GenerateUserID {

    public String generateID() {
        String rand = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "MEM" + rand;
    }
}
