# RuleBasedSavingsGoalApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**savingsGoalsRuleBasedGet**](RuleBasedSavingsGoalApi.md#savingsGoalsRuleBasedGet) | **GET** /savings-goals/rule-based | Get all Rule-based Savings Goals |
| [**savingsGoalsRuleBasedIdDelete**](RuleBasedSavingsGoalApi.md#savingsGoalsRuleBasedIdDelete) | **DELETE** /savings-goals/rule-based/{id} | Delete Rule-based Savings Goal by ID |
| [**savingsGoalsRuleBasedIdGet**](RuleBasedSavingsGoalApi.md#savingsGoalsRuleBasedIdGet) | **GET** /savings-goals/rule-based/{id} | Get Rule-based Savings Goal by ID |
| [**savingsGoalsRuleBasedIdPatch**](RuleBasedSavingsGoalApi.md#savingsGoalsRuleBasedIdPatch) | **PATCH** /savings-goals/rule-based/{id} | Update Rule-based Savings Goal by ID |
| [**savingsGoalsRuleBasedPost**](RuleBasedSavingsGoalApi.md#savingsGoalsRuleBasedPost) | **POST** /savings-goals/rule-based | Create a new Rule-based Savings Goal |


<a id="savingsGoalsRuleBasedGet"></a>
# **savingsGoalsRuleBasedGet**
> List&lt;RuleBasedSavingsGoalDto&gt; savingsGoalsRuleBasedGet()

Get all Rule-based Savings Goals

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RuleBasedSavingsGoalApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RuleBasedSavingsGoalApi apiInstance = new RuleBasedSavingsGoalApi(defaultClient);
    try {
      List<RuleBasedSavingsGoalDto> result = apiInstance.savingsGoalsRuleBasedGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RuleBasedSavingsGoalApi#savingsGoalsRuleBasedGet");
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

[**List&lt;RuleBasedSavingsGoalDto&gt;**](RuleBasedSavingsGoalDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Retrieve a list of all existing rule-based savings goals. |  -  |

<a id="savingsGoalsRuleBasedIdDelete"></a>
# **savingsGoalsRuleBasedIdDelete**
> savingsGoalsRuleBasedIdDelete(id)

Delete Rule-based Savings Goal by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RuleBasedSavingsGoalApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RuleBasedSavingsGoalApi apiInstance = new RuleBasedSavingsGoalApi(defaultClient);
    String id = "id_example"; // String | Savings Goal ID.
    try {
      apiInstance.savingsGoalsRuleBasedIdDelete(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling RuleBasedSavingsGoalApi#savingsGoalsRuleBasedIdDelete");
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
| **200** | Rule Based Savings Goal deleted successfully |  -  |
| **404** | Rule Based Savings Goal not found |  -  |

<a id="savingsGoalsRuleBasedIdGet"></a>
# **savingsGoalsRuleBasedIdGet**
> RuleBasedSavingsGoalDto savingsGoalsRuleBasedIdGet(id)

Get Rule-based Savings Goal by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RuleBasedSavingsGoalApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RuleBasedSavingsGoalApi apiInstance = new RuleBasedSavingsGoalApi(defaultClient);
    String id = "id_example"; // String | Savings Goal ID.
    try {
      RuleBasedSavingsGoalDto result = apiInstance.savingsGoalsRuleBasedIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RuleBasedSavingsGoalApi#savingsGoalsRuleBasedIdGet");
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

[**RuleBasedSavingsGoalDto**](RuleBasedSavingsGoalDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Savings Goal retrieved successfully. |  -  |
| **404** | Savings Goal not found |  -  |

<a id="savingsGoalsRuleBasedIdPatch"></a>
# **savingsGoalsRuleBasedIdPatch**
> savingsGoalsRuleBasedIdPatch(id, ruleBasedSavingsGoalDto)

Update Rule-based Savings Goal by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RuleBasedSavingsGoalApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RuleBasedSavingsGoalApi apiInstance = new RuleBasedSavingsGoalApi(defaultClient);
    String id = "id_example"; // String | Savings Goal ID.
    RuleBasedSavingsGoalDto ruleBasedSavingsGoalDto = new RuleBasedSavingsGoalDto(); // RuleBasedSavingsGoalDto | 
    try {
      apiInstance.savingsGoalsRuleBasedIdPatch(id, ruleBasedSavingsGoalDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling RuleBasedSavingsGoalApi#savingsGoalsRuleBasedIdPatch");
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
| **ruleBasedSavingsGoalDto** | [**RuleBasedSavingsGoalDto**](RuleBasedSavingsGoalDto.md)|  | |

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
| **200** | Rule Based Savings Goal updated successfully |  -  |
| **404** | Rule Based Savings Goal not found |  -  |

<a id="savingsGoalsRuleBasedPost"></a>
# **savingsGoalsRuleBasedPost**
> savingsGoalsRuleBasedPost(ruleBasedSavingsGoalDto)

Create a new Rule-based Savings Goal

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RuleBasedSavingsGoalApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    RuleBasedSavingsGoalApi apiInstance = new RuleBasedSavingsGoalApi(defaultClient);
    RuleBasedSavingsGoalDto ruleBasedSavingsGoalDto = new RuleBasedSavingsGoalDto(); // RuleBasedSavingsGoalDto | 
    try {
      apiInstance.savingsGoalsRuleBasedPost(ruleBasedSavingsGoalDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling RuleBasedSavingsGoalApi#savingsGoalsRuleBasedPost");
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
| **ruleBasedSavingsGoalDto** | [**RuleBasedSavingsGoalDto**](RuleBasedSavingsGoalDto.md)|  | |

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
| **200** | Rule Based Savings Goal created. |  -  |

