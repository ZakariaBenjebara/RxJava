/**
 * Copyright 2016 Netflix, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package io.reactivex.disposables;

import java.util.concurrent.Future;

import org.reactivestreams.Subscription;

import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.*;

/**
 * Utility class to help create disposables by wrapping
 * other types.
 * @since 2.0
 */
public final class Disposables {
    /** Utility class. */
    private Disposables() {
        throw new IllegalStateException("No instances!");
    }
    
    /**
     * Construct a Disposable by wrapping a Runnable that is
     * executed exactly once when the Disposable is disposed.
     * @param run the Runnable to wrap
     * @return the new Disposable instance
     */
    public static Disposable from(Runnable run) {
        Objects.requireNonNull(run, "run is null");
        return new RunnableDisposable(run);
    }

    /**
     * Construct a Disposable by wrapping a Action that is
     * executed exactly once when the Disposable is disposed.
     * @param run the Action to wrap
     * @return the new Disposable instance
     */
    public static Disposable from(Action run) {
        Objects.requireNonNull(run, "run is null");
        return new ActionDisposable(run);
    }

    /**
     * Construct a Disposable by wrapping a Future that is
     * cancelled exactly once when the Disposable is disposed.
     * @param future the Future to wrap
     * @return the new Disposable instance
     */
    public static Disposable from(Future<?> future) {
        Objects.requireNonNull(future, "future is null");
        return from(future, true);
    }

    /**
     * Construct a Disposable by wrapping a Runnable that is
     * executed exactly once when the Disposable is disposed.
     * @param future the Runnable to wrap
     * @param allowInterrupt if true, the future cancel happens via Future.cancel(true)
     * @return the new Disposable instance
     */
    public static Disposable from(Future<?> future, boolean allowInterrupt) {
        Objects.requireNonNull(future, "future is null");
        return new FutureDisposable(future, allowInterrupt);
    }

    /**
     * Construct a Disposable by wrapping a Subscription that is
     * cancelled exactly once when the Disposable is disposed.
     * @param subscription the Runnable to wrap
     * @return the new Disposable instance
     */
    public static Disposable from(Subscription subscription) {
        Objects.requireNonNull(subscription, "subscription is null");
        return new SubscriptionDisposable(subscription);
    }

    /**
     * Returns a new, undisposed Disposable instance.
     * @return a new, undisposed Disposable instance
     */
    public static Disposable empty() {
        return from(Functions.EMPTY_RUNNABLE);
    }

    /**
     * Returns a disposed Disposable instance.
     * @return a disposed Disposable instance
     */
    public static Disposable disposed() {
        return EmptyDisposable.INSTANCE;
    }
}
