package stream;

import java.util.Objects;

@FunctionalInterface
public interface MyBiConsumer<T, U> {

    void accept(T t, U u);

default MyBiConsumer<T, U> andThen(MyBiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after);
        return (l, r) -> {
            accept(l, r);
            after.accept(l, r);
        };
    }
}