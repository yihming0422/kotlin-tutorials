package com.bennyhuo.kotlin.coroutinebasics.javaimpl;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import org.jetbrains.annotations.NotNull;

public class RunSuspend implements Continuation<Unit> {

    private Object result;

    @Override
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    @Override
    public void resumeWith(@NotNull Object result) {
        synchronized (this){
            this.result = result;
            notifyAll();
        }
    }

    public void await() throws Throwable {
        synchronized (this){
            while (true){
                Object result = this.result;
                if(result == null) wait();
                else if(result instanceof Throwable){
                    throw (Throwable) result;
                } else return;
            }
        }
    }
}
