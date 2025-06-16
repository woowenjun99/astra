package com.wenjun.astra_app.plugins.accounts;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.enums.accounts.Provider;

public interface ProviderPlugin {
    Provider getProvider();

    void createUser(CreateUserDTO request) throws AstraException;
}
