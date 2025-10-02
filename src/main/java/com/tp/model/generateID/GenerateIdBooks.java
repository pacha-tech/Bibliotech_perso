package com.tp.model.generateID;

import java.util.UUID;

public class GenerateIdBooks {
    public String generateID(){
        String prefix = "BO";
        String unique = UUID.randomUUID().toString().substring(0,4);
        return prefix + unique;
    }
}
