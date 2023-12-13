package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.Transaction;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * BankAccount
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-13T10:13:06.151214400+01:00[Europe/Berlin]")
public class BankAccount {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("balance")
  private MonetaryAmount balance;

  @JsonProperty("dispoLimit")
  private MonetaryAmount dispoLimit;

  @JsonProperty("labels")
  @Valid
  private List<String> labels = null;

  @JsonProperty("transactions")
  @Valid
  private List<Transaction> transactions = null;

  public BankAccount id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @Valid 
  @Schema(name = "id", required = false)
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BankAccount name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", required = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BankAccount description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", required = false)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BankAccount balance(MonetaryAmount balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
  */
  @Valid 
  @Schema(name = "balance", required = false)
  public MonetaryAmount getBalance() {
    return balance;
  }

  public void setBalance(MonetaryAmount balance) {
    this.balance = balance;
  }

  public BankAccount dispoLimit(MonetaryAmount dispoLimit) {
    this.dispoLimit = dispoLimit;
    return this;
  }

  /**
   * Get dispoLimit
   * @return dispoLimit
  */
  @Valid 
  @Schema(name = "dispoLimit", required = false)
  public MonetaryAmount getDispoLimit() {
    return dispoLimit;
  }

  public void setDispoLimit(MonetaryAmount dispoLimit) {
    this.dispoLimit = dispoLimit;
  }

  public BankAccount labels(List<String> labels) {
    this.labels = labels;
    return this;
  }

  public BankAccount addLabelsItem(String labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

  /**
   * Get labels
   * @return labels
  */
  
  @Schema(name = "labels", required = false)
  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public BankAccount transactions(List<Transaction> transactions) {
    this.transactions = transactions;
    return this;
  }

  public BankAccount addTransactionsItem(Transaction transactionsItem) {
    if (this.transactions == null) {
      this.transactions = new ArrayList<>();
    }
    this.transactions.add(transactionsItem);
    return this;
  }

  /**
   * Get transactions
   * @return transactions
  */
  @Valid 
  @Schema(name = "transactions", required = false)
  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankAccount bankAccount = (BankAccount) o;
    return Objects.equals(this.id, bankAccount.id) &&
        Objects.equals(this.name, bankAccount.name) &&
        Objects.equals(this.description, bankAccount.description) &&
        Objects.equals(this.balance, bankAccount.balance) &&
        Objects.equals(this.dispoLimit, bankAccount.dispoLimit) &&
        Objects.equals(this.labels, bankAccount.labels) &&
        Objects.equals(this.transactions, bankAccount.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, balance, dispoLimit, labels, transactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankAccount {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    dispoLimit: ").append(toIndentedString(dispoLimit)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

