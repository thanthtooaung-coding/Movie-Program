package com.moving_system;

// Functional interfaces for input handling
@FunctionalInterface
public interface InputValidator<T> {
    boolean isValid(T input);
}