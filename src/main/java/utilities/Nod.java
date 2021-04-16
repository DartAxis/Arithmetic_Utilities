package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Nod {

    private Nod(){
        //приватный конструктор чтоб нельзя было создать экземпляр утилитного класса
    }

    private static <T extends Number> Number genericsNod(T a, T b) {

        long nod = 1L;
        if (a.longValue() <= 0 || b.longValue() <= 0) {
            throw new ArithmeticException("Произошла ошибка");
        }
        if (a.longValue() % b.longValue() == 0) {
            return b.longValue();
        } else if (b.longValue() % a.longValue() == 0) {
            return a;
        } else {
            if (a.longValue() > b.longValue()) {
                T tmp = a;
                a = b;
                b = tmp;
            }
            for (T i : getListSimpleDividersGen(a)) {
                if (a.longValue() % i.longValue() == 0 && b.longValue() % i.longValue() == 0) {
                    nod *= i.longValue();
                    b = (T) (Long) (b.longValue() / i.longValue());
                }
            }
        }
        return nod;
    }

    public static <T extends Number> List<T> getListSimpleDividersGen(T number) {
        List<T> list = new ArrayList<>();
        if (number.longValue() == 0) {
            throw new ArithmeticException("Нельзя вводить нулевое значение");
        }
        for (Long i = 2L; i <= number.longValue(); i++) {
            while (number.longValue() % i == 0) {
                list.add((T) i);
                number = (T) (Long) (number.longValue() / i.longValue());
            }
        }
        return list;
    }

    public static <T extends Number> T getNod(T... number) {
        Stream<T> wordsStream = Stream.of(number);
        Optional<? super T> result = wordsStream.reduce((x, y) -> (T) genericsNod(x, y));

        return (T)result.orElse(null);
    }
}
