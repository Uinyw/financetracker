package com.bank.account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bank.account.model.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {


}
