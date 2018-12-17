/*
 * Copyright (c) 2018 SKB Kontur
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
 *
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.kontur.extern_api.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kontur.extern_api.sdk.adaptor.QueryContext;
import ru.kontur.extern_api.sdk.model.*;
import ru.kontur.extern_api.sdk.utils.CryptoUtils;
import ru.kontur.extern_api.sdk.utils.DocType;
import ru.kontur.extern_api.sdk.utils.EngineUtils;
import ru.kontur.extern_api.sdk.utils.TestSuite;
import ru.kontur.extern_api.sdk.utils.TestUtils;
import ru.kontur.extern_api.sdk.model.PrepareResult.Status;
import ru.kontur.extern_api.sdk.provider.crypt.mscapi.CryptoProviderMSCapi;
import ru.kontur.extern_api.sdk.service.DraftService;
import ru.kontur.extern_api.sdk.utils.Lazy;
import ru.kontur.extern_api.sdk.utils.UncheckedSupplier;
import ru.kontur.extern_api.sdk.utils.Zip;


class DraftServiceIT {

    private static ExternEngine engine;

    private static DraftService draftService;
    private static CryptoUtils cryptoUtils;
    private static EngineUtils engineUtils;

    static class TestPack {

        final TestData data;
        final CreateDraftMeta meta;

        final Lazy<QueryContext<UUID>> draft = Lazy.of(this::newDraft);
        final Lazy<QueryContext<UUID>> withDocument = Lazy.of(this::newDraftWithDoc);

        TestPack(TestData data) {
            this.data = data;
            this.meta = TestUtils.toCreateDraftMeta(data);
        }

        private QueryContext<UUID> newDraft() {
            return UncheckedSupplier.get(() -> engine.getDraftService()
                    .createAsync(meta)
                    .get()
                    .ensureSuccess()
                    .map(QueryContext.DRAFT_ID, Draft::getId)
            );
        }

        private QueryContext<UUID> newDraftWithDoc() {
            UUID id = newDraft().getOrThrow();
            return new QueryContext<>(QueryContext.DRAFT_ID, id)
                    .setDocumentId(addDocument(id));

        }

        private UUID addDocument(UUID draftId) {
            String path = data.getDocs()[0];
            DocType docType = DocType.getDocType(meta.getRecipient());
            DocumentContents dc = EngineUtils.with(engine)
                    .createDocumentContents(path, docType);

            DraftDocument document = UncheckedSupplier.get(() -> engine
                    .getDraftService()
                    .addDecryptedDocumentAsync(draftId, dc)
                    .get()
                    .getOrThrow());

            return document.getId();
        }
    }

    private final Lazy<List<TestPack>> tests = Lazy.of(() -> Arrays
            .stream(TestUtils.getTestData(cryptoUtils.loadX509(engine.getConfiguration().getThumbprint())))
            .map(TestPack::new)
            .collect(Collectors.toList())
    );

    @BeforeAll
    static void setUpClass() {
        engine = TestSuite.Load().engine;
        engine.setCryptoProvider(new CryptoProviderMSCapi());

        cryptoUtils = CryptoUtils.with(engine.getCryptoProvider());
        engineUtils = EngineUtils.with(engine);
    }

    @BeforeEach
    void setUp() {
        draftService = engine.getDraftService();
    }

    /**
     * Test of the createDraft method
     *
     *
     * POST /v1/{accountId}/drafts
     */
    @Test
    void testCreateDraft() {
        for (TestPack test : tests.get()) {
            Assert.assertNotNull(test.newDraft().get());
        }
    }

    /**
     * Test of the getDraftById method
     *
     * GET /v1/{accountId}/drafts/{draftId}
     */
    @Test
    void testGetDraft() throws Exception {
        for (TestPack testPack : tests.get()) {
            Draft found = draftService
                    .lookupAsync(testPack.draft.get().getOrThrow())
                    .get()
                    .ensureSuccess()
                    .get();

            QueryContext<UUID> draftCxt = testPack.draft.get();
            Assert.assertEquals(draftCxt.getDraftId(), found.getId());

            String inn = testPack.data.getClientInfo().getSender().getInn();

            Assert.assertEquals(inn, found.getMeta().getSender().getInn());
        }
    }

    /**
     * DELETE /v1/{accountId}/drafts/{draftId}
     *
     * Test of the deleteDraft method
     */
    @Test
    void testDeleteDraft() throws Exception {

        for (TestPack testPack : tests.get()) {
            QueryContext<UUID> cxt = testPack.newDraft();
            draftService.deleteAsync(cxt.getOrThrow()).get().ensureSuccess();

            QueryContext<Draft> lookup = draftService.lookupAsync(cxt.getOrThrow()).get();
            assertTrue(lookup.isFail());
            assertEquals(404, lookup.getServiceError().getResponseCode());
        }
    }

    /**
     * GET /v1/{accountId}/drafts/{draftId}/meta
     *
     * Test of the getDraftMeta method
     */
    @Test
    void testGetDraftMeta() throws Exception {
        for (TestPack testPack : tests.get()) {
            DraftMeta found = draftService
                    .lookupDraftMetaAsync(testPack.draft.get().getOrThrow())
                    .get()
                    .ensureSuccess()
                    .get();

            String inn = testPack.data.getClientInfo().getSender().getInn();
            Assert.assertEquals(inn, found.getSender().getInn());
        }
    }

    /**
     * Test of the updateDraftMeta method
     *
     * PUT /v1/{accountId}/drafts/{draftId}/meta
     */
    @Test
    void testUpdateDraftMeta() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.draft.get().getOrThrow();
            DraftMeta draftMeta = draftService
                    .lookupDraftMetaAsync(draftId)
                    .get()
                    .getOrThrow();

            Sender currentSender = draftMeta.getSender();
            Organization currentPayer = draftMeta.getPayer();
            String ip = "8.8.8.8";
            CreateDraftMeta newDraftMeta = new CreateDraftMeta(
                    new CreateSender(currentSender.getInn(),currentSender.getKpp(), currentSender.getCertificate(), ip),
                    draftMeta.getRecipient(),
                    new CreateOrganization(currentPayer.getInn(), currentPayer.getKpp()));

            DraftMeta updatedDraftMeta = draftService
                    .updateDraftMetaAsync(draftId, newDraftMeta)
                    .get()
                    .getOrThrow();

            assertEquals(ip, updatedDraftMeta.getSender().getIpaddress());
        }

    }

    /**
     * Test of the addDecryptedDocument method
     *
     * POST /v1/{accountId}/drafts/{draftId}/documents
     */
    @Test
    void testAddDecryptedDocument() {
        for (TestPack test : tests.get()) {
            UUID draftId = test.draft.get().getOrThrow();
            UUID documentId = test.addDocument(draftId);
        }

    }

    /**
     * Test of the deleteDocument method
     *
     * delete /v1/{accountId}/drafts/{draftId}/documents/{documentId}
     */
    @Test
    void testDeleteDocument() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.draft.get().getOrThrow();
            UUID documentId = test.addDocument(draftId);
            draftService.deleteDocumentAsync(draftId, documentId)
                    .get()
                    .getOrThrow();
        }

    }

    /**
     * Test of the getDocument method
     *
     * GET /v1/{accountId}/drafts/{draftId}/documents/{documentId}
     */
    @Test
    void testGetDocument() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.draft.get().getOrThrow();
            UUID documentId = test.addDocument(draftId);
            draftService.lookupDocumentAsync(draftId, documentId)
                    .get()
                    .getOrThrow();
        }

    }

    /**
     * Test of updateDocument method, of class ExternSDKDocument.
     *
     * PUT /v1/{accountId}/drafts/{draftId}/documents/{documentId}
     */
    @Test
    void testUpdateDocument() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.draft.get().getOrThrow();
            UUID documentId = test.addDocument(draftId);

            if (!(test.meta.getRecipient() instanceof FnsRecipient)) {
                continue;
            }

            String path = test.data.getDocs()[0];

            DraftDocument newDoc = draftService.updateDocumentAsync(
                    draftId,
                    documentId,
                    engineUtils.createDocumentContents(
                            TestUtils.getUpdatedDocumentPath(path),
                            DocType.FNS
                    )
            ).get().getOrThrow();
        }
    }

    /**
     * Test of the printDocument method
     *
     * GET /v1/{accountId}/drafts/{draftId}/documents/{documentId}/print
     */
    @Test
    void testPrintDocument() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.draft.get().getOrThrow();
            UUID documentId = test.addDocument(draftId);

            byte[] pdf = draftService
                    .getDocumentAsPdfAsync(draftId, documentId)
                    .get()
                    .getOrThrow();

            String pdfFileHeader = "%PDF";
            assertEquals(pdfFileHeader, new String(Arrays.copyOfRange(pdf, 0, 4)));
        }

    }

    /**
     * Test of getDecryptedDocumentContent method
     *
     * GET /v1/{accountId}/drafts/{draftId}/documents/{documentId}/content/decrypted
     */
    @Test
    void testGetDecryptedDocumentContent() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.draft.get().getOrThrow();
            UUID documentId = test.addDocument(draftId);

            byte[] decrypted = draftService
                    .getDecryptedDocumentContentAsync(draftId, documentId)
                    .get()
                    .getOrThrow();

        }

    }

    /**
     * Test of updateDecryptedDocumentContent method
     *
     * PUT /v1/{accountId}/drafts/{draftId}/documents/{documentId}/content/decrypted
     */
    @Test
    void testUpdateDecryptedDocumentContent() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.draft.get().getOrThrow();
            UUID documentId = test.addDocument(draftId);

            if (!(test.meta.getRecipient() instanceof FnsRecipient)) {
                continue;
            }

            String path = test.data.getDocs()[0];
            byte[] updatedContent = TestUtils.loadUpdateDocument(path);

            draftService
                    .updateDecryptedDocumentContentAsync(draftId, documentId, updatedContent)
                    .get()
                    .getOrThrow();

        }
    }

    /**
     * Test of getEncryptedDocumentContent method
     *
     * GET /v1/{accountId}/drafts/{draftId}/documents/{documentId}/content/encrypted
     */
    @Test
    void testGetEncryptedDocumentContent() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.newDraft().getOrThrow();
            UUID documentId = test.addDocument(draftId);

            draftService.prepareAsync(draftId).get().getOrThrow();

            byte[] content = draftService
                    .getEncryptedDocumentContentAsync(draftId, documentId)
                    .get()
                    .getOrThrow();
        }
    }

    /**
     * Test of getSignatureContent method
     *
     * GET /v1/{accountId}/drafts/{draftId}/documents/{documentId}/signature
     */
    @Test
    void testGetSignatureContent() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.draft.get().getOrThrow();
            UUID documentId = test.addDocument(draftId);

            // after prepare?

            byte[] content = draftService
                    .getSignatureContentAsync(draftId, documentId)
                    .get()
                    .getOrThrow();
        }

    }

    /**
     * Test of updateSignature method
     *
     * PUT /v1/{accountId}/drafts/{draftId}/documents/{documentId}/signature
     */
    @Test
    void testUpdateSignature() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.draft.get().getOrThrow();
            UUID documentId = test.addDocument(draftId);

            byte[] docContent = draftService
                    .getDecryptedDocumentContentAsync(draftId, documentId)
                    .get()
                    .getOrThrow();

            byte[] signature = cryptoUtils
                    .sign(engine.getConfiguration().getThumbprint(), Zip.unzip(docContent));

            draftService
                    .updateSignatureAsync(draftId, documentId, signature)
                    .get()
                    .getOrThrow();
        }

    }

    /**
     * Test of check method
     *
     * POST /v1/{accountId}/drafts/drafts/{draftId}/check
     */
    @Test
    void testCheck() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.withDocument.get().getOrThrow();

            CheckResultData checkResult = draftService.checkAsync(draftId)
                    .get()
                    .getOrThrow();

            Assertions.assertTrue(checkResult.hasNoErrors());
        }
    }

    /**
     * Test of prepare method
     *
     * POST /v1/{accountId}/drafts/drafts/{draftId}/prepare
     */
    @Test
    void testPrepare() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.withDocument.get().getOrThrow();

            PrepareResult prepareResult = draftService.prepareAsync(draftId)
                    .get()
                    .getOrThrow();

            Assertions.assertTrue(prepareResult.getStatus() == Status.OK ||
                    prepareResult.getStatus() == Status.CHECK_PROTOCOL_HAS_ONLY_WARNINGS);
        }
    }


    /**
     * Test of send method.
     *
     * POST /v1/{accountId}/drafts/drafts/{draftId}/send
     */
    @Test
    void testSend() throws Exception {
        for (TestPack test : tests.get()) {
            UUID draftId = test.newDraftWithDoc().getOrThrow();

            draftService.sendAsync(draftId)
                    .get()
                    .getOrThrow();
        }
    }

}
