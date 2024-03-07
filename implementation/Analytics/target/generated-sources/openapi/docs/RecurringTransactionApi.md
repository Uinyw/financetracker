# RecurringTransactionApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**transactionsRecurringGet**](RecurringTransactionApi.md#transactionsRecurringGet) | **GET** /transactions/recurring | Get all recurring transactions |
| [**transactionsRecurringIdDelete**](RecurringTransactionApi.md#transactionsRecurringIdDelete) | **DELETE** /transactions/recurring/{id} | Delete recurring transaction by ID |
| [**transactionsRecurringIdGet**](RecurringTransactionApi.md#transactionsRecurringIdGet) | **GET** /transactions/recurring/{id} | Get recurring transaction by ID |
| [**transactionsRecurringIdPatch**](RecurringTransactionApi.md#transactionsRecurringIdPatch) | **PATCH** /transactions/recurring/{id} | Update recurring transaction by ID |
| [**transactionsRecurringPost**](RecurringTransactionApi.md#transactionsRecurringPost) | **POST** /transactions/recurring | Create new recurring transaction |


<a id="transactionsRecurringGet"></a>
# **transactionsRecurringGet**
> List&lt;RecurringTransactionDto&gt; transactionsRecurringGet(sourceBankAccount, targetBankAccount)

Get all recurring transactions

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RecurringTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RecurringTransactionApi apiInstance = new RecurringTransactionApi(defaultClient);
    UUID sourceBankAccount = UUID.randomUUID(); // UUID | Filter for Transactions with given source bank account ID.
    UUID targetBankAccount = UUID.randomUUID(); // UUID | Filter for Transactions with given target bank account ID.
    try {
      List<RecurringTransactionDto> result = apiInstance.transactionsRecurringGet(sourceBankAccount, targetBankAccount);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RecurringTransactionApi#transactionsRecurringGet");
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
| **sourceBankAccount** | **UUID**| Filter for Transactions with given source bank account ID. | [optional] |
| **targetBankAccount** | **UUID**| Filter for Transactions with given target bank account ID. | [optional] |

### Return type

[**List&lt;RecurringTransactionDto&gt;**](RecurringTransactionDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully retrieved a list of all existing recurring transactions. |  -  |

<a id="transactionsRecurringIdDelete"></a>
# **transactionsRecurringIdDelete**
> transactionsRecurringIdDelete(id)

Delete recurring transaction by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RecurringTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RecurringTransactionApi apiInstance = new RecurringTransactionApi(defaultClient);
    String id = "id_example"; // String | Transaction ID
    try {
      apiInstance.transactionsRecurringIdDelete(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling RecurringTransactionApi#transactionsRecurringIdDelete");
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
| **id** | **String**| Transaction ID | |

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
| **200** | Successfully deleted recurring transaction. |  -  |
| **404** | Recurring transaction with given ID not found. |  -  |

<a id="transactionsRecurringIdGet"></a>
# **transactionsRecurringIdGet**
> RecurringTransactionDto transactionsRecurringIdGet(id)

Get recurring transaction by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RecurringTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RecurringTransactionApi apiInstance = new RecurringTransactionApi(defaultClient);
    String id = "id_example"; // String | Transaction ID
    try {
      RecurringTransactionDto result = apiInstance.transactionsRecurringIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RecurringTransactionApi#transactionsRecurringIdGet");
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
| **id** | **String**| Transaction ID | |

### Return type

[**RecurringTransactionDto**](RecurringTransactionDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully retrieved recurring transaction. |  -  |
| **404** | Recurring transaction with given ID not found. |  -  |

<a id="transactionsRecurringIdPatch"></a>
# **transactionsRecurringIdPatch**
> transactionsRecurringIdPatch(id, recurringTransactionDto)

Update recurring transaction by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RecurringTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RecurringTransactionApi apiInstance = new RecurringTransactionApi(defaultClient);
    String id = "id_example"; // String | Transaction ID
    RecurringTransactionDto recurringTransactionDto = new RecurringTransactionDto(); // RecurringTransactionDto | 
    try {
      apiInstance.transactionsRecurringIdPatch(id, recurringTransactionDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling RecurringTransactionApi#transactionsRecurringIdPatch");
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
| **id** | **String**| Transaction ID | |
| **recurringTransactionDto** | [**RecurringTransactionDto**](RecurringTransactionDto.md)|  | |

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
| **200** | Successfully updated transaction. |  -  |
| **404** | Recurring transaction with given ID not found. |  -  |

<a id="transactionsRecurringPost"></a>
# **transactionsRecurringPost**
> transactionsRecurringPost(recurringTransactionDto)

Create new recurring transaction

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RecurringTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RecurringTransactionApi apiInstance = new RecurringTransactionApi(defaultClient);
    RecurringTransactionDto recurringTransactionDto = new RecurringTransactionDto(); // RecurringTransactionDto | 
    try {
      apiInstance.transactionsRecurringPost(recurringTransactionDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling RecurringTransactionApi#transactionsRecurringPost");
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
| **recurringTransactionDto** | [**RecurringTransactionDto**](RecurringTransactionDto.md)|  | |

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
| **200** | Successfully created a new recurring transactions. |  -  |

