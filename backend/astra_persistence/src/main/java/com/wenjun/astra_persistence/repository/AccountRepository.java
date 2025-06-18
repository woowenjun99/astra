package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.AccountEntityMapper;
import com.wenjun.astra_persistence.mappers.manual.ManualAccountEntityMapper;
import com.wenjun.astra_persistence.models.AccountEntity;
import com.wenjun.astra_persistence.models.AccountEntityExample;

import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;

@Repository
public class AccountRepository {
    @Resource
    private AccountEntityMapper accountEntityMapper;
    @Resource
    private ManualAccountEntityMapper manualAccountEntityMapper;

    public void insertSelective(AccountEntity accountEntity) {
        accountEntityMapper.insertSelective(accountEntity);
    }

    public boolean doesAccountExist(String uid, String provider) {
        AccountEntityExample example = new AccountEntityExample();
        example
                .createCriteria()
                .andUidEqualTo(uid)
                .andProviderIdEqualTo(provider);
        return accountEntityMapper.countByExample(example) != 0;
    }

    public boolean isEmailAlreadyInUseByPasswordProvider(String email) {
        Long count = manualAccountEntityMapper.countByEmailAndPasswordProvider(email);
        return count != 0;
    }

    public void deleteByUidAndProvider(String uid, String provider) {
        AccountEntityExample example = new AccountEntityExample();
        example.createCriteria().andUidEqualTo(uid).andProviderIdEqualTo(provider);
        accountEntityMapper.deleteByExample(example);
    }
}
