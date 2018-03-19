/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.skbkontur.sdk.extern.service.transport.adaptors;

import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.HttpsURLConnection;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import ru.skbkontur.sdk.extern.model.Account;
import ru.skbkontur.sdk.extern.model.AccountList;
import ru.skbkontur.sdk.extern.model.CreateAccountRequest;
import ru.skbkontur.sdk.extern.model.Docflow;
import ru.skbkontur.sdk.extern.model.Document;
import ru.skbkontur.sdk.extern.model.DocumentContents;
import ru.skbkontur.sdk.extern.model.DocumentDescription;
import ru.skbkontur.sdk.extern.model.DocumentToSend;
import ru.skbkontur.sdk.extern.model.DraftDocument;
import ru.skbkontur.sdk.extern.providers.AccountProvider;
import ru.skbkontur.sdk.extern.providers.ApiKeyProvider;
import ru.skbkontur.sdk.extern.providers.AuthenticationProvider;
import ru.skbkontur.sdk.extern.providers.ServiceError;
import ru.skbkontur.sdk.extern.service.transport.invoker.ApiClient;
import ru.skbkontur.sdk.extern.service.transport.invoker.DateAdapter;
import ru.skbkontur.sdk.extern.service.transport.invoker.DateTimeTypeAdapter;
import ru.skbkontur.sdk.extern.service.transport.invoker.LocalDateTypeAdapter;
import ru.skbkontur.sdk.extern.service.transport.swagger.invoker.auth.ApiKeyAuth;
import ru.skbkontur.sdk.extern.service.transport.swagger.invoker.auth.Authentication;
import ru.skbkontur.sdk.extern.model.DraftMeta;
import ru.skbkontur.sdk.extern.model.Link;
import ru.skbkontur.sdk.extern.model.Signature;
import ru.skbkontur.sdk.extern.providers.ServiceError.ErrorCode;
import ru.skbkontur.sdk.extern.service.transport.invoker.ApiException;
import ru.skbkontur.sdk.extern.service.transport.invoker.DocumentToSendAdapter;
import ru.skbkontur.sdk.extern.service.transport.invoker.SignatureToSendAdapter;

/**
 *
 * @author AlexS
 *
 * Api Query Context
 * @param <R> some return type
 */
public class QueryContext<R> implements Serializable {

	private final Map<String, Object> params;

	public static final String SESSION_ID = "sessionId";
	public static final String ENTITY_NAME = "entityName";
	public static final String ENTITY_ID = "entityId";
	public static final String DRAFT_ID = "draftId";
	public static final String DRAFT = "draft";
	public static final String DOCUMENT_ID = "documentId";
	public static final String DOCUMENT = "document";
	public static final String DOCUMENTS = "documents";
	public static final String DOCFLOW_ID = "docflowId";
	public static final String DOCUMENT_TYPE = "documentType";
	public static final String REPLY_ID = "replyId";
	public static final String DOCUMENT_TO_SEND = "documentToSend";
	public static final String DOCUMENT_TO_SENDS = "documentToSends";
	public static final String DRAFT_META = "draftMeta";
	public static final String DEFFERED = "deffered";
	public static final String FORCE = "force";
	public static final String CONTENT_BYTES = "contentBytes";
	public static final String CONTENT_STRING = "contentString";
	public static final String FILE_NAME = "fileName";
	public static final String DOCUMENT_CONTENTS = "documentContents";
	public static final String CONTENT = "content";
	public static final String MAP = "map";
	public static final String PREPARE_RESULT = "prepareResult";
	public static final String DOCFLOW = "docflow";
	public static final String DOCFLOWS = "docflows";
	public static final String DRAFT_DOCUMENT = "draftDocument";
	public static final String DOCUMENT_DESCRIPTION = "documentDescription";
	public static final String SIGNATURE_ID = "signatureId";
	public static final String SIGNATURES = "signatures";
	public static final String SIGNATURE = "signature";
	public static final String THUMBPRINT = "thumbprint";
	public static final String LINKS = "links";
	public static final String ACCOUNT_ID = "accountId";
	public static final String ACCOUNT = "account";
	public static final String ACCOUNT_LIST = "accountList";
	public static final String CREATE_ACCOUNT_REQUEST = "createAccountRequest";
	public static final String NOTHING = "nothing";
	public static final String OBJECT = "object";

	private String result;

	private ServiceError serviceError;

	private ApiClient apiClient;

	private AuthenticationProvider authenticationProvider;

	private AccountProvider accountProvider;
	
	private ApiKeyProvider apiKeyProvider;
	
	public QueryContext() {
		this.params = new ConcurrentHashMap<>();
		this.serviceError = null;
		this.result = null;
	}

	public QueryContext(String entityName) {
		this();
		this.setEntityName(entityName);
	}
	
	public QueryContext(QueryContext<?> parent, String entityName) {
		this.params = new ConcurrentHashMap<>();
		this.params.putAll(parent.params);
		this.serviceError = parent.getServiceError();
		this.result = null;
		this.setEntityName(entityName);
	}
	
	public void setApiClient(ApiClient apiClient) {
		this.apiClient = apiClient;
	}
	
	public QueryContext<R> setResult(R result, String key) {
		this.result = key;
		this.serviceError = null;
		return key.equals(NOTHING) ? this : set(key, result);
	}

	public ServiceError getServiceError() {
		return serviceError;
	}

	public QueryContext<R> setServiceError(QueryContext<?> queryContext) {
		this.result = null;
		this.serviceError = queryContext.serviceError;
		if (serviceError.getResponseCode() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
			set(SESSION_ID, null);
		}
		return this;
	}

	public QueryContext<R> setServiceError(ServiceError serviceError) {
		this.result = null;
		this.serviceError = serviceError;
		if (serviceError.getResponseCode() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
			set(SESSION_ID, null);
		}
		return this;
	}
	
	public QueryContext<R> setServiceError(ApiException x) {
		return setServiceError(ErrorCode.server, x.getMessage(), x.getCode(), x.getResponseHeaders(), x.getResponseBody());
	}

	public QueryContext<R> setServiceError(ServiceError.ErrorCode errorCode, String message, int code, Map<String, List<String>> responseHeaders, String responseBody) {
		return setServiceError(new ServiceErrorImpl(errorCode, message, code, responseHeaders, responseBody));
	}
	
	public QueryContext<R> setServiceError(String message) {
		return setServiceError(new ServiceErrorImpl(ServiceError.ErrorCode.business, message, 0, null, null));
	}
	
	public R get() {
		if(result==null) {
			return null;
		}
		return (R)get(result);
	}

	public boolean isSuccess() {
		return serviceError == null;
	}

	public boolean isFail() {
		return serviceError != null;
	}

	public QueryContext<R> setServiceBaseUri(String serviceBaseUri) {
		this.apiClient.setBasePath(serviceBaseUri);
		return this;
	}

	public QueryContext<R> setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
		return this;
	}

	public AuthenticationProvider getAuthenticationProvider() {
		return authenticationProvider;
	}

	public AccountProvider getAccountProvider() {
		return accountProvider;
	}

	public QueryContext<R> setAccountProvider(AccountProvider accountProvider) {
		this.accountProvider = accountProvider;
		return this;
	}

	public ApiKeyProvider getApiKeyProvider() {
		return apiKeyProvider;
	}

	public QueryContext<R> setApiKeyProvider(ApiKeyProvider apiKeyProvider) {
		this.apiKeyProvider = apiKeyProvider;
		return this;
	}

	public String getSessionId() {
		return (String) params.get(SESSION_ID);
	}

	public QueryContext<R> setSessionId(QueryContext<?> queryContext) {
		return set(SESSION_ID, UUID.fromString(queryContext.getSessionId()));
	}

	public QueryContext<R> setSessionId(String sessionId) {
		return set(SESSION_ID, sessionId);
	}

	public String getEntityName() {
		return (String) params.get(ENTITY_NAME);
	}

	public final QueryContext<R> setEntityName(String entityName) {
		return set(ENTITY_NAME, entityName);
	}

	public UUID getDraftId() {
		return (UUID) params.get(DRAFT_ID);
	}

	public QueryContext<R> setDraftId(String draftId) {
		return set(DRAFT_ID, UUID.fromString(draftId));
	}

	public Docflow getDocflow() {
		return (Docflow) params.get(DOCFLOW);
	}

	public QueryContext<R> setDocflow(Docflow docflow) {
		return set(DOCFLOW, docflow);
	}

	public List<Docflow> getDocflows() {
		return (List<Docflow>) params.get(DOCFLOWS);
	}

	public QueryContext<R> setDocflow(List<Docflow> docflows) {
		return set(DOCFLOWS, docflows);
	}

	public UUID getDocflowId() {
		UUID docflowId = (UUID) params.get(DOCFLOW_ID);
		if (docflowId == null) {
			Docflow docflow = this.getDocflow();
			if (docflow != null)
				docflowId = docflow.getId();
		}
		return docflowId;
	}

	public QueryContext<R> setDocflowId(String docflowId) {
		return set(DOCFLOW_ID, UUID.fromString(docflowId));
	}
	
	public QueryContext<R> setDocflowId(UUID docflowId) {
		return set(DOCFLOW_ID, docflowId);
	}
	
	public UUID getDocumentId() {
		return (UUID) params.get(DOCUMENT_ID);
	}

	public QueryContext<R> setDocumentId(String documentId) {
		return set(DOCUMENT_ID, UUID.fromString(documentId));
	}

	public QueryContext<R> setDocumentId(UUID documentId) {
		return set(DOCUMENT_ID, documentId);
	}

	public Document getDocument() {
		return (Document) params.get(DOCUMENT);
	}

	public QueryContext<R> setDocument(Document document) {
		return set(DOCUMENT, document);
	}
	
	public List<Document> getDocuments() {
		return (List<Document>) params.get(DOCUMENTS);
	}

	public QueryContext<R> setDocument(List<Document> documents) {
		return set(DOCUMENTS, documents);
	}
	
	public DocumentDescription getDocumentDescription() {
		return (DocumentDescription) params.get(DOCUMENT_DESCRIPTION);
	}

	public QueryContext<R> setDocumentDescription(DocumentDescription documentDescription) {
		return set(DOCUMENT_DESCRIPTION, documentDescription);
	}
	
	public String getDocumentType() {
		return (String) params.get(DOCUMENT_TYPE);
	}

	public QueryContext<R> setDocumentType(String documentType) {
		return set(DOCUMENT_TYPE, documentType);
	}

	public UUID getReplyId() {
		return (UUID) params.get(REPLY_ID);
	}

	public QueryContext<R> setReplyId(String replyId) {
		return set(REPLY_ID, UUID.fromString(replyId));
	}

	public DocumentToSend getDocumentToSend() {
		return (DocumentToSend) params.get(DOCUMENT_TO_SEND);
	}

	public QueryContext<R> setDocumentToSend(DocumentToSend documentToSend) {
		return set(DOCUMENT_TO_SEND, documentToSend);
	}

	public DraftMeta getDraftMeta() {
		return (DraftMeta) params.get(DRAFT_META);
	}

	public QueryContext<R> setDraftMeta(DraftMeta draftMeta) {
		return set(DRAFT_META, draftMeta);
	}

	public boolean getDeffered() {
		Object v = params.get(DEFFERED);
		return v == null ? true : (boolean)v;
	}

	public QueryContext<R> setDeffered(boolean deffered) {
		return set(DEFFERED, deffered);
	}

	public boolean getForce() {
		Object v = params.get(FORCE);
		return v == null ? true : (boolean) v;
	}

	public QueryContext<R> setForce(boolean force) {
		return set(FORCE, force);
	}

	public byte[] getContentBytes() {
		return (byte[]) params.get(CONTENT_BYTES);
	}

	public String getContentString() {
		return (String) params.get(CONTENT_STRING);
	}

	public QueryContext<R> setContentBytes(byte[] documentContent) {
		return set(CONTENT_BYTES, documentContent);
	}

	public QueryContext<R> setContentString(String documentContent) {
		return set(CONTENT_STRING, documentContent);
	}

	public String getFileName() {
		return (String) params.get(FILE_NAME);
	}

	public QueryContext<R> setFileName(String fileName) {
		return set(FILE_NAME, fileName);
	}

	public DocumentContents getDocumentContents() {
		return (DocumentContents) params.get(DOCUMENT_CONTENTS);
	}

	public QueryContext<R> setDocumentContents(DocumentContents documentContents) {
		return set(DOCUMENT_CONTENTS, documentContents);
	}

	public byte[] getContent() {
		return (byte[]) params.get(CONTENT);
	}

	public DraftDocument getDraftDocument() {
		return (DraftDocument) params.get(DRAFT_DOCUMENT);
	}

	public QueryContext<R> setContent(byte[] content) {
		return set(CONTENT, content);
	}

	public UUID getSignatureId() {
		return (UUID) params.get(SIGNATURE_ID);
	}
	
	public QueryContext<R> setSignatureId(String signatureId) {
		return set(SIGNATURE_ID, UUID.fromString(signatureId));
	}
	
	public QueryContext<R> setSignatureId(UUID signatureId) {
		return set(SIGNATURE_ID, signatureId);
	}
	
	public String getThumbprint() {
		return (String) params.get(THUMBPRINT);
	}
	
	public QueryContext<R> setThumbprint(String thumbprint) {
		return set(THUMBPRINT, thumbprint);
	}
	
	public List<Link> getLinks() {
		return (List<Link>) params.get(LINKS);
	}
	
	public QueryContext<R> setLinks(List<Link> links) {
		return set(LINKS, links);
	}
	
	public UUID getAccountId() {
		return (UUID) params.get(ACCOUNT_ID);
	}
	
	public QueryContext<R> setAccountId(UUID accountId) {
		return set(ACCOUNT_ID, accountId);
	}
	
	public QueryContext<R> setAccountId(String accountId) {
		return set(ACCOUNT_ID, UUID.fromString(accountId));
	}
	
	public Account getAccount() {
		return (Account) params.get(ACCOUNT);
	}
	
	public QueryContext<R> setAccount(Account account) {
		return set(ACCOUNT, account);
	}
	
	public AccountList getAccountList() {
		return (AccountList) params.get(ACCOUNT_LIST);
	}
	
	public QueryContext<R> setAccountList(AccountList accountList) {
		return set(ACCOUNT_LIST, accountList);
	}
	
	public CreateAccountRequest getCreateAccountRequest() {
		return (CreateAccountRequest) params.get(CREATE_ACCOUNT_REQUEST);
	}
	
	public QueryContext<R> setCreateAccountRequest(CreateAccountRequest createAccountRequest) {
		return set(CREATE_ACCOUNT_REQUEST, createAccountRequest);
	}
	
	public Signature getSignature() {
		return (Signature) params.get(SIGNATURE);
	}
	
	public QueryContext<R> setSignature(Signature signature) {
		return set(SIGNATURE, signature);
	}
	
	public List<Signature> getSignatures() {
		return (List<Signature>) params.get(SIGNATURES);
	}
	
	public QueryContext<R> setSignatures(List<Signature> signatures) {
		return set(SIGNATURES, signatures);
	}
	
	public List<DocumentToSend> getDocumentToSends() {
		return (List<DocumentToSend>) params.get(DOCUMENT_TO_SENDS);
	}
	
	public QueryContext<R> setDocumentToSends(List<DocumentToSend> replies) {
		return set(DOCUMENT_TO_SENDS, replies);
	}
	
	public QueryContext<R> set(String name, Object val) {
		if (val != null) params.put(name, val);	else params.remove(name);
		
		return this;
	}

	public <T> T get(String name) {
		return (T)params.get(name);
	}

	public ApiClient getApiClient() {
		return apiClient;
	}

	private void acceptApiKey(String apiKey) {
		if (apiKey != null && !apiKey.isEmpty()) {
			Authentication apiKeyAuth = apiClient.getAuthentication("apiKey");
			if (apiKeyAuth != null) {
				((ApiKeyAuth) apiKeyAuth).setApiKey(apiKey);
			}
		}
	}

	public void configureApiClient() {
		apiClient.getJSON().setGson(new GsonBuilder()
			.disableHtmlEscaping()
			.registerTypeAdapter(Date.class, new DateAdapter(apiClient))
			.registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter())
			.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
			.registerTypeAdapter(ru.skbkontur.sdk.extern.service.transport.swagger.model.SignatureToSend.class, new SignatureToSendAdapter())
			.registerTypeAdapter(ru.skbkontur.sdk.extern.service.transport.swagger.model.DocumentToSend.class, new DocumentToSendAdapter())
			.create());
		// устанавливаем api-key
		acceptApiKey(apiKeyProvider.getApiKey());
	}

	public CompletableFuture<QueryContext<R>> applyAsync(Query<R> query) {
		if (isFail()) return CompletableFuture.completedFuture(this);
		
		acquireSessionId();
		
		if (isSuccess()) {
			return CompletableFuture.supplyAsync(()->query.apply(this));
		}
		else
			return CompletableFuture.completedFuture(this);
	}

	public QueryContext<R> apply(Query<R> query) {
		if (isFail()) return this;
		
		acquireSessionId();
		
		if (isSuccess()) {
			QueryContext<R> r = query.apply(this);
			if (r.isFail() && r.getServiceError().getErrorCode() == ErrorCode.server && r.getServiceError().getResponseCode() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
				if (authenticationProvider != null)
					authenticationProvider.raiseUnauthenticated(r.getServiceError());
			}
			return r;
		}
		else
			return this;
	}
	
	public ServiceException failure() {
		throw new ServiceException(getServiceError());
	}
	
	private void acquireSessionId() {
		String sessionId = getSessionId();
		if (sessionId != null && !sessionId.isEmpty()) {
			acceptAccessToken(sessionId);
		}
		else {
			if (authenticationProvider != null) {
				QueryContext<String> authQuery = authenticationProvider.sessionId();
				if (authQuery.isFail()) {
					setServiceError(authQuery);
				}
				else {
					sessionId = setSessionId(authQuery.get()).getSessionId();
					acceptAccessToken(sessionId);
				}
			}
			else {
				setServiceError(new ServiceErrorImpl(ServiceError.ErrorCode.unknownAuth));
			}
		}
	}

	private void acceptAccessToken(String sessionId) {
		if (sessionId != null && !sessionId.isEmpty()) {
			Authentication apiKeyAuth = apiClient.getAuthentication("auth.sid");
			if (apiKeyAuth != null) {
				((ApiKeyAuth) apiKeyAuth).setApiKey(sessionId);
				((ApiKeyAuth) apiKeyAuth).setApiKeyPrefix(getAuthenticationProvider().authPrefix());
			}
		}
	}
}