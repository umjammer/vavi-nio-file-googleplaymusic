/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.nio.file.googleplaymusic;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nonnull;

import com.github.felixgail.gplaymusic.model.Track;
import com.github.fge.filesystem.attributes.provider.BasicFileAttributesProvider;


/**
 * {@link BasicFileAttributes} implementation for Google Play Music.
 */
public final class GPMBasicFileAttributesProvider extends BasicFileAttributesProvider implements PosixFileAttributes {

    private final Object entry;

    public GPMBasicFileAttributesProvider(@Nonnull final Object entry) throws IOException {
        this.entry = Objects.requireNonNull(entry);
    }

    /**
     * Returns the time of last modification.
     * <p>
     * If the file system implementation does not support a time stamp
     * to indicate the time of last modification then this method returns an
     * implementation specific default value, typically a {@code FileTime}
     * representing the epoch (1970-01-01T00:00:00Z).
     *
     * @return a {@code FileTime} representing the time the file was last
     *         modified
     */
    @Override
    public FileTime lastModifiedTime() {
        if (String.class.isInstance(entry)) {
            return FileTime.fromMillis(0);
        } else if (Track.class.isInstance(entry)) {
            return FileTime.fromMillis(0);
        } else {
            throw new IllegalStateException("unsupported type: " + entry.getClass().getName());
        }
    }

    /**
     * Tells whether the file is a regular file with opaque content.
     */
    @Override
    public boolean isRegularFile() {
        if (String.class.isInstance(entry)) {
            return false;
        } else if (Track.class.isInstance(entry)) {
            return true;
        } else {
            throw new IllegalStateException("unsupported type: " + entry.getClass().getName());
        }
    }

    /**
     * Tells whether the file is a directory.
     */
    @Override
    public boolean isDirectory() {
        if (String.class.isInstance(entry)) {
            return true;
        } else if (Track.class.isInstance(entry)) {
            return false;
        } else {
            throw new IllegalStateException("unsupported type: " + entry.getClass().getName());
        }
    }

    /**
     * Returns the size of the file (in bytes). The size may differ from the
     * actual size on the file system due to compression, support for sparse
     * files, or other reasons. The size of files that are not {@link
     * #isRegularFile regular} files is implementation specific and
     * therefore unspecified.
     *
     * @return the file size, in bytes
     */
    @Override
    public long size() {
        if (String.class.isInstance(entry)) {
            return 0;
        } else if (Track.class.isInstance(entry)) {
            return Track.class.cast(entry).getEstimatedSize();
        } else {
            throw new IllegalStateException("unsupported type: " + entry.getClass().getName());
        }
    }

    /* @see java.nio.file.attribute.PosixFileAttributes#owner() */
    @Override
    public UserPrincipal owner() {
        return null;
    }

    /* @see java.nio.file.attribute.PosixFileAttributes#group() */
    @Override
    public GroupPrincipal group() {
        return null;
    }

    /* @see java.nio.file.attribute.PosixFileAttributes#permissions() */
    @Override
    public Set<PosixFilePermission> permissions() {
        return isDirectory() ? PosixFilePermissions.fromString("rwxr-xr-x") : PosixFilePermissions.fromString("rw-r--r--");
    }
}
