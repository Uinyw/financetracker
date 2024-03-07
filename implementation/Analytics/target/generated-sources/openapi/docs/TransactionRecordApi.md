# TransactionRecordApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**transactionsRecurringTransactionIdRecordsPost**](TransactionRecordApi.md#transactionsRecurringTransactionIdRecordsPost) | **POST** /transactions/recurring/{transactionId}/records | Create transaction record |
| [**transactionsRecurringTransactionIdRecordsRecordIdDelete**](TransactionRecordApi.md#transactionsRecurringTransactionIdRecordsRecordIdDelete) | **DELETE** /transactions/recurring/{transactionId}/records/{recordId} | Delete transaction record |
| [**transactionsRecurringTransactionIdRecordsRecordIdTransferPost**](TransactionRecordApi.md#transactionsRecurringTransactionIdRecordsRecordIdTransferPost) | **POST** /transactions/recurring/{transactionId}/records/{recordId}/transfer | Transfer transaction record |


<a id="transactionsRecurringTransactionIdRecordsPost"></a>
# **transactionsRecurringTransactionIdRecordsPost**
> transactionsRecurringTransactionIdRecordsPost(transactionId, transactionRecordDto)

Create transaction record

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TransactionRecordApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    TransactionRecordApi apiInstance = new TransactionRecordApi(defaultClient);
    String transactionId = "transactionId_example"; // String | Transaction ID
    TransactionRecordDto transactionRecordDto = new TransactionRecordDto(); // TransactionRecordDto | 
    try {
      apiInstance.transactionsRecurringTransactionIdRecordsPost(transactionId, transactionRecordDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling TransactionRecordApi#transactionsRecurringTransactionIdRecordsPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **transactionId** | **String**| Transaction ID | |
| **transactionRecordDto** | [**TransactionRecordDto**](TransactionRecordDto.md)|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully created transaction record. |  -  |
| **404** | Recurring transaction with given ID not found. |  -  |

<a id="transactionsRecurringTransactionIdRecordsRecordIdDelete"></a>
# **transactionsRecurringTransactionIdRecordsRecordIdDelete**
> transactionsRecurringTransactionIdRecordsRecordIdDelete(transactionId, recordId)

Delete transaction record

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TransactionRecordApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    TransactionRecordApi apiInstance = new TransactionRecordApi(defaultClient);
    String transactionId = "transactionId_example"; // String | Transaction ID
    String recordId = "recordId_example"; // String | Record ID
    try {
      apiInstance.transactionsRecurringTransactionIdRecordsRecordIdDelete(transactionId, recordId);
    } catch (ApiException e) {
      System.err.println("Exception when calling TransactionRecordApi#transactionsRecurringTransactionIdRecordsRecordIdDelete");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **transactionId** | **String**| Transaction ID | |
| **recordId** | **String**| Record ID | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully deleted transaction record. |  -  |
| **404** | Recurring transaction or transaction record with given ID not found. |  -  |

<a id="transactionsRecurringTransactionIdRecordsRecordIdTransferPost"></a>
# **transactionsRecurringTransactionIdRecordsRecordIdTransferPost**
> transactionsRecurringTransactionIdRecordsRecordIdTransferPost(transactionId, recordId)

Transfer transaction record

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TransactionRecordApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    TransactionRecordApi apiInstance = new TransactionRecordApi(defaultClient);
    String transactionId = "transactionId_example"; // String | Transaction ID
    String recordId = "recordId_example"; // String | Record ID
    try {
      apiInstance.transactionsRecurringTransactionIdRecordsRecordIdTransferPost(transactionId, recordId);
    } catch (ApiException e) {
      System.err.println("Exception when calling TransactionRecordApi#transactionsRecurringTransactionIdRecordsRecordIdTransferPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **transactionId** | **String**| Transaction ID | |
| **recordId** | **String**| Record ID | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully transferred transaction record. |  -  |
| **404** | Recurring transaction or transaction record with given ID not found. |  -  |

