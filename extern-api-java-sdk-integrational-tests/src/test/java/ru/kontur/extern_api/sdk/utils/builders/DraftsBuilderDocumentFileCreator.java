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

package ru.kontur.extern_api.sdk.utils.builders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import ru.kontur.extern_api.sdk.ExternEngine;
import ru.kontur.extern_api.sdk.model.builders.submission.SubmissionDraftsBuilder;
import ru.kontur.extern_api.sdk.model.builders.submission.SubmissionDraftsBuilderDocument;
import ru.kontur.extern_api.sdk.model.builders.submission.SubmissionDraftsBuilderDocumentFile;
import ru.kontur.extern_api.sdk.model.builders.submission.SubmissionDraftsBuilderDocumentFileContents;
import ru.kontur.extern_api.sdk.model.builders.submission.SubmissionDraftsBuilderDocumentFileMetaRequest;
import ru.kontur.extern_api.sdk.utils.CryptoUtils;

public class DraftsBuilderDocumentFileCreator {

    public SubmissionDraftsBuilderDocumentFile createSubmissionDraftsBuilderDocumentFile(
            ExternEngine engine,
            CryptoUtils cryptoUtils,
            SubmissionDraftsBuilder draftsBuilder,
            SubmissionDraftsBuilderDocument draftsBuilderDocument
    ) {
        final String fileName = "AnyFileName.pdf";

        SubmissionDraftsBuilderDocumentFileContents contents = new SubmissionDraftsBuilderDocumentFileContents();
        SubmissionDraftsBuilderDocumentFileMetaRequest meta = new SubmissionDraftsBuilderDocumentFileMetaRequest();

        String scannedContent = getScannedContent();

        byte[] signature = cryptoUtils.sign(
                engine.getConfiguration().getThumbprint(),
                Base64.getDecoder().decode(scannedContent)
        );

        contents.setBase64Content(scannedContent);
        contents.setBase64SignatureContent(Base64.getEncoder().encodeToString(signature));

        meta.setFileName(fileName);
        contents.setMeta(meta);

        return engine
                .getDraftsBuilderService()
                .submission()
                .getDocumentService(draftsBuilder.getId())
                .getFileService(draftsBuilderDocument.getId())
                .createAsync(contents)
                .join();
    }

    public String getScannedContent() {
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(
                    Paths.get(new File(Objects.requireNonNull(getClass()
                                                                      .getClassLoader()
                                                                      .getResource("docs/Scanned.pdf"))
                                               .getFile())
                                      .getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(bytes);
    }
}
