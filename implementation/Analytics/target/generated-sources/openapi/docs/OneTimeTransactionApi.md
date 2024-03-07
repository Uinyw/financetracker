# OneTimeTransactionApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**transactionsOnetimeGet**](OneTimeTransactionApi.md#transactionsOnetimeGet) | **GET** /transactions/onetime | Get all one-time transactions |
| [**transactionsOnetimeIdDelete**](OneTimeTransactionApi.md#transactionsOnetimeIdDelete) | **DELETE** /transactions/onetime/{id} | Delete one-time Transaction by ID |
| [**transactionsOnetimeIdGet**](OneTimeTransactionApi.md#transactionsOnetimeIdGet) | **GET** /transactions/onetime/{id} | Get one-time transaction by ID |
| [**transactionsOnetimeIdPatch**](OneTimeTransactionApi.md#transactionsOnetimeIdPatch) | **PATCH** /transactions/onetime/{id} | Update one-time transaction by ID |
| [**transactionsOnetimeIdTransferPost**](OneTimeTransactionApi.md#transactionsOnetimeIdTransferPost) | **POST** /transactions/onetime/{id}/transfer | Transfer one time transaction |
| [**transactionsOnetimePost**](OneTimeTransactionApi.md#transactionsOnetimePost) | **POST** /transactions/onetime | Create new one-time transaction |


<a id="transactionsOnetimeGet"></a>
# **transactionsOnetimeGet**
> List&lt;OneTimeTransactionDto&gt; transactionsOnetimeGet(sourceBankAccount, targetBankAccount)

Get all one-time transactions

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.OneTimeTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OneTimeTransactionApi apiInstance = new OneTimeTransactionApi(defaultClient);
    UUID sourceBankAccount = UUID.randomUUID(); // UUID | Filter for Transactions with given source bank account ID.
    UUID targetBankAccount = UUID.randomUUID(); // UUID | Filter for Transactions with given target bank account ID.
    try {
      List<OneTimeTransactionDto> result = apiInstance.transactionsOnetimeGet(sourceBankAccount, targetBankAccount);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OneTimeTransactionApi#transactionsOnetimeGet");
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

[**List&lt;OneTimeTransactionDto&gt;**](OneTimeTransactionDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully retrieved a list of all existing one-time transactions. |  -  |

<a id="transactionsOnetimeIdDelete"></a>
# **transactionsOnetimeIdDelete**
> transactionsOnetimeIdDelete(id)

Delete one-time Transaction by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.OneTimeTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OneTimeTransactionApi apiInstance = new OneTimeTransactionApi(defaultClient);
    String id = "id_example"; // String | Transaction ID
    try {
      apiInstance.transactionsOnetimeIdDelete(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling OneTimeTransactionApi#transactionsOnetimeIdDelete");
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
| **200** | Successfully deleted one-time transaction. |  -  |
| **404** | One-Time transaction with given ID not found. |  -  |

<a id="transactionsOnetimeIdGet"></a>
# **transactionsOnetimeIdGet**
> OneTimeTransactionDto transactionsOnetimeIdGet(id)

Get one-time transaction by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.OneTimeTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OneTimeTransactionApi apiInstance = new OneTimeTransactionApi(defaultClient);
    String id = "id_example"; // String | Transaction ID
    try {
      OneTimeTransactionDto result = apiInstance.transactionsOnetimeIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OneTimeTransactionApi#transactionsOnetimeIdGet");
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

[**OneTimeTransactionDto**](OneTimeTransactionDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully retrieved one-time transaction. |  -  |
| **404** | One-Time transaction with given ID not found. |  -  |

<a id="transactionsOnetimeIdPatch"></a>
# **transactionsOnetimeIdPatch**
> transactionsOnetimeIdPatch(id, oneTimeTransactionDto)

Update one-time transaction by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.OneTimeTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OneTimeTransactionApi apiInstance = new OneTimeTransactionApi(defaultClient);
    String id = "id_example"; // String | Transaction ID
    OneTimeTransactionDto oneTimeTransactionDto = new OneTimeTransactionDto(); // OneTimeTransactionDto | 
    try {
      apiInstance.transactionsOnetimeIdPatch(id, oneTimeTransactionDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling OneTimeTransactionApi#transactionsOnetimeIdPatch");
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
| **oneTimeTransactionDto** | [**OneTimeTransactionDto**](OneTimeTransactionDto.md)|  | |

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
| **404** | One-Time transaction with given ID not found. |  -  |

<a id="transactionsOnetimeIdTransferPost"></a>
# **transactionsOnetimeIdTransferPost**
> transactionsOnetimeIdTransferPost(id)

Transfer one time transaction

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.OneTimeTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OneTimeTransactionApi apiInstance = new OneTimeTransactionApi(defaultClient);
    String id = "id_example"; // String | Transaction ID
    try {
      apiInstance.transactionsOnetimeIdTransferPost(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling OneTimeTransactionApi#transactionsOnetimeIdTransferPost");
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
| **200** | Successfully transferred one-time transaction. |  -  |
| **404** | One-Time transaction with given ID not found. |  -  |

<a id="transactionsOnetimePost"></a>
# **transactionsOnetimePost**
> transactionsOnetimePost(oneTimeTransactionDto)

Create new one-time transaction

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.OneTimeTransactionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OneTimeTransactionApi apiInstance = new OneTimeTransactionApi(defaultClient);
    OneTimeTransactionDto oneTimeTransactionDto = new OneTimeTransactionDto(); // OneTimeTransactionDto | 
    try {
      apiInstance.transactionsOnetimePost(oneTimeTransactionDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling OneTimeTransactionApi#transactionsOnetimePost");
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
| **oneTimeTransactionDto** | [**OneTimeTransactionDto**](OneTimeTransactionDto.md)|  | |

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
| **200** | Successfully created a new one-time transactions. |  -  |

