package com.moving_system;

// Functional interfaces for input handling
@FunctionalInterface
public interface InputParser<T> {
    T parse(String input);
}