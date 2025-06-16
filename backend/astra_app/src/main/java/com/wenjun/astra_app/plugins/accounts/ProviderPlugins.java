package com.wenjun.astra_app.plugins.accounts;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.enums.accounts.Provider;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ProviderPlugins {
    private final HashMap<Provider, ProviderPlugin> mappers;

    public ProviderPlugins(List<ProviderPlugin> plugins) {
        mappers = new HashMap<>();
        plugins.forEach(plugin -> mappers.put(plugin.getProvider(), plugin));
    }

    public ProviderPlugin getPlugin(Provider provider) throws AstraException {
        ProviderPlugin plugin = mappers.getOrDefault(provider, null);
        if (plugin == null) {
            throw new AstraException(AstraExceptionEnum.INVALID_PARAMETERS, "Unsupported provider");
        }
        return plugin;
    }
}
