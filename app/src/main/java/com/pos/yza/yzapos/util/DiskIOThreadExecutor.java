package com.pos.yza.yzapos.util;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Dalzy Mendoza on 16/2/18.
 */

/**
 * Executor that runs a task on a new background thread.
 * This implementation is used by the Android instrumentation tests.
 */
public class DiskIOThreadExecutor implements Executor {

    private final Executor mDiskIO;

    public DiskIOThreadExecutor() {
        mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        // increment the idling resources before executing the long running command
        EspressoIdlingResource.increment();
        mDiskIO.execute(command);
        // decrement the idling resources once executing the command has been finished
        EspressoIdlingResource.decrement();
    }
}

