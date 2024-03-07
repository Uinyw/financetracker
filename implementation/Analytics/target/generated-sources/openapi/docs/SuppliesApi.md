# SuppliesApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**suppliesEntriesIdDelete**](SuppliesApi.md#suppliesEntriesIdDelete) | **DELETE** /supplies/entries/{id} | Delete Supply Entry by ID |
| [**suppliesEntriesIdPatch**](SuppliesApi.md#suppliesEntriesIdPatch) | **PATCH** /supplies/entries/{id} | Update Supply Entry by ID |
| [**suppliesEntriesPost**](SuppliesApi.md#suppliesEntriesPost) | **POST** /supplies/entries | Create new Supply Entry |
| [**suppliesGet**](SuppliesApi.md#suppliesGet) | **GET** /supplies | Get Supplies |
| [**suppliesShopPost**](SuppliesApi.md#suppliesShopPost) | **POST** /supplies/shop | Add Required Product Entries to Shopping Cart |


<a id="suppliesEntriesIdDelete"></a>
# **suppliesEntriesIdDelete**
> suppliesEntriesIdDelete(id)

Delete Supply Entry by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SuppliesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SuppliesApi apiInstance = new SuppliesApi(defaultClient);
    String id = "id_example"; // String | Entry ID
    try {
      apiInstance.suppliesEntriesIdDelete(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling SuppliesApi#suppliesEntriesIdDelete");
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
| **id** | **String**| Entry ID | |

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
| **200** | Successfully delete entry. |  -  |

<a id="suppliesEntriesIdPatch"></a>
# **suppliesEntriesIdPatch**
> suppliesEntriesIdPatch(id, productEntryDto)

Update Supply Entry by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SuppliesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SuppliesApi apiInstance = new SuppliesApi(defaultClient);
    String id = "id_example"; // String | Entry ID
    ProductEntryDto productEntryDto = new ProductEntryDto(); // ProductEntryDto | 
    try {
      apiInstance.suppliesEntriesIdPatch(id, productEntryDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling SuppliesApi#suppliesEntriesIdPatch");
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
| **id** | **String**| Entry ID | |
| **productEntryDto** | [**ProductEntryDto**](ProductEntryDto.md)|  | |

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
| **200** | Successfully updated supply entry. |  -  |

<a id="suppliesEntriesPost"></a>
# **suppliesEntriesPost**
> suppliesEntriesPost(productEntryDto)

Create new Supply Entry

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SuppliesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SuppliesApi apiInstance = new SuppliesApi(defaultClient);
    ProductEntryDto productEntryDto = new ProductEntryDto(); // ProductEntryDto | 
    try {
      apiInstance.suppliesEntriesPost(productEntryDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling SuppliesApi#suppliesEntriesPost");
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
| **productEntryDto** | [**ProductEntryDto**](ProductEntryDto.md)|  | |

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
| **200** | Successfully created supply entry. |  -  |

<a id="suppliesGet"></a>
# **suppliesGet**
> ProductEntryCollectionDto suppliesGet()

Get Supplies

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SuppliesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SuppliesApi apiInstance = new SuppliesApi(defaultClient);
    try {
      ProductEntryCollectionDto result = apiInstance.suppliesGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SuppliesApi#suppliesGet");
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

[**ProductEntryCollectionDto**](ProductEntryCollectionDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully retrieved supplies. |  -  |

<a id="suppliesShopPost"></a>
# **suppliesShopPost**
> suppliesShopPost()

Add Required Product Entries to Shopping Cart

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SuppliesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SuppliesApi apiInstance = new SuppliesApi(defaultClient);
    try {
      apiInstance.suppliesShopPost();
    } catch (ApiException e) {
      System.err.println("Exception when calling SuppliesApi#suppliesShopPost");
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

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully updated shopping cart. |  -  |

