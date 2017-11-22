package org.jone.di;

public class SingletonProvider implements Provider {

    private Object value;

    private final Provider original;

    public SingletonProvider(Provider original) {
        this.original = original;
    }

    @Override
    public Object provide(Context context) {
        if (value == null) {
            value = original.provide(context);
        }
        return value;
    }
}
