package com.github.syafiqq.daggertest002.custom.dagger.android;

import android.app.Application;
import android.content.ContentProvider;

import java.util.HashMap;
import java.util.Map;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.internal.Beta;
import kotlin.jvm.Synchronized;

import javax.inject.Inject;

/**
 * An {@link Application} that injects its members and can be used to inject objects that the
 * Android framework instantiates, such as Activitys, Fragments, or Services. Injection is performed
 * in {@link #onCreate()} or the first call to {@link AndroidInjection#inject(ContentProvider)},
 * whichever happens first.
 */
@Beta
public abstract class DaggerApplication extends Application implements HasAndroidInjector {
    private Map<Class<?>, Object> holders = new HashMap<>();
    private Map<Class<?>, DispatchingAndroidInjector<Object>> injectors = new HashMap<>();

    @Inject volatile DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        injectIfNecessary(rootComponent());
    }

    /**
     * Implementations should return an {@link AndroidInjector} for the concrete {@link
     * dagger.android.DaggerApplication}. Typically, that injector is a {@link dagger.Component}.
     */
    protected abstract AndroidInjector<?> applicationInjector(Class<?> cls, Map<Class<?>, Object> holders);

    protected abstract Class<?> rootComponent();

    /**
     * Lazily injects the {@link dagger.android.DaggerApplication}'s members. Injection cannot be performed in {@link
     * Application#onCreate()} since {@link android.content.ContentProvider}s' {@link
     * android.content.ContentProvider#onCreate() onCreate()} method will be called first and might
     * need injected members on the application. Injection is not performed in the constructor, as
     * that may result in members-injection methods being called before the constructor has completed,
     * allowing for a partially-constructed instance to escape.
     */
    private void injectIfNecessary(Class<?> cls) {
        if (!injectors.containsKey(cls) || injectors.get(cls) == null) {
            synchronized (this) {
                if (!injectors.containsKey(cls) || injectors.get(cls) == null)  {
                    if(!holders.containsKey(cls))
                        holders.put(cls, applicationInjector(cls, holders));
                    @SuppressWarnings("unchecked")
                    AndroidInjector<DaggerApplication> applicationInjector =
                            (AndroidInjector<DaggerApplication>) holders.get(cls);
                    applicationInjector.inject(this);
                    if (androidInjector == null) {
                        throw new IllegalStateException(
                                "The AndroidInjector returned from applicationInjector() did not inject the "
                                        + "DaggerApplication");
                    }
                    injectors.put(cls, androidInjector);
                    return;
                }
            }
        }

        synchronized (this) {
            androidInjector = injectors.get(cls);
        }
    }

    @Override
    @Synchronized
    public AndroidInjector<Object> androidInjector() {
        // injectIfNecessary should already be called unless we are about to inject a ContentProvider,
        // which can happen before Application.onCreate()
        return androidInjector(rootComponent());
    }

    @Override
    @Synchronized
    public AndroidInjector<Object> androidInjector(Class<?> cls) {
        // injectIfNecessary should already be called unless we are about to inject a ContentProvider,
        // which can happen before Application.onCreate()
        injectIfNecessary(cls);

        return androidInjector;
    }


    @Override
    public void clear(Class<?> cls) {
        holders.remove(cls);
        injectors.remove(cls);
    }

    @Override
    public boolean exists(Class<?> cls) {
        return injectors.containsKey(cls);
    }
}