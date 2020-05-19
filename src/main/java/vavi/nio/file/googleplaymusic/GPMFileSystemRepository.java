/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.nio.file.googleplaymusic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.github.felixgail.gplaymusic.api.GPlayMusic;
import com.github.fge.filesystem.driver.FileSystemDriver;
import com.github.fge.filesystem.provider.FileSystemRepositoryBase;

import vavi.net.auth.AppCredential;
import vavi.net.auth.UserCredential;
import vavi.net.auth.oauth2.googleplaymusic.GPMLocalAppCredential;
import vavi.net.auth.oauth2.googleplaymusic.GPMLocalAuthenticator;
import vavi.net.auth.oauth2.googleplaymusic.GPMLocalUserCredential;
import vavi.util.Debug;
import vavi.util.properties.annotation.Property;
import vavi.util.properties.annotation.PropsEntity;

import svarzee.gps.gpsoauth.AuthToken;


/**
 * GPMFileSystemRepository.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/02/15 umjammer initial version <br>
 */
@ParametersAreNonnullByDefault
@PropsEntity(url = "classpath:googleplaymusic.properties")
public final class GPMFileSystemRepository extends FileSystemRepositoryBase {

    /** */
    public GPMFileSystemRepository() {
        super("googleplaymusic", new GPMFileSystemFactoryProvider());
    }

    /** should have a constructor without args */
    @Property(value = "vavi.net.auth.oauth2.googleplaymusic.GPMLocalAuthenticator")
    private String authenticatorClassName = "vavi.net.auth.oauth2.googleplaymusic.GPMLocalAuthenticator";

    /* */
    {
        try {
            PropsEntity.Util.bind(this);
        } catch (Exception e) {
Debug.println(Level.WARNING, "no googleplaymusic.properties in classpath, use defaut");
        }
Debug.println("authenticatorClassName: " + authenticatorClassName);
    }

    /**
     * TODO root from uri
     *
     * @throws NoSuchElementException required values are not in env
     */
    @Nonnull
    @Override
    public FileSystemDriver createDriver(final URI uri, final Map<String, ?> env) throws IOException {
        // 1. user credential
        UserCredential userCredential = null;

        if (env.containsKey(GPMFileSystemProvider.ENV_USER_CREDENTIAL)) {
            userCredential = UserCredential.class.cast(env.get(GPMFileSystemProvider.ENV_USER_CREDENTIAL));
        }

        Map<String, String> params = getParamsMap(uri);
        if (userCredential == null && params.containsKey(GPMFileSystemProvider.PARAM_ID)) {
            String email = params.get(GPMFileSystemProvider.PARAM_ID);
            userCredential = new GPMLocalUserCredential(email);
        }

        if (userCredential == null) {
            throw new NoSuchElementException("uri not contains a param " + GPMFileSystemProvider.PARAM_ID + " nor " +
                                             "env not contains a param " + GPMFileSystemProvider.ENV_USER_CREDENTIAL);
        }

        // 2. app credential
        AppCredential appCredential = null;

        if (env.containsKey(GPMFileSystemProvider.ENV_APP_CREDENTIAL)) {
            appCredential = AppCredential.class.cast(env.get(GPMFileSystemProvider.ENV_APP_CREDENTIAL));
        }

        if (appCredential == null) {
            appCredential = new GPMLocalAppCredential(); // TODO use prop
        }

        // 3. process
        GPMLocalAuthenticator authenticator = getAuthenticator(appCredential.getClientId());
        AuthToken authToken = authenticator.authorize(userCredential);
        GPlayMusic api = new GPlayMusic.Builder().setDebug(false).setAuthToken(authToken).setAndroidID(appCredential.getClientId()).build();
        final GPMFileStore fileStore = new GPMFileStore(api, factoryProvider.getAttributesFactory());
        return new GPMFileSystemDriver(fileStore, factoryProvider, api, env);
    }

    /** */
    private GPMLocalAuthenticator getAuthenticator(String androidId) {
        try {
            GPMLocalAuthenticator authenticator = GPMLocalAuthenticator.class.cast(Class.forName(authenticatorClassName)
                .getDeclaredConstructor(String.class).newInstance(androidId));
            return authenticator;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    };
}
