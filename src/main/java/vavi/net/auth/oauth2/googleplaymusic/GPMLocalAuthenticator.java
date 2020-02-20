/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.net.auth.oauth2.googleplaymusic;

import java.io.IOException;

import com.github.felixgail.gplaymusic.util.TokenProvider;

import vavi.net.auth.oauth2.Authenticator;
import vavi.util.properties.annotation.Property;
import vavi.util.properties.annotation.PropsEntity;

import svarzee.gps.gpsoauth.AuthToken;
import svarzee.gps.gpsoauth.Gpsoauth.TokenRequestFailed;


/**
 * GPMLocalAuthenticator.
 *
 * properties file "~/vavifuse/credentials.properties"
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/02/15 umjammer initial version <br>
 */
@PropsEntity(url = "file://${HOME}/.vavifuse/googleplaymusic.properties")
public class GPMLocalAuthenticator implements Authenticator<AuthToken> {

    @Property(name = "googleplaymusic.password.{0}")
    private transient String password;

    private String androidId;

    public GPMLocalAuthenticator(String androidId) {
        this.androidId = androidId;
    }

    /**
     * @return token.
     */
    @Override
    public AuthToken authorize(String email) throws IOException {

        PropsEntity.Util.bind(this, email);
//System.err.println("password for " + email + ": " + password);

        try {
            AuthToken authToken = TokenProvider.provideToken(email, password, androidId);
            return authToken;
        } catch (TokenRequestFailed e) {
            throw new IOException(e);
        }
    }
}

/* */
