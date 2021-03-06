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

package ru.kontur.extern_api.sdk;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static ru.kontur.extern_api.sdk.provider.LoginAndPasswordProvider.form;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import ru.kontur.extern_api.sdk.provider.AuthenticationProvider;
import ru.kontur.extern_api.sdk.provider.LoginAndPasswordProvider;
import ru.kontur.extern_api.sdk.provider.auth.AuthenticationProviderBuilder;
import ru.kontur.extern_api.sdk.provider.auth.PasswordAuthenticationProvider;
import ru.kontur.extern_api.sdk.provider.auth.TrustedAuthenticationProvider;

final class ConfigurationUtils {

    static Optional<AuthenticationProvider> guessAuthProvider(Configuration configuration) {

        LoginAndPasswordProvider form = form(configuration.getLogin(), configuration.getPass());

        if (nonNull(form.getLogin()) && nonNull(form.getPass())) {
            return Optional.of(createPasswordAuthProvider(configuration));
        }

        if (isEnoughDataForTrustedAuth(configuration)) {
            return Optional.of(createTrustedAuth(configuration));
        }

        return Optional.empty();
    }

    private static TrustedAuthenticationProvider createTrustedAuth(Configuration configuration) {
        String authType = "trusted";

        requireParam(configuration::getAuthBaseUri, "auth base uri", authType);
        requireParam(configuration::getApiKey, "api key", authType);
        requireParam(configuration::getThumbprintRsa, "Thumbprint Rsa", authType);
        requireParam(configuration::getCredential, "credential (name, value)", authType);
        requireParam(configuration::getServiceUserId, "service user id", authType);
        requireParam(configuration::getJksPass, "jks pass", authType);
        requireParam(configuration::getRsaKeyPass, "rsa key pass", authType);

        return AuthenticationProviderBuilder
                .createFor(configuration.getAuthBaseUri(), Level.BODY)
                .trustedAuthentication(UUID.fromString(configuration.getServiceUserId()))
                .configureEncryption(
                        configuration.getJksPass(),
                        configuration.getRsaKeyPass(),
                        configuration.getThumbprintRsa()
                );

    }

    private static boolean isEnoughDataForTrustedAuth(Configuration configuration) {
        return Optional
                .ofNullable(configuration.getThumbprintRsa())
                .map(o -> configuration.getCredential())
                .map(o -> configuration.getJksPass())
                .map(o -> configuration.getRsaKeyPass())
                .isPresent();
    }

    private static PasswordAuthenticationProvider createPasswordAuthProvider(Configuration config) {
        String authType = "password";

        requireParam(config::getLogin, "login", authType);
        requireParam(config::getPass, "password", authType);
        requireParam(config::getAuthBaseUri, "auth base uri", authType);
        requireParam(config::getApiKey, "api key", authType);

        return AuthenticationProviderBuilder.createFor(config.getAuthBaseUri(), Level.BODY)
                .withApiKey(config.getApiKey())
                .passwordAuthentication(config.getLogin(), config.getPass());
    }

    private static void requireParam(Supplier<?> s, String paramName, String authType) {
        String format = String.format("`%s` required for %s authentication", paramName, authType);
        requireNonNull(s.get(), format);
    }

}
