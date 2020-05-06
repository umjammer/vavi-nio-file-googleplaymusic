/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.nio.file.googleplaymusic;

import vavi.net.auth.oauth2.BaseLocalUserCredential;
import vavi.util.properties.annotation.Env;
import vavi.util.properties.annotation.PropsEntity;


/**
 * GPMTestUserCredential.
 * <p>
 * environment variable
 * <ul>
 * <li> TEST_PASSWORD
 * </ul>
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/05/02 umjammer initial version <br>
 */
@PropsEntity
public class GPMTestUserCredential extends BaseLocalUserCredential {

    /** */
    @Env(name = "TEST_PASSWORD")
    private transient String password;

    /**
     * @param email
     */
    public GPMTestUserCredential(String email) {
        super(email);
//System.err.println("password for " + id + ": " + password);
    }

    /* */
    public String getPassword() {
        return password;
    }
}

/* */
