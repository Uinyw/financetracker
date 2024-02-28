package com.bank.account.logic.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankAccountTest {

    @Test
    void givenBankAccount_whenSetAttributes_thenAttributesAreUpdated() {
        final var bankAccount = BankAccount.with()
                .id(UUID.randomUUID().toString())
                .name("Basic Account")
                .description("A basic account")
                .balance(MonetaryAmount.DEFAULT)
                .dispositionLimit(MonetaryAmount.DEFAULT)
                .labels(Set.of(new Label("Basic")))
                .build();

        bankAccount.setName("New Account");
        bankAccount.setDescription("A new account");
        bankAccount.setBalance(new MonetaryAmount(BigDecimal.ONE));
        bankAccount.setDispositionLimit(new MonetaryAmount(BigDecimal.ONE));
        bankAccount.setLabels(Set.of(new Label("New")));

        assertThat(bankAccount.getName(), is("New Account"));
        assertThat(bankAccount.getDescription(), is("A new account"));
        assertThat(bankAccount.getBalance().amount(), comparesEqualTo(BigDecimal.ONE));
        assertThat(bankAccount.getDispositionLimit().amount(), comparesEqualTo(BigDecimal.ONE));
        assertThat(bankAccount.getLabels(), contains(new Label("New")));
    }

    @Test
    void givenBankAccountBuilder_whenToString_thenStringRepresentationContainsValidId() {
        final var bankAccountId = UUID.randomUUID().toString();
        final var bankAccount = BankAccount.with()
                .id(bankAccountId)
                .name("Basic Account")
                .description("A basic account")
                .balance(MonetaryAmount.DEFAULT)
                .dispositionLimit(MonetaryAmount.DEFAULT)
                .labels(Set.of(new Label("Basic")))
                .toString();
        assertTrue(bankAccount.contains("id=" + bankAccountId));
    }
}
