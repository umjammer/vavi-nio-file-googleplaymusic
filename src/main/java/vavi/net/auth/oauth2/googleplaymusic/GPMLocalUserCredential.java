/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.net.auth.oauth2.googleplaymusic;

import vavi.net.auth.BaseLocalUserCredential;
import vavi.util.properties.annotation.Property;
import vavi.util.properties.annotation.PropsEntity;


/**
 * GPMLocalUserCredencial.
 *
 * properties file "~/vavifuse/credentials.properties"
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/05/02 umjammer initial version <br>
 */
@PropsEntity(url = "file://${HOME}/.vavifuse/credentials.properties")
public class GPMLocalUserCredential extends BaseLocalUserCredential {

    /** */
    @Property(name = "googleplaymusic.password.{0}")
    private transient String password;

    /**
     * @param email
     */
    public GPMLocalUserCredential(String email) {
        super(email);
//System.err.println("password for " + id + ": " + password);
    }

    /* */
    public String getPassword() {
        return password;
    }
}

/* */
