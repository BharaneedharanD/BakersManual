package networkcalls;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {
    private AtomicBoolean isIdle=new AtomicBoolean(true);
    private volatile ResourceCallback mCallback;

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
    mCallback=callback;
    }
    public void setIdleStat(boolean bool){
        isIdle.set(bool);
        if (isIdle.get() &&  mCallback!=null)
            mCallback.onTransitionToIdle();
    }
}
