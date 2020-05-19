/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.nio.file.googleplaymusic;

import vavi.net.auth.AppCredential;
import vavi.net.auth.BaseLocalAppCredential;
import vavi.util.properties.annotation.Env;
import vavi.util.properties.annotation.PropsEntity;


/**
 * GPMTestAppCredential.
 * <p>
 * environment variable
 * <ul>
 * <li> TEST_ANDROID_ID
 * </ul>
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/02/15 umjammer initial version <br>
 */
@PropsEntity
public class GPMTestAppCredential extends BaseLocalAppCredential implements AppCredential {

    @Env(name = "TEST_ANDROID_ID")
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
