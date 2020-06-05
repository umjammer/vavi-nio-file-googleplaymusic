/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.nio.file.googleplaymusic;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import vavi.net.fuse.Fuse;


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

        FileSystem fs = FileSystems.newFileSystem(uri, Collections.EMPTY_MAP);

        Map<String, Object> options = new HashMap<>();
        options.put("fsname", "googleplaymusic_fs" + "@" + System.currentTimeMillis());
        options.put("noappledouble", null);
        options.put(vavi.net.fuse.javafs.JavaFSFuse.ENV_DEBUG, false);
        options.put(vavi.net.fuse.javafs.JavaFSFuse.ENV_READ_ONLY, false);

        Fuse.getFuse().mount(fs, args[0], options);
    }

    @Test
    void test() throws Exception {
        String email = System.getenv("TEST_ACCOUNT");
        String filter = System.getenv("TEST_FILTER");

        Map<String, Object> env = new HashMap<>();
        env.put(GPMFileSystemProvider.ENV_APP_CREDENTIAL, new GPMTestAppCredential());
        env.put(GPMFileSystemProvider.ENV_USER_CREDENTIAL, new GPMTestUserCredential(email));

        URI uri = URI.create("googleplaymusic:///");

        FileSystem fs = FileSystems.newFileSystem(uri, env);
        Files.newDirectoryStream(fs.getPath("/"), filter).forEach(System.err::println);
    }
}

/* */
