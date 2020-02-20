/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.net.auth.oauth2.googleplaymusic;

import vavi.net.auth.oauth2.Credential;
import vavi.util.properties.annotation.Property;
import vavi.util.properties.annotation.PropsEntity;


/**
 * GPMLocalAppCredential.
 *
 * properties file "~/.vavifuse/googleplaymusic.properties"
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/02/15 umjammer initial version <br>
 */
@PropsEntity(url = "file://${user.home}/.vavifuse/googleplaymusic.properties")
public class GPMLocalAppCredential implements Credential {

    @Property(name = "googleplaymusic.androidId")
    private String androidId;

    public String getScheme() {
        return "googleplaymusic";
    }

    public String getClientId() {
        return androidId;
    }
}

/* */
