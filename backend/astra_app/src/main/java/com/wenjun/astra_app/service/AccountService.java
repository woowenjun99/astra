package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.LinkAccountDTO;

public interface AccountService {
    void linkAccount(LinkAccountDTO request) throws AstraException;

    void unlinkAccount(LinkAccountDTO request) throws AstraException;
}
