package com.wenjun.astra_app.model.enums.users;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHERS("Others");

    private String alias;
    private static HashMap<String, Gender> mappers;

    Gender(String alias) {
        this.alias = alias;
    }

    static {
        mappers = new HashMap<>();
        for (Gender gender: Gender.values()) {
            mappers.put(gender.getAlias(), gender);
        }
    }

    public static Gender getByAlias(String alias) throws AstraException {
        Gender gender = mappers.getOrDefault(alias, null);
        if (gender == null) {
            throw new AstraException(AstraExceptionEnum.INVALID_PARAMETERS, "Invalid gender");
        }
        return gender;
    }
}
