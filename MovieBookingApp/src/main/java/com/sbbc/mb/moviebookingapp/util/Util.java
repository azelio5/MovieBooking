package com.sbbc.mb.moviebookingapp.util;

import java.util.function.Consumer;

public class Util {
    public static <T> void applyIfNotNull(Consumer<T> consumer, T value) {
        if (value != null) {
            consumer.accept(value);
        }
    }
}
