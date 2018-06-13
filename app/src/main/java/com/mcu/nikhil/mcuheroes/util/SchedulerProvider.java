package com.mcu.nikhil.mcuheroes.util;


import io.reactivex.Scheduler;

/**
 * Created by Mohsen on 20/10/2016.
 */

public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
