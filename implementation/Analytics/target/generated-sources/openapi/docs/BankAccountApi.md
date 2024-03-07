# BankAccountApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**bankAccountsGet**](BankAccountApi.md#bankAccountsGet) | **GET** /bankAccounts | Get all existing bank accounts |
| [**bankAccountsIdDelete**](BankAccountApi.md#bankAccountsIdDelete) | **DELETE** /bankAccounts/{id} | Delete a bank account by ID |
| [**bankAccountsIdGet**](BankAccountApi.md#bankAccountsIdGet) | **GET** /bankAccounts/{id} | Get bank account by ID |
| [**bankAccountsIdPatch**](BankAccountApi.md#bankAccountsIdPatch) | **PATCH** /bankAccounts/{id} | Update bank account by ID |
| [**bankAccountsPost**](BankAccountApi.md#bankAccountsPost) | **POST** /bankAccounts | Create a new bank account |


<a id="bankAccountsGet"></a>
# **bankAccountsGet**
> List&lt;BankAccountDto&gt; bankAccountsGet()

Get all existing bank accounts

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BankAccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    BankAccountApi apiInstance = new BankAccountApi(defaultClient);
    try {
      List<BankAccountDto> result = apiInstance.bankAccountsGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BankAccountApi#bankAccountsGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;BankAccountDto&gt;**](BankAccountDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Retrieve a list of all existing bank accounts. |  -  |

<a id="bankAccountsIdDelete"></a>
# **bankAccountsIdDelete**
> bankAccountsIdDelete(id)

Delete a bank account by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BankAccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    BankAccountApi apiInstance = new BankAccountApi(defaultClient);
    String id = "id_example"; // String | Bank Account ID
    try {
      apiInstance.bankAccountsIdDelete(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling BankAccountApi#bankAccountsIdDelete");
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
| **id** | **String**| Bank Account ID | |

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
| **200** | Successfully deleted bank account. |  -  |
| **404** | Bank Account not found |  -  |

<a id="bankAccountsIdGet"></a>
# **bankAccountsIdGet**
> BankAccountDto bankAccountsIdGet(id)

Get bank account by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BankAccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    BankAccountApi apiInstance = new BankAccountApi(defaultClient);
    String id = "id_example"; // String | Bank Account ID
    try {
      BankAccountDto result = apiInstance.bankAccountsIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BankAccountApi#bankAccountsIdGet");
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
| **id** | **String**| Bank Account ID | |

### Return type

[**BankAccountDto**](BankAccountDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully retrieved bank account. |  -  |
| **404** | Bank account not found |  -  |

<a id="bankAccountsIdPatch"></a>
# **bankAccountsIdPatch**
> bankAccountsIdPatch(id, bankAccountDto)

Update bank account by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BankAccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    BankAccountApi apiInstance = new BankAccountApi(defaultClient);
    String id = "id_example"; // String | Bank Account ID
    BankAccountDto bankAccountDto = new BankAccountDto(); // BankAccountDto | 
    try {
      apiInstance.bankAccountsIdPatch(id, bankAccountDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling BankAccountApi#bankAccountsIdPatch");
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
| **id** | **String**| Bank Account ID | |
| **bankAccountDto** | [**BankAccountDto**](BankAccountDto.md)|  | |

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
| **200** | Successfully updated bank account. |  -  |
| **404** | Bank account not found |  -  |

<a id="bankAccountsPost"></a>
# **bankAccountsPost**
> bankAccountsPost(bankAccountDto)

Create a new bank account

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BankAccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    BankAccountApi apiInstance = new BankAccountApi(defaultClient);
    BankAccountDto bankAccountDto = new BankAccountDto(); // BankAccountDto | 
    try {
      apiInstance.bankAccountsPost(bankAccountDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling BankAccountApi#bankAccountsPost");
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
| **bankAccountDto** | [**BankAccountDto**](BankAccountDto.md)|  | |

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
| **200** | Successfully created bank account. |  -  |

