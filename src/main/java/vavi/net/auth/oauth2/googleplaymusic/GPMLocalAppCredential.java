/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.net.auth.oauth2.googleplaymusic;

import vavi.net.auth.AppCredential;
import vavi.net.auth.BaseLocalAppCredential;
import vavi.util.properties.annotation.Property;
import vavi.util.properties.annotation.PropsEntity;


/**
 * GPMLocalAppCredential.
 * <p>
 * properties file "~/.vavifuse/googleplaymusic.properties"
 * <ul>
 * <li> googleplaymusic.androidId
 * </ul>
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/02/15 umjammer initial version <br>
 */
@PropsEntity(url = "file://${user.home}/.vavifuse/googleplaymusic.properties")
public class GPMLocalAppCredential extends BaseLocalAppCredential implements AppCredential {

    @Property(name = "googleplaymusic.androidId")
    private String androidId;

    @Override
    public String getApplicationName() {
        return "vavi-apps-fuse";
    }

    public String getScheme() {
        return "googleplaymusic";
    }

    public String getClientId() {
        return androidId;
    }
}

/* */
