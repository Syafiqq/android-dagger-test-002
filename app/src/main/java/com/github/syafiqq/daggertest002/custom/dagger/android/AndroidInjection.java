package com.github.syafiqq.daggertest002.custom.dagger.android;

import static android.util.Log.DEBUG;
import static dagger.internal.Preconditions.checkNotNull;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.util.Log;

import dagger.android.AndroidInjector;
import dagger.internal.Beta;

/** Injects core Android types. */
@Beta
public final class AndroidInjection {
  private static final String TAG = "dagger.android";

  /**
   * Injects {@code activity} if an associated {@link AndroidInjector} implementation can be found,
   * otherwise throws an {@link IllegalArgumentException}.
   *
   * @throws RuntimeException if the {@link Application} doesn't implement {@link
   *     HasAndroidInjector}.
   */
  public static void inject(Activity activity, Class<?> cls) {
    checkNotNull(activity, "activity");
    Application application = activity.getApplication();
    if (!(application instanceof HasAndroidInjector)) {
      throw new RuntimeException(
          String.format(
              "%s does not implement %s",
              application.getClass().getCanonicalName(),
              HasAndroidInjector.class.getCanonicalName()));
    }

    inject(activity, (HasAndroidInjector) application, cls);
  }

  /**
   * Injects {@code fragment} if an associated {@link AndroidInjector} implementation can be found,
   * otherwise throws an {@link IllegalArgumentException}.
   *
   * <p>Uses the following algorithm to find the appropriate {@code AndroidInjector<Fragment>} to
   * use to inject {@code fragment}:
   *
   * <ol>
   *   <li>Walks the parent-fragment hierarchy to find the a fragment that implements {@link
   *       HasAndroidInjector}, and if none do
   *   <li>Uses the {@code fragment}'s {@link Fragment#getActivity() activity} if it implements
   *       {@link HasAndroidInjector}, and if not
   *   <li>Uses the {@link android.app.Application} if it implements {@link HasAndroidInjector}.
   * </ol>
   *
   * If none of them implement {@link HasAndroidInjector}, a {@link IllegalArgumentException} is
   * thrown.
   *
   * @throws IllegalArgumentException if no parent fragment, activity, or application implements
   *     {@link HasAndroidInjector}.
   */
  public static void inject(Fragment fragment, Class<?> cls) {
    checkNotNull(fragment, "fragment");
    HasAndroidInjector hasAndroidInjector = findHasAndroidInjectorForFragment(fragment);
    if (Log.isLoggable(TAG, DEBUG)) {
      Log.d(
          TAG,
          String.format(
              "An injector for %s was found in %s",
              fragment.getClass().getCanonicalName(),
              hasAndroidInjector.getClass().getCanonicalName()));
    }

    inject(fragment, hasAndroidInjector, cls);
  }

  private static HasAndroidInjector findHasAndroidInjectorForFragment(Fragment fragment) {
    Fragment parentFragment = fragment;
    while ((parentFragment = parentFragment.getParentFragment()) != null) {
      if (parentFragment instanceof HasAndroidInjector) {
        return (HasAndroidInjector) parentFragment;
      }
    }
    Activity activity = fragment.getActivity();
    if (activity instanceof HasAndroidInjector) {
      return (HasAndroidInjector) activity;
    }
    if (activity.getApplication() instanceof HasAndroidInjector) {
      return (HasAndroidInjector) activity.getApplication();
    }
    throw new IllegalArgumentException(
        String.format("No injector was found for %s", fragment.getClass().getCanonicalName()));
  }

  /**
   * Injects {@code service} if an associated {@link AndroidInjector} implementation can be found,
   * otherwise throws an {@link IllegalArgumentException}.
   *
   * @throws RuntimeException if the {@link Application} doesn't implement {@link
   *     HasAndroidInjector}.
   */
  public static void inject(Service service, Class<?> cls) {
    checkNotNull(service, "service");
    Application application = service.getApplication();
    if (!(application instanceof HasAndroidInjector)) {
      throw new RuntimeException(
          String.format(
              "%s does not implement %s",
              application.getClass().getCanonicalName(),
              HasAndroidInjector.class.getCanonicalName()));
    }

    inject(service, (HasAndroidInjector) application, cls);
  }

  /**
   * Injects {@code broadcastReceiver} if an associated {@link AndroidInjector} implementation can
   * be found, otherwise throws an {@link IllegalArgumentException}.
   *
   * @throws RuntimeException if the {@link Application} from {@link
   *     Context#getApplicationContext()} doesn't implement {@link HasAndroidInjector}.
   */
  public static void inject(BroadcastReceiver broadcastReceiver, Context context, Class<?> cls) {
    checkNotNull(broadcastReceiver, "broadcastReceiver");
    checkNotNull(context, "context");
    Application application = (Application) context.getApplicationContext();
    if (!(application instanceof HasAndroidInjector)) {
      throw new RuntimeException(
          String.format(
              "%s does not implement %s",
              application.getClass().getCanonicalName(),
              HasAndroidInjector.class.getCanonicalName()));
    }

    inject(broadcastReceiver, (HasAndroidInjector) application, cls);
  }

  /**
   * Injects {@code contentProvider} if an associated {@link AndroidInjector} implementation can be
   * found, otherwise throws an {@link IllegalArgumentException}.
   *
   * @throws RuntimeException if the {@link Application} doesn't implement {@link
   *     HasAndroidInjector}.
   */
  public static void inject(ContentProvider contentProvider, Class<?> cls) {
    checkNotNull(contentProvider, "contentProvider");
    Application application = (Application) contentProvider.getContext().getApplicationContext();
    if (!(application instanceof HasAndroidInjector)) {
      throw new RuntimeException(
          String.format(
              "%s does not implement %s",
              application.getClass().getCanonicalName(),
              HasAndroidInjector.class.getCanonicalName()));
    }

    inject(contentProvider, (HasAndroidInjector) application, cls);
  }

  private static void inject(Object target, HasAndroidInjector hasAndroidInjector, Class<?> cls) {
    AndroidInjector<Object> androidInjector = cls == null ? hasAndroidInjector.androidInjector() : hasAndroidInjector.androidInjector(cls);
    checkNotNull(
        androidInjector, "%s.androidInjector() returned null", hasAndroidInjector.getClass());

    androidInjector.inject(target);
  }

  private AndroidInjection() {}
}