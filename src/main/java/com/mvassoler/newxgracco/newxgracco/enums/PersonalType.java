package com.mvassoler.newxgracco.newxgracco.enums;

public enum PersonalType {

    F("Física"),
    J("Jurídica");

    private final String type;

    PersonalType(String type) {
        this.type = type;
    }

    public String getPersonalType() {
        return type;
    }
}
