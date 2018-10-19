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

package ru.kontur.extern_api.sdk.provider.auth;

import com.google.gson.Gson;
import java.security.cert.CertificateException;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.jetbrains.annotations.NotNull;
import ru.argosgrp.cryptoservice.CryptoException;
import ru.kontur.extern_api.sdk.GsonProvider;
import ru.kontur.extern_api.sdk.crypt.CryptoApi;
import ru.kontur.extern_api.sdk.httpclient.KonturConfiguredClient;
import ru.kontur.extern_api.sdk.model.Credential;
import ru.kontur.extern_api.sdk.portal.AuthApi;
import ru.kontur.extern_api.sdk.provider.CryptoProvider;
import ru.kontur.extern_api.sdk.provider.LoginAndPasswordProvider;
import ru.kontur.extern_api.sdk.provider.crypt.mscapi.CryptoProviderMSCapi;

public class AuthenticationProviderBuilder {

    private final KonturConfiguredClient configuredClient;
    private String apiKey;

    public static AuthenticationProviderBuilder createFor(
            @NotNull String authServiceBaseUri,
            @NotNull Level logLevel
    ) {
        return new AuthenticationProviderBuilder(authServiceBaseUri, logLevel);
    }

    private AuthenticationProviderBuilder(String authServiceBaseUri, Level logLevel) {
        Gson gson = GsonProvider.getPortalCompatibleGson();
        this.configuredClient = new KonturConfiguredClient(logLevel, authServiceBaseUri, gson);
    }


    public AuthenticationProviderBuilder withApiKey(@NotNull String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public PasswordAuthenticationProvider passwordAuthentication(
            @NotNull String login,
            @NotNull String pass) {
        return new PasswordAuthenticationProvider(
                configuredClient.createService(AuthApi.class),
                LoginAndPasswordProvider.form(login, pass),
                apiKey
        );
    }

    public CertificateAuthenticationProvider certificateAuthentication(
            @NotNull CryptoProvider cryptoProvider,
            @NotNull byte[] certContent)
            throws CryptoException, CertificateException {

        CryptoApi cryptoApi = new CryptoApi();

        return new CertificateAuthenticationProvider(
                configuredClient.createService(AuthApi.class),
                certContent,
                cryptoApi.getThumbprint(certContent),
                apiKey,
                cryptoProvider
        );
    }

    public CertificateAuthenticationProvider certificateAuthentication(@NotNull byte[] certContent)
            throws CryptoException, CertificateException {
        return certificateAuthentication(new CryptoProviderMSCapi(), certContent);
    }

    public TrustedAuthentication trustedAuthentication(@NotNull String serviceUserId) {
        return trustedAuthentication(serviceUserId, new Credential("serviceUserId", serviceUserId));
    }

    public TrustedAuthentication trustedAuthentication(
            @NotNull String serviceUserId,
            @NotNull Credential credential) {
        return null;
    }


}