package com.playground.java8;

import org.junit.jupiter.api.Test;

public class FunctionalInterfaceTest {

    @Test
    void testFunctionalInterface() {
        functionalInterface(() -> System.out.println("It works"));
    }

    @Test
    void testAbstractClassAsFunctionalInterface() {
        System.out.println("Abstract class can't be used as functional interface. " +
                "Compilation Error: Target type of lambda conversion must be an interface");
        // abstractClassAsFunctionalInterface(() -> System.out.println("It works!"));
    }

    public void functionalInterface(MyFunctionalInterface functionalInterface) {
        functionalInterface.doSomething();
    }

    @SuppressWarnings("unused")
    public void abstractClassAsFunctionalInterface(MyAbstractClass cls) {
        cls.doSomething();
    }

    private static abstract class MyAbstractClass {
        public abstract void doSomething();
    }

    @FunctionalInterface
    private interface MyFunctionalInterface {
        void doSomething();
    }
}
