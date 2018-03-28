/*
 * Kontur.Extern.Api.Public
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package ru.skbkontur.sdk.extern.service.transport.swagger.api;

import ru.skbkontur.sdk.extern.service.transport.swagger.invoker.ApiCallback;
import ru.skbkontur.sdk.extern.service.transport.swagger.invoker.ApiClient;
import ru.skbkontur.sdk.extern.service.transport.swagger.invoker.ApiException;
import ru.skbkontur.sdk.extern.service.transport.swagger.invoker.ApiResponse;
import ru.skbkontur.sdk.extern.service.transport.swagger.invoker.Configuration;
import ru.skbkontur.sdk.extern.service.transport.swagger.invoker.Pair;
import ru.skbkontur.sdk.extern.service.transport.swagger.invoker.ProgressRequestBody;
import ru.skbkontur.sdk.extern.service.transport.swagger.invoker.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import ru.skbkontur.sdk.extern.service.transport.swagger.model.CertificateList;
import ru.skbkontur.sdk.extern.service.transport.swagger.model.Error;
import java.util.UUID;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CertificatesApi {
    private ApiClient apiClient;

    public CertificatesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public CertificatesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for certificatesGetCertificatesAsync
     * @param accountId  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call certificatesGetCertificatesAsyncCall(UUID accountId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/v1/{accountId}/certificates"
            .replaceAll("\\{" + "accountId" + "\\}", apiClient.escapeString(accountId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json", "text/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "apiKey", "auth.sid" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call certificatesGetCertificatesAsyncValidateBeforeCall(UUID accountId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'accountId' is set
        if (accountId == null) {
            throw new ApiException("Missing the required parameter 'accountId' when calling certificatesGetCertificatesAsync(Async)");
        }
        
        
        com.squareup.okhttp.Call call = certificatesGetCertificatesAsyncCall(accountId, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Allow API user to get a description for all user&#39;s certificates
     * 
     * @param accountId  (required)
     * @return CertificateList
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public CertificateList certificatesGetCertificatesAsync(UUID accountId) throws ApiException {
        ApiResponse<CertificateList> resp = certificatesGetCertificatesAsyncWithHttpInfo(accountId);
        return resp.getData();
    }

    /**
     * Allow API user to get a description for all user&#39;s certificates
     * 
     * @param accountId  (required)
     * @return ApiResponse&lt;CertificateList&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<CertificateList> certificatesGetCertificatesAsyncWithHttpInfo(UUID accountId) throws ApiException {
        com.squareup.okhttp.Call call = certificatesGetCertificatesAsyncValidateBeforeCall(accountId, null, null);
        Type localVarReturnType = new TypeToken<CertificateList>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Allow API user to get a description for all user&#39;s certificates (asynchronously)
     * 
     * @param accountId  (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call certificatesGetCertificatesAsyncAsync(UUID accountId, final ApiCallback<CertificateList> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = certificatesGetCertificatesAsyncValidateBeforeCall(accountId, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<CertificateList>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
