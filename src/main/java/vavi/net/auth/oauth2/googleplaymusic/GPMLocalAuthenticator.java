/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.net.auth.oauth2.googleplaymusic;

import java.io.IOException;

import com.github.felixgail.gplaymusic.util.TokenProvider;

import vavi.net.auth.Authenticator;
import vavi.net.auth.UserCredential;

import svarzee.gps.gpsoauth.AuthToken;
import svarzee.gps.gpsoauth.Gpsoauth.TokenRequestFailed;


/**
 * GPMLocalAuthenticator.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/02/15 umjammer initial version <br>
 */
public class GPMLocalAuthenticator implements Authenticator<UserCredential, AuthToken> {

    /** */
    private String androidId;

    /** */
    public GPMLocalAuthenticator(String androidId) {
        this.androidId = androidId;
    }

    /**
     * @return token.
     */
    @Override
    public AuthToken authorize(UserCredential credential) throws IOException {

        try {
            AuthToken authToken = TokenProvider.provideToken(credential.getId(), credential.getPassword(), androidId);
            return authToken;
        } catch (TokenRequestFailed e) {
            throw new IOException(e);
        }
    }
}

/* */
