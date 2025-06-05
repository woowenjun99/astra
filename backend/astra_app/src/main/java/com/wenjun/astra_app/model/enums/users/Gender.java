package com.wenjun.astra_app.model.enums.users;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum Gender {
    MALE(0, "Male"),
    FEMALE(1, "Female"),
    NON_BINARY(2, "Non-binary"),
    OTHERS(3, "Others"),
    PREFER_NOT_TO_SAY(4, "Prefer not to say");

    private final Integer code;
    private final String alias;
    private static HashMap<String, Gender> aliasToEnum;

    Gender(Integer code, String alias) {
        this.code = code;
        this.alias = alias;
    }

    static {
        aliasToEnum = new HashMap<>();
        for (Gender gender: Gender.values()) {
            aliasToEnum.put(gender.getAlias(), gender);
        }
    }

    public static Gender getByAlias(String alias) throws AstraException {
        Gender results = aliasToEnum.getOrDefault(alias, null);
        if (results == null) {
            throw new AstraException(AstraExceptionEnum.INVALID_PARAMETERS, "Invalid gender alias. Received %s");
        }
        return results;
    }
}
