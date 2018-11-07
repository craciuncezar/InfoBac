package io.github.craciuncezar.infobac.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static Executor IO_EXECUTOR = Executors.newSingleThreadExecutor();

    public static void runOnIoThread(Runnable runnable) {
        IO_EXECUTOR.execute(runnable);
    }

}
