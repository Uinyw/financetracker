/*
 * Transaction API
 * Manage the lifecycle of one-time and recurring transactions.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.client.model.PeriodicityDto;
import org.openapitools.client.model.TransactionRecordDto;
import org.openapitools.client.model.TransferDto;
import org.openapitools.client.model.TypeDto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * RecurringTransactionDto
 */
@lombok.Builder @lombok.AllArgsConstructor
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-03-05T07:47:24.900141500+01:00[Europe/Berlin]")
public class RecurringTransactionDto {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private UUID id;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private TypeDto type;

  public static final String SERIALIZED_NAME_LABELS = "labels";
  @SerializedName(SERIALIZED_NAME_LABELS)
  private List<String> labels;

  public static final String SERIALIZED_NAME_TRANSFER = "transfer";
  @SerializedName(SERIALIZED_NAME_TRANSFER)
  private TransferDto transfer;

  public static final String SERIALIZED_NAME_PERIODICITY = "periodicity";
  @SerializedName(SERIALIZED_NAME_PERIODICITY)
  private PeriodicityDto periodicity;

  public static final String SERIALIZED_NAME_START_DATE = "startDate";
  @SerializedName(SERIALIZED_NAME_START_DATE)
  private String startDate;

  public static final String SERIALIZED_NAME_FIXED_AMOUNT = "fixedAmount";
  @SerializedName(SERIALIZED_NAME_FIXED_AMOUNT)
  private MonetaryAmountDto fixedAmount;

  public static final String SERIALIZED_NAME_TRANSACTION_RECORDS = "transactionRecords";
  @SerializedName(SERIALIZED_NAME_TRANSACTION_RECORDS)
  private List<TransactionRecordDto> transactionRecords;

  public RecurringTransactionDto() {
  }

  public RecurringTransactionDto id(UUID id) {
    
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @jakarta.annotation.Nullable
  public UUID getId() {
    return id;
  }


  public void setId(UUID id) {
    this.id = id;
  }


  public RecurringTransactionDto name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @jakarta.annotation.Nullable
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public RecurringTransactionDto description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @jakarta.annotation.Nullable
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public RecurringTransactionDto type(TypeDto type) {
    
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @jakarta.annotation.Nullable
  public TypeDto getType() {
    return type;
  }


  public void setType(TypeDto type) {
    this.type = type;
  }


  public RecurringTransactionDto labels(List<String> labels) {
    
    this.labels = labels;
    return this;
  }

  public RecurringTransactionDto addLabelsItem(String labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

   /**
   * Get labels
   * @return labels
  **/
  @jakarta.annotation.Nullable
  public List<String> getLabels() {
    return labels;
  }


  public void setLabels(List<String> labels) {
    this.labels = labels;
  }


  public RecurringTransactionDto transfer(TransferDto transfer) {
    
    this.transfer = transfer;
    return this;
  }

   /**
   * Get transfer
   * @return transfer
  **/
  @jakarta.annotation.Nullable
  public TransferDto getTransfer() {
    return transfer;
  }


  public void setTransfer(TransferDto transfer) {
    this.transfer = transfer;
  }


  public RecurringTransactionDto periodicity(PeriodicityDto periodicity) {
    
    this.periodicity = periodicity;
    return this;
  }

   /**
   * Get periodicity
   * @return periodicity
  **/
  @jakarta.annotation.Nullable
  public PeriodicityDto getPeriodicity() {
    return periodicity;
  }


  public void setPeriodicity(PeriodicityDto periodicity) {
    this.periodicity = periodicity;
  }


  public RecurringTransactionDto startDate(String startDate) {
    
    this.startDate = startDate;
    return this;
  }

   /**
   * Get startDate
   * @return startDate
  **/
  @jakarta.annotation.Nullable
  public String getStartDate() {
    return startDate;
  }


  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }


  public RecurringTransactionDto fixedAmount(MonetaryAmountDto fixedAmount) {
    
    this.fixedAmount = fixedAmount;
    return this;
  }

   /**
   * Get fixedAmount
   * @return fixedAmount
  **/
  @jakarta.annotation.Nullable
  public MonetaryAmountDto getFixedAmount() {
    return fixedAmount;
  }


  public void setFixedAmount(MonetaryAmountDto fixedAmount) {
    this.fixedAmount = fixedAmount;
  }


  public RecurringTransactionDto transactionRecords(List<TransactionRecordDto> transactionRecords) {
    
    this.transactionRecords = transactionRecords;
    return this;
  }

  public RecurringTransactionDto addTransactionRecordsItem(TransactionRecordDto transactionRecordsItem) {
    if (this.transactionRecords == null) {
      this.transactionRecords = new ArrayList<>();
    }
    this.transactionRecords.add(transactionRecordsItem);
    return this;
  }

   /**
   * Get transactionRecords
   * @return transactionRecords
  **/
  @jakarta.annotation.Nullable
  public List<TransactionRecordDto> getTransactionRecords() {
    return transactionRecords;
  }


  public void setTransactionRecords(List<TransactionRecordDto> transactionRecords) {
    this.transactionRecords = transactionRecords;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecurringTransactionDto recurringTransactionDto = (RecurringTransactionDto) o;
    return Objects.equals(this.id, recurringTransactionDto.id) &&
        Objects.equals(this.name, recurringTransactionDto.name) &&
        Objects.equals(this.description, recurringTransactionDto.description) &&
        Objects.equals(this.type, recurringTransactionDto.type) &&
        Objects.equals(this.labels, recurringTransactionDto.labels) &&
        Objects.equals(this.transfer, recurringTransactionDto.transfer) &&
        Objects.equals(this.periodicity, recurringTransactionDto.periodicity) &&
        Objects.equals(this.startDate, recurringTransactionDto.startDate) &&
        Objects.equals(this.fixedAmount, recurringTransactionDto.fixedAmount) &&
        Objects.equals(this.transactionRecords, recurringTransactionDto.transactionRecords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, type, labels, transfer, periodicity, startDate, fixedAmount, transactionRecords);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecurringTransactionDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    transfer: ").append(toIndentedString(transfer)).append("\n");
    sb.append("    periodicity: ").append(toIndentedString(periodicity)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    fixedAmount: ").append(toIndentedString(fixedAmount)).append("\n");
    sb.append("    transactionRecords: ").append(toIndentedString(transactionRecords)).append("\n");
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


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("id");
    openapiFields.add("name");
    openapiFields.add("description");
    openapiFields.add("type");
    openapiFields.add("labels");
    openapiFields.add("transfer");
    openapiFields.add("periodicity");
    openapiFields.add("startDate");
    openapiFields.add("fixedAmount");
    openapiFields.add("transactionRecords");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to RecurringTransactionDto
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!RecurringTransactionDto.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in RecurringTransactionDto is not found in the empty JSON string", RecurringTransactionDto.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!RecurringTransactionDto.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `RecurringTransactionDto` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("id") != null && !jsonObj.get("id").isJsonNull()) && !jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if ((jsonObj.get("name") != null && !jsonObj.get("name").isJsonNull()) && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull()) && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      // validate the optional field `type`
      if (jsonObj.get("type") != null && !jsonObj.get("type").isJsonNull()) {
        TypeDto.validateJsonElement(jsonObj.get("type"));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("labels") != null && !jsonObj.get("labels").isJsonNull() && !jsonObj.get("labels").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `labels` to be an array in the JSON string but got `%s`", jsonObj.get("labels").toString()));
      }
      // validate the optional field `transfer`
      if (jsonObj.get("transfer") != null && !jsonObj.get("transfer").isJsonNull()) {
        TransferDto.validateJsonElement(jsonObj.get("transfer"));
      }
      // validate the optional field `periodicity`
      if (jsonObj.get("periodicity") != null && !jsonObj.get("periodicity").isJsonNull()) {
        PeriodicityDto.validateJsonElement(jsonObj.get("periodicity"));
      }
      if ((jsonObj.get("startDate") != null && !jsonObj.get("startDate").isJsonNull()) && !jsonObj.get("startDate").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `startDate` to be a primitive type in the JSON string but got `%s`", jsonObj.get("startDate").toString()));
      }
      // validate the optional field `fixedAmount`
      if (jsonObj.get("fixedAmount") != null && !jsonObj.get("fixedAmount").isJsonNull()) {
        MonetaryAmountDto.validateJsonElement(jsonObj.get("fixedAmount"));
      }
      if (jsonObj.get("transactionRecords") != null && !jsonObj.get("transactionRecords").isJsonNull()) {
        JsonArray jsonArraytransactionRecords = jsonObj.getAsJsonArray("transactionRecords");
        if (jsonArraytransactionRecords != null) {
          // ensure the json data is an array
          if (!jsonObj.get("transactionRecords").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `transactionRecords` to be an array in the JSON string but got `%s`", jsonObj.get("transactionRecords").toString()));
          }

          // validate the optional field `transactionRecords` (array)
          for (int i = 0; i < jsonArraytransactionRecords.size(); i++) {
            TransactionRecordDto.validateJsonElement(jsonArraytransactionRecords.get(i));
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!RecurringTransactionDto.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'RecurringTransactionDto' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<RecurringTransactionDto> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(RecurringTransactionDto.class));

       return (TypeAdapter<T>) new TypeAdapter<RecurringTransactionDto>() {
           @Override
           public void write(JsonWriter out, RecurringTransactionDto value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public RecurringTransactionDto read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of RecurringTransactionDto given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of RecurringTransactionDto
  * @throws IOException if the JSON string is invalid with respect to RecurringTransactionDto
  */
  public static RecurringTransactionDto fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, RecurringTransactionDto.class);
  }

 /**
  * Convert an instance of RecurringTransactionDto to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

