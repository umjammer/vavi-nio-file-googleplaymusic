/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.nio.file.googleplaymusic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.github.felixgail.gplaymusic.api.GPlayMusic;
import com.github.felixgail.gplaymusic.model.Track;
import com.github.felixgail.gplaymusic.model.enums.StreamQuality;
import com.github.fge.filesystem.driver.UnixLikeFileSystemDriverBase;
import com.github.fge.filesystem.exceptions.IsDirectoryException;
import com.github.fge.filesystem.provider.FileSystemFactoryProvider;

import vavi.nio.file.Cache;
import vavi.nio.file.Util;
import vavi.util.Debug;

import static vavi.nio.file.Util.toFilenameString;


/**
 * Google Play Music FileSystemDriver.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/02/15 umjammer initial version <br>
 */
@ParametersAreNonnullByDefault
public final class GPMFileSystemDriver extends UnixLikeFileSystemDriverBase {

    private boolean ignoreAppleDouble = false;

    private final Map<String, Track> tracks = new HashMap<>();

    public GPMFileSystemDriver(final FileStore fileStore,
            final FileSystemFactoryProvider provider,
            final GPlayMusic api,
            final Map<String, ?> env) throws IOException {
        super(fileStore, provider);
Debug.println("here0");
        for(Track track : api.getTrackApi().getLibraryTracks()) {
            this.tracks.put(track.getID(), track);
        }
Debug.println("here1");
    }

    /** */
    private boolean isDirectory(Object file) {
        return String.class.isInstance(file);
    }

    /** */
    private boolean isFile(Object file) {
        return Track.class.isInstance(file);
    }

    /** */
    private Cache<Object> cache = new Cache<Object>() {
        /**
         * TODO when the parent is not cached
         * 
         * @see #ignoreAppleDouble
         * @throws NoSuchFileException must be thrown when the path is not found
         *             in this cache
         */
        public Object getEntry(Path path) throws IOException {
            if (cache.containsFile(path)) {
                return cache.getFile(path);
            } else {
                if (ignoreAppleDouble && path.getFileName() != null && Util.isAppleDouble(path)) {
                    throw new NoSuchFileException("ignore apple double file: " + path);
                }

                String filenameString = toFilenameString(path);
Debug.println("path: " + filenameString);
                Track entry = tracks.get(filenameString.substring(0, filenameString.indexOf(" - ")));
                cache.putFile(path, entry);
                return entry;
            }
        }
    };

    @Nonnull
    @Override
    public InputStream newInputStream(final Path path, final Set<? extends OpenOption> options) throws IOException {
        final Object entry = cache.getEntry(path);

        // TODO: metadata driver
        if (isDirectory(entry)) {
            throw new IsDirectoryException("path: " + path);
        }

        return Track.class.cast(entry).getStreamURL(StreamQuality.HIGH).openStream();
    }

    @Nonnull
    @Override
    public OutputStream newOutputStream(final Path path, final Set<? extends OpenOption> options) throws IOException {
        throw new UnsupportedOperationException("newOutputStream is not supported by the file system");
    }

    @Nonnull
    @Override
    public DirectoryStream<Path> newDirectoryStream(final Path dir,
                                                    final DirectoryStream.Filter<? super Path> filter) throws IOException {
        return Util.newDirectoryStream(getDirectoryEntries(dir));
    }

    @Override
    public SeekableByteChannel newByteChannel(Path path,
                                              Set<? extends OpenOption> options,
                                              FileAttribute<?>... attrs) throws IOException {
        throw new UnsupportedOperationException("newByteChannel is not supported by the file system");
    }

    @Override
    public void createDirectory(final Path dir, final FileAttribute<?>... attrs) throws IOException {
        throw new UnsupportedOperationException("createDirectory is not supported by the file system");
    }

    @Override
    public void delete(final Path path) throws IOException {
        throw new UnsupportedOperationException("delete is not supported by the file system");
    }

    @Override
    public void copy(final Path source, final Path target, final Set<CopyOption> options) throws IOException {
        throw new UnsupportedOperationException("copy is not supported by the file system");
    }

    @Override
    public void move(final Path source, final Path target, final Set<CopyOption> options) throws IOException {
        throw new UnsupportedOperationException("move is not supported by the file system");
    }

    /**
     * Check access modes for a path on this filesystem
     * <p>
     * If no modes are provided to check for, this simply checks for the
     * existence of the path.
     * </p>
     *
     * @param path the path to check
     * @param modes the modes to check for, if any
     * @throws IOException filesystem level error, or a plain I/O error if you
     *             use this with javafs (jnr-fuse), you should throw
     *             {@link NoSuchFileException} when the file not found.
     * @see FileSystemProvider#checkAccess(Path, AccessMode...)
     */
    @Override
    public void checkAccess(final Path path, final AccessMode... modes) throws IOException {
        final Object entry = cache.getEntry(path);

        if (!isFile(entry)) {
            return;
        }

        // TODO: assumed; not a file == directory
        for (final AccessMode mode : modes) {
            if (mode == AccessMode.EXECUTE) {
                throw new AccessDeniedException(path.toString());
            }
        }
    }

    @Override
    public void close() throws IOException {
    }

    /**
     * @throws IOException you should throw {@link NoSuchFileException} when the
     *             file not found.
     */
    @Nonnull
    @Override
    public Object getPathMetadata(final Path path) throws IOException {
        return cache.getEntry(path);
    }

    /** */
    private List<Path> getDirectoryEntries(Path dir) throws IOException {
        if (dir.getNameCount() == 0) {
            List<Path> list = new ArrayList<>(tracks.size());

            for (Track track : tracks.values()) {
                Path childPath = dir.resolve(URLEncoder.encode(track.getID() + " - " + track.getArtist() + " - " + track.getTitle() + ".mp3", "utf-8"));
                list.add(childPath);
            }

            return list;
        } else {
            throw new NoSuchFileException(dir.toString());
        }
    }
}
