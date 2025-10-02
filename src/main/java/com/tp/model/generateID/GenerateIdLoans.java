package com.tp.model.generateID;

import java.util.UUID;

public class GenerateIdLoans {
    public String generateID(){
        String prefix = "LO";
        String unique = UUID.randomUUID().toString().substring(0,4);
        return prefix + unique;
    }
}
