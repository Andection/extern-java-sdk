/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.skbkontur.sdk.extern.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.joda.time.DateTime;
import ru.skbkontur.sdk.extern.model.Docflow;
import ru.skbkontur.sdk.extern.model.DocflowPage;
import ru.skbkontur.sdk.extern.model.Document;
import ru.skbkontur.sdk.extern.model.DocumentDescription;
import ru.skbkontur.sdk.extern.model.Signature;
import ru.skbkontur.sdk.extern.service.DocflowService;
import ru.skbkontur.sdk.extern.service.transport.adaptors.DocflowsAdaptor;
import ru.skbkontur.sdk.extern.service.transport.adaptors.QueryContext;
import ru.skbkontur.sdk.extern.model.DocumentToSend;

/**
 *
 * @author AlexS
 */
public class DocflowServiceImpl extends BaseService<DocflowsAdaptor> implements DocflowService {
	private static final String EN_DFW = "Документооборот";
	private static final String EN_DOC = "Документ";
	private static final String EN_SGN = "Подпись";
	
	/**
	 * Allow API user to get Docflow object
	 * 
	 * GET /v1/{accountId}/docflows/{docflowId}
	 * 
	 * @param docflowId String an docflow identity
	 * 
	 * @return CompletableFuture&lt;QueryContext&lt;Docflow&gt;&gt;
	 */
	@Override
	public CompletableFuture<QueryContext<Docflow>> lookupDocflowAsync(String docflowId) {
		QueryContext<Docflow> cxt = createQueryContext(EN_DFW);
		return cxt
			.setDocflowId(docflowId)
			.applyAsync(api::lookupDocflow);
	}

	@Override
	public QueryContext<Docflow> lookupDocflow(QueryContext<?> parent) {
		QueryContext<Docflow> cxt = createQueryContext(parent, EN_DFW);
		return cxt.apply(api::lookupDocflow);
	}

	@Override
	public CompletableFuture<QueryContext<List<Document>>> getDocumentsAsync(String docflowId) {
		QueryContext<List<Document>> cxt = createQueryContext(EN_DFW);
		return cxt
			.setDocflowId(docflowId)
			.applyAsync(api::getDocuments);
	}

	@Override
	public QueryContext<List<Document>> getDocuments(QueryContext<?> parent) {
		QueryContext<List<Document>> cxt = createQueryContext(parent, EN_DFW);
		return cxt.apply(api::getDocuments);
	}
	
	@Override
	public CompletableFuture<QueryContext<Document>> lookupDocumentAsync(String docflowId, String documentId) {
		QueryContext<Document> cxt = createQueryContext(EN_DOC);
		return cxt
			.setDocflowId(docflowId)
			.setDocumentId(documentId)
			.applyAsync(api::lookupDocument);
	}

	@Override
	public QueryContext<Document> lookupDocument(QueryContext<?> parent) {
		QueryContext<Document> cxt = createQueryContext(parent, EN_DOC);
		return cxt.apply(api::lookupDocument);
	}

	@Override
	public CompletableFuture<QueryContext<DocumentDescription>> lookupDescriptionAsync(String docflowId, String documentId) {
		QueryContext<DocumentDescription> cxt = createQueryContext(EN_DOC);
		return cxt
			.setDocflowId(docflowId)
			.setDocumentId(documentId)
			.applyAsync(api::lookupDescription);
	}

	@Override
	public QueryContext<DocumentDescription> lookupDescription(QueryContext<?> parent) {
		QueryContext<DocumentDescription> cxt = createQueryContext(parent, EN_DOC);
		return cxt.apply(api::lookupDescription);
	}
	
	@Override
	public CompletableFuture<QueryContext<byte[]>> getEncryptedContentAsync(String docflowId, String documentId) {
		QueryContext<byte[]> cxt = createQueryContext(EN_DOC);
		return cxt
			.setDocflowId(docflowId)
			.setDocumentId(documentId)
			.applyAsync(api::getEncryptedContent);
	}

	@Override
	public QueryContext<byte[]> getEncryptedContent(QueryContext<?> parent) {
		QueryContext<byte[]> cxt = createQueryContext(parent, EN_DOC);
		return cxt.apply(api::getEncryptedContent);
	}

	@Override
	public CompletableFuture<QueryContext<byte[]>> getDecryptedContentAsync(String docflowId, String documentId) {
		QueryContext<byte[]> cxt = createQueryContext(EN_DOC);
		return cxt
			.setDocflowId(docflowId)
			.setDocumentId(documentId)
			.applyAsync(api::getDecryptedContent);
	}

	@Override
	public QueryContext<byte[]> getDecryptedContent(QueryContext<?> parent) {
		QueryContext<byte[]> cxt = createQueryContext(parent, EN_DOC);
		return cxt.apply(api::getDecryptedContent);
	}

	@Override
	public CompletableFuture<QueryContext<List<Signature>>> getSignaturesAsync(String docflowId, String documentId) {
		QueryContext<List<Signature>> cxt = createQueryContext(EN_DOC);
		return cxt
			.setDocflowId(docflowId)
			.setDocumentId(documentId)
			.applyAsync(api::getSignatures);
	}

	@Override
	public QueryContext<List<Signature>> getSignatures(QueryContext<?> parent) {
		QueryContext<List<Signature>> cxt = createQueryContext(parent, EN_DOC);
		return cxt.apply(api::getSignatures);
	}

	@Override
	public CompletableFuture<QueryContext<Signature>> getSignatureAsync(String docflowId, String documentId, String signatureId) {
		QueryContext<Signature> cxt = createQueryContext(EN_SGN);
		return cxt
			.setDocflowId(docflowId)
			.setDocumentId(documentId)
			.applyAsync(api::getSignature);
	}

	@Override
	public QueryContext<Signature> getSignature(QueryContext<?> parent) {
		QueryContext<Signature> cxt = createQueryContext(parent, EN_SGN);
		return cxt.apply(api::getSignature);
	}

	@Override
	public CompletableFuture<QueryContext<byte[]>> getSignatureContentAsync(String docflowId, String documentId, String signatureId) {
		QueryContext<byte[]> cxt = createQueryContext(EN_SGN);
		return cxt
			.setDocflowId(docflowId)
			.setDocumentId(documentId)
			.applyAsync(api::getSignatureContent);
	}

	@Override
	public QueryContext<byte[]> getSignatureContent(QueryContext<?> parent) {
		QueryContext<byte[]> cxt = createQueryContext(parent, EN_SGN);
		return cxt.apply(api::getSignatureContent);
	}

	@Override
	public CompletableFuture<QueryContext<DocumentToSend>> generateDocumentTypeReplyAsync(String docflowId, String documentType, String documentId, String x509Base64) {
		QueryContext<DocumentToSend> cxt = createQueryContext(EN_SGN);
		return cxt
			.setDocflowId(docflowId)
			.setDocumentType(documentType)
			.setDocumentId(documentId)
			.setContentString(x509Base64)
			.applyAsync(api::generateDocumentTypeReply);
	}

	@Override
	public QueryContext<DocumentToSend> generateDocumentTypeReply(QueryContext<?> parent) {
		QueryContext<DocumentToSend> cxt = createQueryContext(parent, EN_SGN);
		return cxt.apply(api::generateDocumentTypeReply);
	}

	@Override
	public CompletableFuture<QueryContext<Docflow>> addDocumentTypeReplyAsync(String docflowId, String documentType, String documentId, DocumentToSend documentToSend) {
		QueryContext<Docflow> cxt = createQueryContext(EN_DOC);
		return cxt
			.setDocflowId(docflowId)
			.setDocumentType(documentType)
			.setDocumentId(documentId)
			.setDocumentToSend(documentToSend)
			.applyAsync(api::addDocumentTypeReply);
	}

	@Override
	public QueryContext<Docflow> addDocumentTypeReply(QueryContext<?> parent) {
		QueryContext<Docflow> cxt = createQueryContext(parent, EN_DOC);
		return cxt.apply(api::addDocumentTypeReply);
	}
	
	@Override
	public CompletableFuture<QueryContext<List<DocumentToSend>>> generateRepliesAsync(Docflow docflow, String signerX509Base64) {
		QueryContext<List<DocumentToSend>> cxt = createQueryContext(EN_DFW);
		return cxt
			.setDocflow(docflow)
			.setCertificate(signerX509Base64)
			.applyAsync(api::generateReplies);
	}

	@Override
	public QueryContext<List<DocumentToSend>> generateReplies(QueryContext<?> parent) {
		QueryContext<List<DocumentToSend>> cxt = createQueryContext(parent, EN_DFW);
		return cxt.apply(api::generateReplies);
	}

	@Override
	public CompletableFuture<QueryContext<Docflow>> createReplyAsync(Docflow docflow) {
		QueryContext<Docflow> cxt = createQueryContext(EN_DFW);
		return cxt.applyAsync(api::sendReply);
	}
	
	@Override
	public QueryContext<Docflow> sendReply(QueryContext<?> parent) {
		QueryContext<Docflow> cxt = createQueryContext(parent, EN_DFW);
		return cxt.apply(api::sendReply);
	}

	@Override
	public CompletableFuture<QueryContext<DocflowPage>> getDocflows(
		boolean finished,
		boolean incoming,
		long skip,
		int take,
		String innKpp,
		DateTime updatedFrom,
		DateTime updatedTo,
		DateTime createdFrom,
		DateTime createdTo,
		String type
	)
	{
		QueryContext<DocflowPage> cxt = createQueryContext(EN_DFW);
		return cxt
			.setFinished(finished)
			.setIncoming(incoming)
			.setSkip(skip)
			.setTake(take)
			.setInnKpp(innKpp)
			.setUpdatedFrom(updatedFrom)
			.setUpdatedTo(updatedTo)
			.setCreatedFrom(createdFrom)
			.setCreatedTo(createdTo)
			.setType(type)
			.applyAsync(api::getDocflows);
	}
	
	@Override
	public QueryContext<DocflowPage> getDocflows(QueryContext<?> parent) {
		QueryContext<DocflowPage> cxt = createQueryContext(parent, EN_DFW);
		return cxt.apply(api::getDocflows);
	}

	@Override
	public CompletableFuture<QueryContext<String>> print(String docflowId, String documentId, String documentContentBase64) {
		QueryContext<String> cxt = createQueryContext(EN_DFW);
		return cxt
			.setDocflowId(docflowId)
			.setDocumentId(documentId)
			.setContentString(documentContentBase64)
			.applyAsync(api::print);
	}

	@Override
	public QueryContext<String> print(QueryContext<?> parent) {
		QueryContext<String> cxt = createQueryContext(parent, EN_DFW);
		return cxt.apply(api::print);
	}
}
