# ProductApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**productsGet**](ProductApi.md#productsGet) | **GET** /products | Get all products |
| [**productsIdDelete**](ProductApi.md#productsIdDelete) | **DELETE** /products/{id} | Delete Product by ID |
| [**productsIdGet**](ProductApi.md#productsIdGet) | **GET** /products/{id} | Get Product by ID |
| [**productsIdPatch**](ProductApi.md#productsIdPatch) | **PATCH** /products/{id} | Update product by ID |
| [**productsPost**](ProductApi.md#productsPost) | **POST** /products | Create new product |


<a id="productsGet"></a>
# **productsGet**
> List&lt;ProductDto&gt; productsGet()

Get all products

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ProductApi apiInstance = new ProductApi(defaultClient);
    try {
      List<ProductDto> result = apiInstance.productsGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#productsGet");
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

[**List&lt;ProductDto&gt;**](ProductDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully retrieved a list of all existing products. |  -  |

<a id="productsIdDelete"></a>
# **productsIdDelete**
> productsIdDelete(id)

Delete Product by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ProductApi apiInstance = new ProductApi(defaultClient);
    String id = "id_example"; // String | Product ID
    try {
      apiInstance.productsIdDelete(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#productsIdDelete");
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
| **id** | **String**| Product ID | |

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
| **200** | Successfully deleted product. |  -  |
| **404** | Product with given ID not found. |  -  |

<a id="productsIdGet"></a>
# **productsIdGet**
> ProductDto productsIdGet(id)

Get Product by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ProductApi apiInstance = new ProductApi(defaultClient);
    String id = "id_example"; // String | Product ID
    try {
      ProductDto result = apiInstance.productsIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#productsIdGet");
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
| **id** | **String**| Product ID | |

### Return type

[**ProductDto**](ProductDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successfully retrieved product. |  -  |
| **404** | Product with given ID not found. |  -  |

<a id="productsIdPatch"></a>
# **productsIdPatch**
> productsIdPatch(id, productDto)

Update product by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ProductApi apiInstance = new ProductApi(defaultClient);
    String id = "id_example"; // String | Product ID
    ProductDto productDto = new ProductDto(); // ProductDto | 
    try {
      apiInstance.productsIdPatch(id, productDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#productsIdPatch");
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
| **id** | **String**| Product ID | |
| **productDto** | [**ProductDto**](ProductDto.md)|  | |

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
| **200** | Successfully updated product. |  -  |
| **404** | Product with given ID not found. |  -  |

<a id="productsPost"></a>
# **productsPost**
> productsPost(productDto)

Create new product

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    ProductApi apiInstance = new ProductApi(defaultClient);
    ProductDto productDto = new ProductDto(); // ProductDto | 
    try {
      apiInstance.productsPost(productDto);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductApi#productsPost");
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
| **productDto** | [**ProductDto**](ProductDto.md)|  | |

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
| **200** | Successfully created a new product. |  -  |

