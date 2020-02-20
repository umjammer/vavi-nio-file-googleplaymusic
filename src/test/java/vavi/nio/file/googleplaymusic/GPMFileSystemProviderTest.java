/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.nio.file.googleplaymusic;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import vavi.net.auth.oauth2.Credential;
import vavi.net.auth.oauth2.googleplaymusic.GPMLocalAppCredential;
import vavi.util.properties.annotation.PropsEntity;

import static org.junit.jupiter.api.Assertions.fail;

import co.paralleluniverse.javafs.JavaFS;


/**
 * GPMFileSystemProviderTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/02/20 umjammer initial version <br>
 */
class GPMFileSystemProviderTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String email = args[1];

        URI uri = URI.create("googleplaymusic:///?id=" + email);

        Credential appCredential = new GPMLocalAppCredential();
        PropsEntity.Util.bind(appCredential);

        Map<String, Object> env = new HashMap<>();
        env.put(GPMFileSystemProvider.ENV_CREDENTIAL, appCredential);

        FileSystem fs = FileSystems.newFileSystem(uri, env);

        Map<String, String> options = new HashMap<>();
        options.put("fsname", "googleplaymusic_fs" + "@" + System.currentTimeMillis());
        options.put("noappledouble", null);

        JavaFS.mount(fs, Paths.get(args[0]), true, false, options);
    }

    @Test
    void test() {
        fail("Not yet implemented");
    }
}

/* */
