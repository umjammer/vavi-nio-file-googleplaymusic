/*
 * Copyright (c) 2019 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.nio.file.googleplaymusic;

import com.github.fge.filesystem.attributes.FileAttributesFactory;


/**
 * DuFileAttributesFactory.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2019/11/17 umjammer initial version <br>
 */
public final class GPMFileAttributesFactory extends FileAttributesFactory {

    public GPMFileAttributesFactory() {
        setMetadataClass(Object.class);
        addImplementation("basic", GPMBasicFileAttributesProvider.class);
    }
}
