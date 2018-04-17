package ru.skbkontur.sdk.extern.docflows;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.*;
import ru.skbkontur.sdk.extern.common.ResponseData;
import ru.skbkontur.sdk.extern.common.StandardObjects;
import ru.skbkontur.sdk.extern.common.StandardValues;
import ru.skbkontur.sdk.extern.common.TestServlet;
import ru.skbkontur.sdk.extern.model.Document;
import ru.skbkontur.sdk.extern.providers.ServiceError;
import ru.skbkontur.sdk.extern.service.transport.adaptors.DocflowsAdaptor;
import ru.skbkontur.sdk.extern.service.transport.adaptors.QueryContext;
import ru.skbkontur.sdk.extern.service.transport.invoker.ApiClient;

import java.util.UUID;

import static javax.servlet.http.HttpServletResponse.*;
import static junit.framework.TestCase.*;

public class DocflowsLookupDocumentTest {
    private static final String LOCALHOST_DOCFLOWS = "http://localhost:8080/docflows";
    private static Server server;

    private QueryContext<Document> queryContext;

    private final static String DOCUMENT_DESCRIPTION = "{\"type\": \"urn:nss:nid\"," +
            "\"filename\": \"string\"," +
            "\"content-type\": \"string\"}";

    @BeforeClass
    public static void startJetty() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(TestServlet.class, "/docflows/*");
        server = new Server(8080);
        server.setHandler(context);
        server.start();
    }

    @Before
    public void prepareQueryContext() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(LOCALHOST_DOCFLOWS);
        queryContext = new QueryContext<>();
        queryContext.setApiClient(apiClient);
        queryContext.setAccountProvider(UUID::randomUUID);
        queryContext.setDocflowId(UUID.randomUUID());
        queryContext.setDocumentId(UUID.randomUUID());
    }

    @AfterClass
    public static void stopJetty() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore // bug reported: KA-1213
    public void testLookupDocument_Empty() {
        ResponseData.INSTANCE.setResponseCode(SC_OK); // 200
        ResponseData.INSTANCE.setResponseMessage("{}");
        DocflowsAdaptor docflowsAdaptor = new DocflowsAdaptor();
        docflowsAdaptor.lookupDocument(queryContext);
        assertNotNull("Document must not be null!", queryContext.get());
    }

    @Test
    @Ignore // bug reported: KA-1213
    public void testLookupDocument_Document() {
        ResponseData.INSTANCE.setResponseCode(SC_OK); // 200
        ResponseData.INSTANCE.setResponseMessage(String.format("{\"id\": \"%s\"}", StandardValues.ID));
        DocflowsAdaptor docflowsAdaptor = new DocflowsAdaptor();
        docflowsAdaptor.lookupDocument(queryContext);
        DocflowsValidator.validateDocument(queryContext.get(), false, false, false, false);
    }

    @Test
    @Ignore // bug reported: KA-1213
    public void testLookupDocument_Document_Description() {
        ResponseData.INSTANCE.setResponseCode(SC_OK); // 200
        ResponseData.INSTANCE.setResponseMessage("{" +
                "\"id\": \"" + StandardValues.ID + "\"," +
                "\"description\": " + DOCUMENT_DESCRIPTION +
                "}");
        DocflowsAdaptor docflowsAdaptor = new DocflowsAdaptor();
        docflowsAdaptor.lookupDocument(queryContext);
        DocflowsValidator.validateDocument(queryContext.get(), true, false, false, false);
    }

    @Test
    @Ignore // bug reported: KA-1213
    public void testLookupDocument_Document_WithContent() {
        ResponseData.INSTANCE.setResponseCode(SC_OK); // 200
        ResponseData.INSTANCE.setResponseMessage("{" +
                "\"id\": \"" + StandardValues.ID + "\"," +
                "\"description\": " + DOCUMENT_DESCRIPTION + "," +
                "\"content\": {\n" +
                "  \"decrypted\": " + StandardObjects.LINK + "," +
                "  \"encrypted\": " + StandardObjects.LINK +
                "}" +
                "}");
        DocflowsAdaptor docflowsAdaptor = new DocflowsAdaptor();
        docflowsAdaptor.lookupDocument(queryContext);
        DocflowsValidator.validateDocument(queryContext.get(), true, true, false, false);
    }

    @Test
    @Ignore // bug reported: KA-1237
    public void testLookupDocument_Document_Signature() {
        ResponseData.INSTANCE.setResponseCode(SC_OK); // 200
        ResponseData.INSTANCE.setResponseMessage("{" +
                "\"id\": \"" + StandardValues.ID + "\"," +
                "\"description\": " + DOCUMENT_DESCRIPTION + "," +
                "\"content\": {\n" +
                "  \"decrypted\": " + StandardObjects.LINK + "," +
                "  \"encrypted\": " + StandardObjects.LINK +
                "}," +
                "\"signatures\": [{\"id\": \"" + StandardValues.ID + "\"}]" +
                "}");
        DocflowsAdaptor docflowsAdaptor = new DocflowsAdaptor();
        docflowsAdaptor.lookupDocument(queryContext);
        DocflowsValidator.validateDocument(queryContext.get(), true, true, true, false);
    }

    @Test
    @Ignore // bug reported: KA-1237
    public void testLookupDocument_Document_Links() {
        ResponseData.INSTANCE.setResponseCode(SC_OK); // 200
        ResponseData.INSTANCE.setResponseMessage("{" +
                "\"id\": \"" + StandardValues.ID + "\"," +
                "\"description\": " + DOCUMENT_DESCRIPTION + "," +
                "\"content\": {\n" +
                "  \"decrypted\": " + StandardObjects.LINK + "," +
                "  \"encrypted\": " + StandardObjects.LINK +
                "}," +
                "\"signatures\": [{\"id\": \"" + StandardValues.ID + "\"}]," +
                "\"links\": [" + StandardObjects.LINK + "]" +
                "}");
        DocflowsAdaptor docflowsAdaptor = new DocflowsAdaptor();
        docflowsAdaptor.lookupDocument(queryContext);
        DocflowsValidator.validateDocument(queryContext.get(), true, true, true, true);
    }

    @Test
    public void testGetDocflows_BAD_REQUEST() {
        ResponseData.INSTANCE.setResponseCode(SC_BAD_REQUEST); // 400
        checkResponseCode(SC_BAD_REQUEST);
    }

    @Test
    public void testGetDocflows_UNAUTHORIZED() {
        ResponseData.INSTANCE.setResponseCode(SC_UNAUTHORIZED); // 401
        checkResponseCode(SC_UNAUTHORIZED);
    }

    @Test
    public void testGetDocflows_FORBIDDEN() {
        ResponseData.INSTANCE.setResponseCode(SC_FORBIDDEN); // 403
        checkResponseCode(SC_FORBIDDEN);
    }

    @Test
    public void testGetDocflows_NOT_FOUND() {
        ResponseData.INSTANCE.setResponseCode(SC_NOT_FOUND); // 404
        checkResponseCode(SC_NOT_FOUND);
    }

    @Test
    public void testGetDocflows_INTERNAL_SERVER_ERROR() {
        ResponseData.INSTANCE.setResponseCode(SC_INTERNAL_SERVER_ERROR); // 500
        checkResponseCode(SC_INTERNAL_SERVER_ERROR);
    }

    private void checkResponseCode(int code) {
        DocflowsAdaptor docflowsAdaptor = new DocflowsAdaptor();
        docflowsAdaptor.lookupDocument(queryContext);
        Document document = queryContext.get();
        assertNull("document must be null!", document);
        ServiceError serviceError = queryContext.getServiceError();
        assertNotNull("ServiceError must not be null!", serviceError);
        assertEquals("Response code is wrong!", code, serviceError.getResponseCode());
    }
}
