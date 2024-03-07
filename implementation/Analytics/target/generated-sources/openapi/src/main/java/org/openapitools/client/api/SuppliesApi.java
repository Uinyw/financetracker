/*
 * Product API
 * Manage products, supplies and shopping carts.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiCallback;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.Configuration;
import org.openapitools.client.Pair;
import org.openapitools.client.ProgressRequestBody;
import org.openapitools.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import org.openapitools.client.model.ProductEntryCollectionDto;
import org.openapitools.client.model.ProductEntryDto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuppliesApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public SuppliesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public SuppliesApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    /**
     * Build call for suppliesEntriesIdDelete
     * @param id Entry ID (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully delete entry. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call suppliesEntriesIdDeleteCall(String id, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/supplies/entries/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call suppliesEntriesIdDeleteValidateBeforeCall(String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling suppliesEntriesIdDelete(Async)");
        }

        return suppliesEntriesIdDeleteCall(id, _callback);

    }

    /**
     * Delete Supply Entry by ID
     * 
     * @param id Entry ID (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully delete entry. </td><td>  -  </td></tr>
     </table>
     */
    public void suppliesEntriesIdDelete(String id) throws ApiException {
        suppliesEntriesIdDeleteWithHttpInfo(id);
    }

    /**
     * Delete Supply Entry by ID
     * 
     * @param id Entry ID (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully delete entry. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> suppliesEntriesIdDeleteWithHttpInfo(String id) throws ApiException {
        okhttp3.Call localVarCall = suppliesEntriesIdDeleteValidateBeforeCall(id, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Supply Entry by ID (asynchronously)
     * 
     * @param id Entry ID (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully delete entry. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call suppliesEntriesIdDeleteAsync(String id, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = suppliesEntriesIdDeleteValidateBeforeCall(id, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for suppliesEntriesIdPatch
     * @param id Entry ID (required)
     * @param productEntryDto  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated supply entry. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call suppliesEntriesIdPatchCall(String id, ProductEntryDto productEntryDto, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = productEntryDto;

        // create path and map variables
        String localVarPath = "/supplies/entries/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call suppliesEntriesIdPatchValidateBeforeCall(String id, ProductEntryDto productEntryDto, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling suppliesEntriesIdPatch(Async)");
        }

        // verify the required parameter 'productEntryDto' is set
        if (productEntryDto == null) {
            throw new ApiException("Missing the required parameter 'productEntryDto' when calling suppliesEntriesIdPatch(Async)");
        }

        return suppliesEntriesIdPatchCall(id, productEntryDto, _callback);

    }

    /**
     * Update Supply Entry by ID
     * 
     * @param id Entry ID (required)
     * @param productEntryDto  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated supply entry. </td><td>  -  </td></tr>
     </table>
     */
    public void suppliesEntriesIdPatch(String id, ProductEntryDto productEntryDto) throws ApiException {
        suppliesEntriesIdPatchWithHttpInfo(id, productEntryDto);
    }

    /**
     * Update Supply Entry by ID
     * 
     * @param id Entry ID (required)
     * @param productEntryDto  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated supply entry. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> suppliesEntriesIdPatchWithHttpInfo(String id, ProductEntryDto productEntryDto) throws ApiException {
        okhttp3.Call localVarCall = suppliesEntriesIdPatchValidateBeforeCall(id, productEntryDto, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Update Supply Entry by ID (asynchronously)
     * 
     * @param id Entry ID (required)
     * @param productEntryDto  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated supply entry. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call suppliesEntriesIdPatchAsync(String id, ProductEntryDto productEntryDto, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = suppliesEntriesIdPatchValidateBeforeCall(id, productEntryDto, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for suppliesEntriesPost
     * @param productEntryDto  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created supply entry. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call suppliesEntriesPostCall(ProductEntryDto productEntryDto, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = productEntryDto;

        // create path and map variables
        String localVarPath = "/supplies/entries";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call suppliesEntriesPostValidateBeforeCall(ProductEntryDto productEntryDto, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'productEntryDto' is set
        if (productEntryDto == null) {
            throw new ApiException("Missing the required parameter 'productEntryDto' when calling suppliesEntriesPost(Async)");
        }

        return suppliesEntriesPostCall(productEntryDto, _callback);

    }

    /**
     * Create new Supply Entry
     * 
     * @param productEntryDto  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created supply entry. </td><td>  -  </td></tr>
     </table>
     */
    public void suppliesEntriesPost(ProductEntryDto productEntryDto) throws ApiException {
        suppliesEntriesPostWithHttpInfo(productEntryDto);
    }

    /**
     * Create new Supply Entry
     * 
     * @param productEntryDto  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created supply entry. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> suppliesEntriesPostWithHttpInfo(ProductEntryDto productEntryDto) throws ApiException {
        okhttp3.Call localVarCall = suppliesEntriesPostValidateBeforeCall(productEntryDto, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Create new Supply Entry (asynchronously)
     * 
     * @param productEntryDto  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created supply entry. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call suppliesEntriesPostAsync(ProductEntryDto productEntryDto, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = suppliesEntriesPostValidateBeforeCall(productEntryDto, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for suppliesGet
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully retrieved supplies. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call suppliesGetCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/supplies";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call suppliesGetValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return suppliesGetCall(_callback);

    }

    /**
     * Get Supplies
     * 
     * @return ProductEntryCollectionDto
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully retrieved supplies. </td><td>  -  </td></tr>
     </table>
     */
    public ProductEntryCollectionDto suppliesGet() throws ApiException {
        ApiResponse<ProductEntryCollectionDto> localVarResp = suppliesGetWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Get Supplies
     * 
     * @return ApiResponse&lt;ProductEntryCollectionDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully retrieved supplies. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ProductEntryCollectionDto> suppliesGetWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = suppliesGetValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<ProductEntryCollectionDto>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Supplies (asynchronously)
     * 
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully retrieved supplies. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call suppliesGetAsync(final ApiCallback<ProductEntryCollectionDto> _callback) throws ApiException {

        okhttp3.Call localVarCall = suppliesGetValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<ProductEntryCollectionDto>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for suppliesShopPost
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated shopping cart. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call suppliesShopPostCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/supplies/shop";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call suppliesShopPostValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return suppliesShopPostCall(_callback);

    }

    /**
     * Add Required Product Entries to Shopping Cart
     * 
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated shopping cart. </td><td>  -  </td></tr>
     </table>
     */
    public void suppliesShopPost() throws ApiException {
        suppliesShopPostWithHttpInfo();
    }

    /**
     * Add Required Product Entries to Shopping Cart
     * 
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated shopping cart. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> suppliesShopPostWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = suppliesShopPostValidateBeforeCall(null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Add Required Product Entries to Shopping Cart (asynchronously)
     * 
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated shopping cart. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call suppliesShopPostAsync(final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = suppliesShopPostValidateBeforeCall(_callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
