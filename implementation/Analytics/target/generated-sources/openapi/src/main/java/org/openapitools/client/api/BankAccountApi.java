/*
 * Bank Account API
 * Manage the lifecycle of bank accounts.
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


import org.openapitools.client.model.BankAccountDto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankAccountApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public BankAccountApi() {
        this(Configuration.getDefaultApiClient());
    }

    public BankAccountApi(ApiClient apiClient) {
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
     * Build call for bankAccountsGet
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Retrieve a list of all existing bank accounts. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call bankAccountsGetCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/bankAccounts";

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
    private okhttp3.Call bankAccountsGetValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return bankAccountsGetCall(_callback);

    }

    /**
     * Get all existing bank accounts
     * 
     * @return List&lt;BankAccountDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Retrieve a list of all existing bank accounts. </td><td>  -  </td></tr>
     </table>
     */
    public List<BankAccountDto> bankAccountsGet() throws ApiException {
        ApiResponse<List<BankAccountDto>> localVarResp = bankAccountsGetWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Get all existing bank accounts
     * 
     * @return ApiResponse&lt;List&lt;BankAccountDto&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Retrieve a list of all existing bank accounts. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<BankAccountDto>> bankAccountsGetWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = bankAccountsGetValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<List<BankAccountDto>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get all existing bank accounts (asynchronously)
     * 
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Retrieve a list of all existing bank accounts. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call bankAccountsGetAsync(final ApiCallback<List<BankAccountDto>> _callback) throws ApiException {

        okhttp3.Call localVarCall = bankAccountsGetValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<List<BankAccountDto>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for bankAccountsIdDelete
     * @param id Bank Account ID (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully deleted bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank Account not found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call bankAccountsIdDeleteCall(String id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/bankAccounts/{id}"
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
    private okhttp3.Call bankAccountsIdDeleteValidateBeforeCall(String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling bankAccountsIdDelete(Async)");
        }

        return bankAccountsIdDeleteCall(id, _callback);

    }

    /**
     * Delete a bank account by ID
     * 
     * @param id Bank Account ID (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully deleted bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank Account not found </td><td>  -  </td></tr>
     </table>
     */
    public void bankAccountsIdDelete(String id) throws ApiException {
        bankAccountsIdDeleteWithHttpInfo(id);
    }

    /**
     * Delete a bank account by ID
     * 
     * @param id Bank Account ID (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully deleted bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank Account not found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> bankAccountsIdDeleteWithHttpInfo(String id) throws ApiException {
        okhttp3.Call localVarCall = bankAccountsIdDeleteValidateBeforeCall(id, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete a bank account by ID (asynchronously)
     * 
     * @param id Bank Account ID (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully deleted bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank Account not found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call bankAccountsIdDeleteAsync(String id, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = bankAccountsIdDeleteValidateBeforeCall(id, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for bankAccountsIdGet
     * @param id Bank Account ID (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully retrieved bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank account not found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call bankAccountsIdGetCall(String id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/bankAccounts/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

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
    private okhttp3.Call bankAccountsIdGetValidateBeforeCall(String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling bankAccountsIdGet(Async)");
        }

        return bankAccountsIdGetCall(id, _callback);

    }

    /**
     * Get bank account by ID
     * 
     * @param id Bank Account ID (required)
     * @return BankAccountDto
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully retrieved bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank account not found </td><td>  -  </td></tr>
     </table>
     */
    public BankAccountDto bankAccountsIdGet(String id) throws ApiException {
        ApiResponse<BankAccountDto> localVarResp = bankAccountsIdGetWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Get bank account by ID
     * 
     * @param id Bank Account ID (required)
     * @return ApiResponse&lt;BankAccountDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully retrieved bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank account not found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<BankAccountDto> bankAccountsIdGetWithHttpInfo(String id) throws ApiException {
        okhttp3.Call localVarCall = bankAccountsIdGetValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<BankAccountDto>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get bank account by ID (asynchronously)
     * 
     * @param id Bank Account ID (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully retrieved bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank account not found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call bankAccountsIdGetAsync(String id, final ApiCallback<BankAccountDto> _callback) throws ApiException {

        okhttp3.Call localVarCall = bankAccountsIdGetValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<BankAccountDto>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for bankAccountsIdPatch
     * @param id Bank Account ID (required)
     * @param bankAccountDto  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank account not found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call bankAccountsIdPatchCall(String id, BankAccountDto bankAccountDto, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = bankAccountDto;

        // create path and map variables
        String localVarPath = "/bankAccounts/{id}"
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
    private okhttp3.Call bankAccountsIdPatchValidateBeforeCall(String id, BankAccountDto bankAccountDto, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling bankAccountsIdPatch(Async)");
        }

        // verify the required parameter 'bankAccountDto' is set
        if (bankAccountDto == null) {
            throw new ApiException("Missing the required parameter 'bankAccountDto' when calling bankAccountsIdPatch(Async)");
        }

        return bankAccountsIdPatchCall(id, bankAccountDto, _callback);

    }

    /**
     * Update bank account by ID
     * 
     * @param id Bank Account ID (required)
     * @param bankAccountDto  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank account not found </td><td>  -  </td></tr>
     </table>
     */
    public void bankAccountsIdPatch(String id, BankAccountDto bankAccountDto) throws ApiException {
        bankAccountsIdPatchWithHttpInfo(id, bankAccountDto);
    }

    /**
     * Update bank account by ID
     * 
     * @param id Bank Account ID (required)
     * @param bankAccountDto  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank account not found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> bankAccountsIdPatchWithHttpInfo(String id, BankAccountDto bankAccountDto) throws ApiException {
        okhttp3.Call localVarCall = bankAccountsIdPatchValidateBeforeCall(id, bankAccountDto, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Update bank account by ID (asynchronously)
     * 
     * @param id Bank Account ID (required)
     * @param bankAccountDto  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully updated bank account. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Bank account not found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call bankAccountsIdPatchAsync(String id, BankAccountDto bankAccountDto, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = bankAccountsIdPatchValidateBeforeCall(id, bankAccountDto, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for bankAccountsPost
     * @param bankAccountDto  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created bank account. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call bankAccountsPostCall(BankAccountDto bankAccountDto, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = bankAccountDto;

        // create path and map variables
        String localVarPath = "/bankAccounts";

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
    private okhttp3.Call bankAccountsPostValidateBeforeCall(BankAccountDto bankAccountDto, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'bankAccountDto' is set
        if (bankAccountDto == null) {
            throw new ApiException("Missing the required parameter 'bankAccountDto' when calling bankAccountsPost(Async)");
        }

        return bankAccountsPostCall(bankAccountDto, _callback);

    }

    /**
     * Create a new bank account
     * 
     * @param bankAccountDto  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created bank account. </td><td>  -  </td></tr>
     </table>
     */
    public void bankAccountsPost(BankAccountDto bankAccountDto) throws ApiException {
        bankAccountsPostWithHttpInfo(bankAccountDto);
    }

    /**
     * Create a new bank account
     * 
     * @param bankAccountDto  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created bank account. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> bankAccountsPostWithHttpInfo(BankAccountDto bankAccountDto) throws ApiException {
        okhttp3.Call localVarCall = bankAccountsPostValidateBeforeCall(bankAccountDto, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Create a new bank account (asynchronously)
     * 
     * @param bankAccountDto  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created bank account. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call bankAccountsPostAsync(BankAccountDto bankAccountDto, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = bankAccountsPostValidateBeforeCall(bankAccountDto, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
