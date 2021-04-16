package utilities;

import java.util.ArrayList;
import java.util.List;

public class Nok {

    private Nok(){
        //приватный конструктор чтоб нельзя было создать экземпляр утилитного класса
    }

    //статический метод нахождения простых множителей у числа
    public static <T extends Number> List<T> getMultiples(T number) {
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
    //статический метод для получения наименьшего общего кратного
    public static <T extends Number> Number getNokFromTwo(T a, T b) {
        if (a.longValue() <= 0 || b.longValue() <= 0) {
            throw new ArithmeticException("Произошла ошибка");
        }
        if (a.longValue() > b.longValue()) {
            T tmp = a;
            a = b;
            b = tmp;
        }
        long nok = 1L;
        long result = b.longValue();
        for (T i : Nok.getMultiples(a)) {
            if (a.longValue() % i.longValue() == 0 && result % i.longValue() == 0) {
                nok *= i.longValue();
                result = result / i.longValue();
            }
        }
        return a.longValue() / nok * b.longValue();
    }

    public static <T extends Number> T getNok(T... number) {
        T result = number[0];
        for (int i = 0; i < number.length; i++)
            result = (T) getNokFromTwo(result, number[i]);
        return result;
    }
}
