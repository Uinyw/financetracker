# ShoppingCartApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**shoppingCartEntriesIdDelete**](ShoppingCartApi.md#shoppingCartEntriesIdDelete) | **DELETE** /shopping-cart/entries/{id} | Delete Shopping Cart Entry by ID |
| [**shoppingCartEntriesIdMarkPost**](ShoppingCartApi.md#shoppingCartEntriesIdMarkPost) | **POST** /shopping-cart/entries/{id}/mark | Mark Shopping Cart Entry as Purchased |
| [**shoppingCartEntriesIdPatch**](ShoppingCartApi.md#shoppingCartEntriesIdPatch) | **PATCH** /shopping-cart/entries/{id} | Update Shopping Cart Entry by ID |
| [**shoppingCartEntriesPost**](ShoppingCartApi.md#shoppingCartEntriesPost) | **POST** /shopping-cart/entries | Create new Shopping Cart Entry |
| [**shoppingCartGet**](ShoppingCartApi.md#shoppingCartGet) | **GET** /shopping-cart | Get Shopping Cart |
| [**shoppingCartPurchasePost**](ShoppingCartApi.md#shoppingCartPurchasePost) | **POST** /shopping-cart/purchase | Purchase Marked Products Entries of Shopping Cart |


<a id="shoppingCartEntriesIdDelete"></a>
# **shoppingCartEntriesIdDelete**
> shoppingCartEntriesIdDelete(id)

Delete Shopping Cart Entry by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ShoppingCartApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ShoppingCartApi apiInstance = new ShoppingCartApi(defaultClient);
    String id = "id_example"; // String | Entry ID
    try {
      apiInstance.shoppingCartEntriesIdDelete(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling ShoppingCartApi#shoppingCartEntriesIdDelete");
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

<a id="shoppingCartEntriesIdMarkPost"></a>
# **shoppingCartEntriesIdMarkPost**
> shoppingCartEntriesIdMarkPost(id)

Mark Shopping Cart Entry as Purchased

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ShoppingCartApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ShoppingCartApi apiInstance = new ShoppingCartApi(defaultClient);
    String id = "id_example"; // String | Entry ID
    try {
      apiInstance.shoppingCartEntriesIdMarkPost(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling ShoppingCartApi#shoppingCartEntriesIdMarkPost");
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
| **200** | Successfully checked off entry. |  -  |

<a id="shoppingCartEntriesIdPatch"></a>
# **shoppingCartEntriesIdPatch**
> shoppingCartEntriesIdPatch(id, productEntryDto)

Update Shopping Cart Entry by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ShoppingCartApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ShoppingCartApi apiInstance = new ShoppingCartApi(defaultClient);
    String id = "id_example"; // String | Entry ID
    ProductEntryDto productEntryDto = new ProductEntryDto(); // ProductEntryDto | 
    try {
      apiInstance.shoppingCartEntriesIdPatch(id, productEntryDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling ShoppingCartApi#shoppingCartEntriesIdPatch");
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
| **200** | Successfully updated shopping cart entry. |  -  |

<a id="shoppingCartEntriesPost"></a>
# **shoppingCartEntriesPost**
> shoppingCartEntriesPost(productEntryDto)

Create new Shopping Cart Entry

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ShoppingCartApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ShoppingCartApi apiInstance = new ShoppingCartApi(defaultClient);
    ProductEntryDto productEntryDto = new ProductEntryDto(); // ProductEntryDto | 
    try {
      apiInstance.shoppingCartEntriesPost(productEntryDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling ShoppingCartApi#shoppingCartEntriesPost");
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
| **200** | Successfully created shopping cart entry. |  -  |

<a id="shoppingCartGet"></a>
# **shoppingCartGet**
> ProductEntryCollectionDto shoppingCartGet()

Get Shopping Cart

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ShoppingCartApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ShoppingCartApi apiInstance = new ShoppingCartApi(defaultClient);
    try {
      ProductEntryCollectionDto result = apiInstance.shoppingCartGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ShoppingCartApi#shoppingCartGet");
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
| **200** | Successfully retrieved shopping cart. |  -  |

<a id="shoppingCartPurchasePost"></a>
# **shoppingCartPurchasePost**
> shoppingCartPurchasePost(purchaseDto)

Purchase Marked Products Entries of Shopping Cart

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ShoppingCartApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ShoppingCartApi apiInstance = new ShoppingCartApi(defaultClient);
    PurchaseDto purchaseDto = new PurchaseDto(); // PurchaseDto | 
    try {
      apiInstance.shoppingCartPurchasePost(purchaseDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling ShoppingCartApi#shoppingCartPurchasePost");
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
| **purchaseDto** | [**PurchaseDto**](PurchaseDto.md)|  | |

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
| **200** | Successfully purchased shopping cart. |  -  |

