package org.jone.di;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class Test1 {


    @Test
    public void test() {
        ApplicationContext context = new ApplicationContext();
        context.register(new ProviderTypeLookup(new FakeFactory()));

        Foo foo1 = context.getInstance(Foo.class);
        Foo foo2 = context.getInstance(Foo.class);

        Bar bar1 = context.getInstance(Bar.class);
        Bar bar2 = context.getInstance(Bar.class);

        assertNotSame(foo1, foo2);
        assertSame(bar1, bar2);
    }
}
