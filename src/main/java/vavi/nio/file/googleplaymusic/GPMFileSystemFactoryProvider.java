/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.nio.file.googleplaymusic;

import com.github.fge.filesystem.provider.FileSystemFactoryProvider;


/**
 * GPMFileSystemFactoryProvider.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/02/15 umjammer initial version <br>
 */
public final class GPMFileSystemFactoryProvider extends FileSystemFactoryProvider {

    public GPMFileSystemFactoryProvider() {
        setAttributesFactory(new GPMFileAttributesFactory());
        setOptionsFactory(new GPMFileSystemOptionsFactory());
    }
}
