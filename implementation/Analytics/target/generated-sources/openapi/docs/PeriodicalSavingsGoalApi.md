# PeriodicalSavingsGoalApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**savingsGoalsPeriodicalGet**](PeriodicalSavingsGoalApi.md#savingsGoalsPeriodicalGet) | **GET** /savings-goals/periodical | Get all Periodical Savings Goals |
| [**savingsGoalsPeriodicalIdDelete**](PeriodicalSavingsGoalApi.md#savingsGoalsPeriodicalIdDelete) | **DELETE** /savings-goals/periodical/{id} | Delete Periodical Savings Goal by ID |
| [**savingsGoalsPeriodicalIdGet**](PeriodicalSavingsGoalApi.md#savingsGoalsPeriodicalIdGet) | **GET** /savings-goals/periodical/{id} | Get Periodical Savings Goal by ID |
| [**savingsGoalsPeriodicalIdPatch**](PeriodicalSavingsGoalApi.md#savingsGoalsPeriodicalIdPatch) | **PATCH** /savings-goals/periodical/{id} | Update Periodical Savings Goal by ID |
| [**savingsGoalsPeriodicalPost**](PeriodicalSavingsGoalApi.md#savingsGoalsPeriodicalPost) | **POST** /savings-goals/periodical | Create a new Periodical Savings Goal |


<a id="savingsGoalsPeriodicalGet"></a>
# **savingsGoalsPeriodicalGet**
> List&lt;PeriodicalSavingsGoalDto&gt; savingsGoalsPeriodicalGet()

Get all Periodical Savings Goals

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PeriodicalSavingsGoalApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    PeriodicalSavingsGoalApi apiInstance = new PeriodicalSavingsGoalApi(defaultClient);
    try {
      List<PeriodicalSavingsGoalDto> result = apiInstance.savingsGoalsPeriodicalGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PeriodicalSavingsGoalApi#savingsGoalsPeriodicalGet");
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

[**List&lt;PeriodicalSavingsGoalDto&gt;**](PeriodicalSavingsGoalDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Retrieve a list of all existing savings goals. |  -  |

<a id="savingsGoalsPeriodicalIdDelete"></a>
# **savingsGoalsPeriodicalIdDelete**
> savingsGoalsPeriodicalIdDelete(id)

Delete Periodical Savings Goal by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PeriodicalSavingsGoalApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    PeriodicalSavingsGoalApi apiInstance = new PeriodicalSavingsGoalApi(defaultClient);
    String id = "id_example"; // String | Savings Goal ID.
    try {
      apiInstance.savingsGoalsPeriodicalIdDelete(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling PeriodicalSavingsGoalApi#savingsGoalsPeriodicalIdDelete");
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
| **id** | **String**| Savings Goal ID. | |

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
| **200** | Periodical Savings Goal deleted successfully |  -  |
| **404** | Periodical Savings Goal not found |  -  |

<a id="savingsGoalsPeriodicalIdGet"></a>
# **savingsGoalsPeriodicalIdGet**
> PeriodicalSavingsGoalDto savingsGoalsPeriodicalIdGet(id)

Get Periodical Savings Goal by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PeriodicalSavingsGoalApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    PeriodicalSavingsGoalApi apiInstance = new PeriodicalSavingsGoalApi(defaultClient);
    String id = "id_example"; // String | Savings Goal ID.
    try {
      PeriodicalSavingsGoalDto result = apiInstance.savingsGoalsPeriodicalIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PeriodicalSavingsGoalApi#savingsGoalsPeriodicalIdGet");
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
| **id** | **String**| Savings Goal ID. | |

### Return type

[**PeriodicalSavingsGoalDto**](PeriodicalSavingsGoalDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Savings Goal retrieved successfully. |  -  |
| **404** | Periodical Savings Goal not found. |  -  |

<a id="savingsGoalsPeriodicalIdPatch"></a>
# **savingsGoalsPeriodicalIdPatch**
> savingsGoalsPeriodicalIdPatch(id, periodicalSavingsGoalDto)

Update Periodical Savings Goal by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PeriodicalSavingsGoalApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    PeriodicalSavingsGoalApi apiInstance = new PeriodicalSavingsGoalApi(defaultClient);
    String id = "id_example"; // String | Savings Goal ID.
    PeriodicalSavingsGoalDto periodicalSavingsGoalDto = new PeriodicalSavingsGoalDto(); // PeriodicalSavingsGoalDto | 
    try {
      apiInstance.savingsGoalsPeriodicalIdPatch(id, periodicalSavingsGoalDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling PeriodicalSavingsGoalApi#savingsGoalsPeriodicalIdPatch");
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
| **id** | **String**| Savings Goal ID. | |
| **periodicalSavingsGoalDto** | [**PeriodicalSavingsGoalDto**](PeriodicalSavingsGoalDto.md)|  | |

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
| **200** | Periodical Savings Goal updated successfully |  -  |
| **404** | Periodical Savings Goal not found |  -  |

<a id="savingsGoalsPeriodicalPost"></a>
# **savingsGoalsPeriodicalPost**
> savingsGoalsPeriodicalPost(periodicalSavingsGoalDto)

Create a new Periodical Savings Goal

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PeriodicalSavingsGoalApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    PeriodicalSavingsGoalApi apiInstance = new PeriodicalSavingsGoalApi(defaultClient);
    PeriodicalSavingsGoalDto periodicalSavingsGoalDto = new PeriodicalSavingsGoalDto(); // PeriodicalSavingsGoalDto | 
    try {
      apiInstance.savingsGoalsPeriodicalPost(periodicalSavingsGoalDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling PeriodicalSavingsGoalApi#savingsGoalsPeriodicalPost");
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
| **periodicalSavingsGoalDto** | [**PeriodicalSavingsGoalDto**](PeriodicalSavingsGoalDto.md)|  | |

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
| **200** | Periodical Savings Goal created. |  -  |

