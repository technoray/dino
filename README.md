# Dino
### Dependency Injection made simple for Java 8

#### No setter injection

Dino doesn't promote the setter injection technique. Construct objects as you would normally do - by invoking their constructor... or builder ...or whatever _you_ decide is cleaner.

#### No need to add annotations to _your_ classes

The classes to be injected don't need to have Dino annotations. All the wiring logic is kept separately in factories, where objects are created as they normally would. Only cleaner.

#### Separation of construction from implementation

By keeping the construction logic separate from actual implementation Dino doesn't add any restrictions to your classes. Just define them accorting to 
your own needs. You don't need to change your existing code to use DI, just define a factory where you construct all your objects, that's it.


### Three steps to get started

#### 1. Create a factory

Create a factory, mark methods with `@Provide` annotation.

```java
public class MyFactory {

  @Provide
  public Foo createFoo(Context context) {
    return new Foo();
  }

  @Provide
  public Bar createBar(Context context) {
    Foo foo = context.getInstance(Foo.class);
    return new Bar(foo);
  }
}
```

#### 2. Register factory at `ApplicationContext`.

```java
public static void main(String[] args) {
  ApplicationContext context = new ApplicationContext();
  context.register(new ProviderTypeLookup(new MyFactory()));
  // ...
}
```

#### 3. ... and use it
```java
  Foo foo = context.getInstance(Foo.class);
  foo.doSomething();
```



#### Singleton

If there's a need to keep exactly one instance of a class within the application context, it's enough to mark a corresponding factory method with a `singleton` parameter: 

```java
  @Provide(singleton = true)
  public Bar createBar(Context context) {
    return new Bar();
  }
```
