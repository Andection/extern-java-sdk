/*
 * Copyright (c) 2019 SKB Kontur
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ru.kontur.extern_api.sdk.httpclient.api;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.kontur.extern_api.sdk.GsonProvider;
import ru.kontur.extern_api.sdk.httpclient.ApiResponseConverter;
import ru.kontur.extern_api.sdk.httpclient.JsonSerialization;
import ru.kontur.extern_api.sdk.httpclient.LibapiResponseConverter;

@JsonSerialization(GsonProvider.LIBAPI)
@ApiResponseConverter(LibapiResponseConverter.class)
public interface DraftsBuilderApi<
        TDraftsBuilder,
        TDraftsBuilderMeta,
        TDraftsBuilderMetaRequest> {

    /**
     * Create new a drafts builder
     *
     * @param accountId private account identifier
     * @param meta drafts builder metadata
     */
    @POST("v1/{accountId}/drafts/builders")
    CompletableFuture<TDraftsBuilder> create(
            @Path("accountId") UUID accountId,
            @Body TDraftsBuilderMetaRequest meta
    );

    /**
     * Get a drafts builder by an identifier
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     */
    @GET("v1/{accountId}/drafts/builders/{draftsBuilderId}")
    CompletableFuture<TDraftsBuilder> get(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId
    );

    /**
     * Delete a drafts builder
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     */
    @DELETE("v1/{accountId}/drafts/builders/{draftsBuilderId}")
    CompletableFuture delete(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId
    );

    /**
     * Get a drafts builder meta by an identifier
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     */
    @GET("v1/{accountId}/drafts/builders/{draftsBuilderId}/meta")
    CompletableFuture<TDraftsBuilderMeta> getMeta(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId
    );

    /**
     * Update a drafts builder meta
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     * @param newMeta drafts builder metadata
     */
    @PUT("v1/{accountId}/drafts/builders/{draftsBuilderId}/meta")
    CompletableFuture<TDraftsBuilderMeta> updateMeta(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId,
            @Body TDraftsBuilderMetaRequest newMeta
    );

    //Task<DraftsBuilderBuildResult> BuildDraftsAsync(Guid draftsBuilderId);
    //Task<ApiTaskResult<DraftsBuilderBuildResult>> StartBuildDraftsAsync(Guid draftsBuilderId);
    //Task<ApiTaskResult<DraftsBuilderBuildResult>> GetBuildResultAsync(Guid draftsBuilderId, Guid apiTaskId);
}
