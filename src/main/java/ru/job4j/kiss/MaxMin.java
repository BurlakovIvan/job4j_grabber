package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;

public class MaxMin {
    public <T> T max(List<T> value, Comparator<T> comparator) {
        return compare(value, (valueFirst, valueSecond)
                -> comparator.compare(valueFirst, valueSecond) < 0);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return max(value, comparator.reversed());
    }

    public <T> T compare(List<T> values, BiPredicate<T, T> predicate) {
        T rsl = values.size() > 0 ? values.get(0) : null;
        for (T value : values) {
            if (predicate.test(rsl, value)) {
                rsl = value;
            }
        }
        return rsl;
    }
}
