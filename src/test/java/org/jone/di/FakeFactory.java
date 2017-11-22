package org.jone.di;

public class FakeFactory {

    @Provide
    public Foo createFoo(Context context) {
        return new Foo();
    }

    @Provide(singleton = true)
    public Bar createBar(Context context) {
        Foo foo = context.getInstance(Foo.class);
        return new Bar(foo);
    }
}
