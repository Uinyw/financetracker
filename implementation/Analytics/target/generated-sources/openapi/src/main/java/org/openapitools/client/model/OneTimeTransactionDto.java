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
import org.openapitools.client.model.TransferDto;
import org.openapitools.client.model.TransferStatusDto;
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
 * OneTimeTransactionDto
 */
@lombok.Builder @lombok.AllArgsConstructor
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-03-05T07:47:24.900141500+01:00[Europe/Berlin]")
public class OneTimeTransactionDto {
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

  public static final String SERIALIZED_NAME_AMOUNT = "amount";
  @SerializedName(SERIALIZED_NAME_AMOUNT)
  private MonetaryAmountDto amount;

  public static final String SERIALIZED_NAME_DATE = "date";
  @SerializedName(SERIALIZED_NAME_DATE)
  private String date;

  public static final String SERIALIZED_NAME_TRANSFER_STATUS = "transferStatus";
  @SerializedName(SERIALIZED_NAME_TRANSFER_STATUS)
  private TransferStatusDto transferStatus;

  public OneTimeTransactionDto() {
  }

  public OneTimeTransactionDto id(UUID id) {
    
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


  public OneTimeTransactionDto name(String name) {
    
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


  public OneTimeTransactionDto description(String description) {
    
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


  public OneTimeTransactionDto type(TypeDto type) {
    
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


  public OneTimeTransactionDto labels(List<String> labels) {
    
    this.labels = labels;
    return this;
  }

  public OneTimeTransactionDto addLabelsItem(String labelsItem) {
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


  public OneTimeTransactionDto transfer(TransferDto transfer) {
    
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


  public OneTimeTransactionDto amount(MonetaryAmountDto amount) {
    
    this.amount = amount;
    return this;
  }

   /**
   * Get amount
   * @return amount
  **/
  @jakarta.annotation.Nullable
  public MonetaryAmountDto getAmount() {
    return amount;
  }


  public void setAmount(MonetaryAmountDto amount) {
    this.amount = amount;
  }


  public OneTimeTransactionDto date(String date) {
    
    this.date = date;
    return this;
  }

   /**
   * Get date
   * @return date
  **/
  @jakarta.annotation.Nullable
  public String getDate() {
    return date;
  }


  public void setDate(String date) {
    this.date = date;
  }


  public OneTimeTransactionDto transferStatus(TransferStatusDto transferStatus) {
    
    this.transferStatus = transferStatus;
    return this;
  }

   /**
   * Get transferStatus
   * @return transferStatus
  **/
  @jakarta.annotation.Nullable
  public TransferStatusDto getTransferStatus() {
    return transferStatus;
  }


  public void setTransferStatus(TransferStatusDto transferStatus) {
    this.transferStatus = transferStatus;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OneTimeTransactionDto oneTimeTransactionDto = (OneTimeTransactionDto) o;
    return Objects.equals(this.id, oneTimeTransactionDto.id) &&
        Objects.equals(this.name, oneTimeTransactionDto.name) &&
        Objects.equals(this.description, oneTimeTransactionDto.description) &&
        Objects.equals(this.type, oneTimeTransactionDto.type) &&
        Objects.equals(this.labels, oneTimeTransactionDto.labels) &&
        Objects.equals(this.transfer, oneTimeTransactionDto.transfer) &&
        Objects.equals(this.amount, oneTimeTransactionDto.amount) &&
        Objects.equals(this.date, oneTimeTransactionDto.date) &&
        Objects.equals(this.transferStatus, oneTimeTransactionDto.transferStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, type, labels, transfer, amount, date, transferStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OneTimeTransactionDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    transfer: ").append(toIndentedString(transfer)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    transferStatus: ").append(toIndentedString(transferStatus)).append("\n");
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
    openapiFields.add("amount");
    openapiFields.add("date");
    openapiFields.add("transferStatus");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to OneTimeTransactionDto
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!OneTimeTransactionDto.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in OneTimeTransactionDto is not found in the empty JSON string", OneTimeTransactionDto.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!OneTimeTransactionDto.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `OneTimeTransactionDto` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
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
      // validate the optional field `amount`
      if (jsonObj.get("amount") != null && !jsonObj.get("amount").isJsonNull()) {
        MonetaryAmountDto.validateJsonElement(jsonObj.get("amount"));
      }
      if ((jsonObj.get("date") != null && !jsonObj.get("date").isJsonNull()) && !jsonObj.get("date").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `date` to be a primitive type in the JSON string but got `%s`", jsonObj.get("date").toString()));
      }
      // validate the optional field `transferStatus`
      if (jsonObj.get("transferStatus") != null && !jsonObj.get("transferStatus").isJsonNull()) {
        TransferStatusDto.validateJsonElement(jsonObj.get("transferStatus"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!OneTimeTransactionDto.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'OneTimeTransactionDto' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<OneTimeTransactionDto> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(OneTimeTransactionDto.class));

       return (TypeAdapter<T>) new TypeAdapter<OneTimeTransactionDto>() {
           @Override
           public void write(JsonWriter out, OneTimeTransactionDto value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public OneTimeTransactionDto read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of OneTimeTransactionDto given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of OneTimeTransactionDto
  * @throws IOException if the JSON string is invalid with respect to OneTimeTransactionDto
  */
  public static OneTimeTransactionDto fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, OneTimeTransactionDto.class);
  }

 /**
  * Convert an instance of OneTimeTransactionDto to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

