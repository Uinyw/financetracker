package com.bank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.account.model.BankAccountEntity;


@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {


}
