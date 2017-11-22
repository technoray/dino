package org.jone.di;

import java.lang.invoke.MethodHandle;

public class MethodHandleProvider implements Provider {

    private final Object source;

    private final MethodHandle method;

    public MethodHandleProvider(Object source, MethodHandle method) {
        this.source = source;
        this.method = method;
    }

    @Override
    public Object provide(Context context) {
        try {
            return method.invoke(source, context);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
