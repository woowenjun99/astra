package com.wenjun.astra_app.model.enums.accounts;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum Provider {
    PASSWORD("password"),
    GOOGLE("google.com");

    private final String providerId;
    private static final HashMap<String, Provider> mappers;

    static {
        mappers = new HashMap<>();
        for (Provider provider: Provider.values()) {
            mappers.put(provider.providerId, provider);
        }
    }

    Provider(String providerId) {
        this.providerId = providerId;
    }

    public static Provider getByProviderId(String providerId) throws AstraException {
        Provider provider = mappers.getOrDefault(providerId, null);
        if (provider == null) {
            throw new AstraException(AstraExceptionEnum.INVALID_PARAMETERS, "Invalid provider ID");
        }
        return provider;
    }
}
