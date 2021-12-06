package com.bennyhuo.kotlin.coroutinebasics.javaimpl;

import com.bennyhuo.coroutines.sample2.theory.SuspendFunctionsKt;
import com.bennyhuo.kotlin.coroutinebasics.utils.LogKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.jetbrains.annotations.NotNull;

public class ContinuationImpl implements Continuation<Object> {

    private int label = 0;

    private final Continuation<Unit> completion;

    public ContinuationImpl(Continuation<Unit> completion) {
        this.completion = completion;
    }

    @NotNull
    @Override
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    @Override
    public void resumeWith(@NotNull Object o) {
        try {
            Object result = o;
            switch (label) {
                case 0: {
                    LogKt.log(1);
                    result = SuspendFunctionsKt.returnSuspended( this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 1: {
                    LogKt.log(result);
                    LogKt.log(2);
                    result = DelayKt.delay(1000, this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 2: {
                    LogKt.log(3);
                    result = SuspendFunctionsKt.returnImmediately( this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 3:{
                    LogKt.log(result);
                    LogKt.log(4);
                }
            }
            completion.resumeWith(Unit.INSTANCE);
        } catch (Exception e) {
            completion.resumeWith(e);
        }
    }

    private boolean isSuspended(Object result) {
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }

    public static void main(String... args) throws Throwable {
        RunSuspend runSuspend = new RunSuspend();
        ContinuationImpl table = new ContinuationImpl(runSuspend);
        table.resumeWith(Unit.INSTANCE);
        runSuspend.await();
    }
}
