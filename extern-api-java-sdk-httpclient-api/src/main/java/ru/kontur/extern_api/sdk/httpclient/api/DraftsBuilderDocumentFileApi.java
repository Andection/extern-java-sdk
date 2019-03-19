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

public interface DraftsBuilderDocumentFileApi<TDraftsBuilderDocumentFile, TDraftsBuilderDocumentFileContents, TDraftsBuilderDocumentFileMeta, TDraftsBuilderDocumentFileMetaRequest> {

    /**
     * Create new a drafts builder document file
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     * @param draftsBuilderDocumentId drafts builder document identifier
     * @param contents drafts builder document file contents
     */
    @POST("v1/{accountId}/drafts/builders/{draftsBuilderId}/documents/{draftsBuilderDocumentId}/files")
    CompletableFuture<TDraftsBuilderDocumentFile> create(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId,
            @Path("draftsBuilderDocumentId") UUID draftsBuilderDocumentId,
            @Body TDraftsBuilderDocumentFileContents contents
    );

    /**
     * Get all drafts builder document files inside drafts builder document
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     * @param draftsBuilderDocumentId drafts builder document identifier
     */
    @GET("v1/{accountId}/drafts/builders/{draftsBuilderId}/documents/{draftsBuilderDocumentId}/files")
    CompletableFuture<TDraftsBuilderDocumentFile> getAll(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId,
            @Path("draftsBuilderDocumentId") UUID draftsBuilderDocumentId
    );

    /**
     * Get a drafts builder document file by an identifier
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     * @param draftsBuilderDocumentId drafts builder document identifier
     * @param draftsBuilderDocumentFileId drafts builder document file identifier
     */
    @GET("v1/{accountId}/drafts/builders/{draftsBuilderId}/documents/{draftsBuilderDocumentId}/files/{draftsBuilderDocumentFileId}")
    CompletableFuture<TDraftsBuilderDocumentFile> get(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId,
            @Path("draftsBuilderDocumentId") UUID draftsBuilderDocumentId,
            @Path("draftsBuilderDocumentFileId") UUID draftsBuilderDocumentFileId
    );

    /**
     * Get a drafts builder document file by an identifier
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     * @param draftsBuilderDocumentId drafts builder document identifier
     * @param draftsBuilderDocumentFileId drafts builder document file identifier
     * @param newContents new drafts builder document file contents
     */
    @PUT("v1/{accountId}/drafts/builders/{draftsBuilderId}/documents/{draftsBuilderDocumentId}/files/{draftsBuilderDocumentFileId}")
    CompletableFuture<TDraftsBuilderDocumentFile> update(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId,
            @Path("draftsBuilderDocumentId") UUID draftsBuilderDocumentId,
            @Path("draftsBuilderDocumentFileId") UUID draftsBuilderDocumentFileId,
            TDraftsBuilderDocumentFileContents newContents
    );

    /**
     * Delete a drafts builder document file
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     * @param draftsBuilderDocumentId drafts builder document identifier
     * @param draftsBuilderDocumentFileId drafts builder document file identifier
     */
    @DELETE("v1/{accountId}/drafts/builders/{draftsBuilderId}/documents/{draftsBuilderDocumentId}/files/{draftsBuilderDocumentFileId}")
    CompletableFuture delete(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId,
            @Path("draftsBuilderDocumentId") UUID draftsBuilderDocumentId,
            @Path("draftsBuilderDocumentFileId") UUID draftsBuilderDocumentFileId
    );

    /**
     * Get a drafts builder document file content
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     * @param draftsBuilderDocumentId drafts builder document identifier
     * @param draftsBuilderDocumentFileId drafts builder document file identifier
     */
    @GET("/v1/{accountId}/drafts/builders/{draftsBuilderId}/documents/{draftsBuilderDocumentId}/files/{draftsBuilderDocumentFileId}/content")
    CompletableFuture<byte[]> getContent(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId,
            @Path("draftsBuilderDocumentId") UUID draftsBuilderDocumentId,
            @Path("draftsBuilderDocumentFileId") UUID draftsBuilderDocumentFileId
    );

    /**
     * Delete a drafts builder document file signature content
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     * @param draftsBuilderDocumentId drafts builder document identifier
     * @param draftsBuilderDocumentFileId drafts builder document file identifier
     */
    @GET("/v1/{accountId}/drafts/builders/{draftsBuilderId}/documents/{draftsBuilderDocumentId}/files/{draftsBuilderDocumentFileId}/signature")
    CompletableFuture<byte[]> getSignature(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId,
            @Path("draftsBuilderDocumentId") UUID draftsBuilderDocumentId,
            @Path("draftsBuilderDocumentFileId") UUID draftsBuilderDocumentFileId
    );

    /**
     * Get a drafts builder document file meta by an identifier
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     * @param draftsBuilderDocumentId drafts builder document identifier
     * @param draftsBuilderDocumentFileId drafts builder document file identifier
     */
    @GET("v1/{accountId}/drafts/builders/{draftsBuilderId}/documents/{draftsBuilderDocumentId}/files/{draftsBuilderDocumentFileId}/meta")
    CompletableFuture<TDraftsBuilderDocumentFileMeta> getMeta(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId,
            @Path("draftsBuilderDocumentId") UUID draftsBuilderDocumentId,
            @Path("draftsBuilderDocumentFileId") UUID draftsBuilderDocumentFileId
    );

    /**
     * Update a drafts builder document file meta
     *
     * @param accountId private account identifier
     * @param draftsBuilderId drafts builder identifier
     * @param draftsBuilderDocumentId drafts builder document identifier
     * @param draftsBuilderDocumentFileId drafts builder document file identifier
     * @param newMeta drafts builder document file metadata
     */
    @PUT("v1/{accountId}/drafts/builders/{draftsBuilderId}/documents/{draftsBuilderDocumentId}/files/{draftsBuilderDocumentFileId}/meta")
    CompletableFuture<TDraftsBuilderDocumentFileMeta> updateMeta(
            @Path("accountId") UUID accountId,
            @Path("draftsBuilderId") UUID draftsBuilderId,
            @Path("draftsBuilderDocumentId") UUID draftsBuilderDocumentId,
            @Path("draftsBuilderDocumentFileId") UUID draftsBuilderDocumentFileId,
            @Body TDraftsBuilderDocumentFileMetaRequest newMeta
    );
}
