/*
 * Transaction API
 * Manage the lifecycle of one-time and recurring transactions.
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


import org.openapitools.client.model.TransactionRecordDto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRecordApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public TransactionRecordApi() {
        this(Configuration.getDefaultApiClient());
    }

    public TransactionRecordApi(ApiClient apiClient) {
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
     * Build call for transactionsRecurringTransactionIdRecordsPost
     * @param transactionId Transaction ID (required)
     * @param transactionRecordDto  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call transactionsRecurringTransactionIdRecordsPostCall(String transactionId, TransactionRecordDto transactionRecordDto, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = transactionRecordDto;

        // create path and map variables
        String localVarPath = "/transactions/recurring/{transactionId}/records"
            .replace("{" + "transactionId" + "}", localVarApiClient.escapeString(transactionId.toString()));

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
    private okhttp3.Call transactionsRecurringTransactionIdRecordsPostValidateBeforeCall(String transactionId, TransactionRecordDto transactionRecordDto, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'transactionId' is set
        if (transactionId == null) {
            throw new ApiException("Missing the required parameter 'transactionId' when calling transactionsRecurringTransactionIdRecordsPost(Async)");
        }

        // verify the required parameter 'transactionRecordDto' is set
        if (transactionRecordDto == null) {
            throw new ApiException("Missing the required parameter 'transactionRecordDto' when calling transactionsRecurringTransactionIdRecordsPost(Async)");
        }

        return transactionsRecurringTransactionIdRecordsPostCall(transactionId, transactionRecordDto, _callback);

    }

    /**
     * Create transaction record
     * 
     * @param transactionId Transaction ID (required)
     * @param transactionRecordDto  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public void transactionsRecurringTransactionIdRecordsPost(String transactionId, TransactionRecordDto transactionRecordDto) throws ApiException {
        transactionsRecurringTransactionIdRecordsPostWithHttpInfo(transactionId, transactionRecordDto);
    }

    /**
     * Create transaction record
     * 
     * @param transactionId Transaction ID (required)
     * @param transactionRecordDto  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> transactionsRecurringTransactionIdRecordsPostWithHttpInfo(String transactionId, TransactionRecordDto transactionRecordDto) throws ApiException {
        okhttp3.Call localVarCall = transactionsRecurringTransactionIdRecordsPostValidateBeforeCall(transactionId, transactionRecordDto, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Create transaction record (asynchronously)
     * 
     * @param transactionId Transaction ID (required)
     * @param transactionRecordDto  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully created transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call transactionsRecurringTransactionIdRecordsPostAsync(String transactionId, TransactionRecordDto transactionRecordDto, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = transactionsRecurringTransactionIdRecordsPostValidateBeforeCall(transactionId, transactionRecordDto, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for transactionsRecurringTransactionIdRecordsRecordIdDelete
     * @param transactionId Transaction ID (required)
     * @param recordId Record ID (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully deleted transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction or transaction record with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call transactionsRecurringTransactionIdRecordsRecordIdDeleteCall(String transactionId, String recordId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/transactions/recurring/{transactionId}/records/{recordId}"
            .replace("{" + "transactionId" + "}", localVarApiClient.escapeString(transactionId.toString()))
            .replace("{" + "recordId" + "}", localVarApiClient.escapeString(recordId.toString()));

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
    private okhttp3.Call transactionsRecurringTransactionIdRecordsRecordIdDeleteValidateBeforeCall(String transactionId, String recordId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'transactionId' is set
        if (transactionId == null) {
            throw new ApiException("Missing the required parameter 'transactionId' when calling transactionsRecurringTransactionIdRecordsRecordIdDelete(Async)");
        }

        // verify the required parameter 'recordId' is set
        if (recordId == null) {
            throw new ApiException("Missing the required parameter 'recordId' when calling transactionsRecurringTransactionIdRecordsRecordIdDelete(Async)");
        }

        return transactionsRecurringTransactionIdRecordsRecordIdDeleteCall(transactionId, recordId, _callback);

    }

    /**
     * Delete transaction record
     * 
     * @param transactionId Transaction ID (required)
     * @param recordId Record ID (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully deleted transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction or transaction record with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public void transactionsRecurringTransactionIdRecordsRecordIdDelete(String transactionId, String recordId) throws ApiException {
        transactionsRecurringTransactionIdRecordsRecordIdDeleteWithHttpInfo(transactionId, recordId);
    }

    /**
     * Delete transaction record
     * 
     * @param transactionId Transaction ID (required)
     * @param recordId Record ID (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully deleted transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction or transaction record with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> transactionsRecurringTransactionIdRecordsRecordIdDeleteWithHttpInfo(String transactionId, String recordId) throws ApiException {
        okhttp3.Call localVarCall = transactionsRecurringTransactionIdRecordsRecordIdDeleteValidateBeforeCall(transactionId, recordId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete transaction record (asynchronously)
     * 
     * @param transactionId Transaction ID (required)
     * @param recordId Record ID (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully deleted transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction or transaction record with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call transactionsRecurringTransactionIdRecordsRecordIdDeleteAsync(String transactionId, String recordId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = transactionsRecurringTransactionIdRecordsRecordIdDeleteValidateBeforeCall(transactionId, recordId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for transactionsRecurringTransactionIdRecordsRecordIdTransferPost
     * @param transactionId Transaction ID (required)
     * @param recordId Record ID (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully transferred transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction or transaction record with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call transactionsRecurringTransactionIdRecordsRecordIdTransferPostCall(String transactionId, String recordId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/transactions/recurring/{transactionId}/records/{recordId}/transfer"
            .replace("{" + "transactionId" + "}", localVarApiClient.escapeString(transactionId.toString()))
            .replace("{" + "recordId" + "}", localVarApiClient.escapeString(recordId.toString()));

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
    private okhttp3.Call transactionsRecurringTransactionIdRecordsRecordIdTransferPostValidateBeforeCall(String transactionId, String recordId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'transactionId' is set
        if (transactionId == null) {
            throw new ApiException("Missing the required parameter 'transactionId' when calling transactionsRecurringTransactionIdRecordsRecordIdTransferPost(Async)");
        }

        // verify the required parameter 'recordId' is set
        if (recordId == null) {
            throw new ApiException("Missing the required parameter 'recordId' when calling transactionsRecurringTransactionIdRecordsRecordIdTransferPost(Async)");
        }

        return transactionsRecurringTransactionIdRecordsRecordIdTransferPostCall(transactionId, recordId, _callback);

    }

    /**
     * Transfer transaction record
     * 
     * @param transactionId Transaction ID (required)
     * @param recordId Record ID (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully transferred transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction or transaction record with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public void transactionsRecurringTransactionIdRecordsRecordIdTransferPost(String transactionId, String recordId) throws ApiException {
        transactionsRecurringTransactionIdRecordsRecordIdTransferPostWithHttpInfo(transactionId, recordId);
    }

    /**
     * Transfer transaction record
     * 
     * @param transactionId Transaction ID (required)
     * @param recordId Record ID (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully transferred transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction or transaction record with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> transactionsRecurringTransactionIdRecordsRecordIdTransferPostWithHttpInfo(String transactionId, String recordId) throws ApiException {
        okhttp3.Call localVarCall = transactionsRecurringTransactionIdRecordsRecordIdTransferPostValidateBeforeCall(transactionId, recordId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Transfer transaction record (asynchronously)
     * 
     * @param transactionId Transaction ID (required)
     * @param recordId Record ID (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Successfully transferred transaction record. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Recurring transaction or transaction record with given ID not found. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call transactionsRecurringTransactionIdRecordsRecordIdTransferPostAsync(String transactionId, String recordId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = transactionsRecurringTransactionIdRecordsRecordIdTransferPostValidateBeforeCall(transactionId, recordId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
