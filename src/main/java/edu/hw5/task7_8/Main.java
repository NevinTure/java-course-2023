package edu.hw5.task7_8;

import java.util.regex.Pattern;

public class Main {

    //содержит не менее 3 символов, причем третий символ равен 0
    public static final Pattern AT_LEAST_3_SYMBOLS_AND_THIRD_IS_0 = Pattern.compile("^[01]{2}0[01]*$");

    //начинается и заканчивается одним и тем же символом
    public static final Pattern ENDS_AND_STARTS_THE_SAME = Pattern.compile("^((0[01]*0)|(1[01]*1)|0|1)$");

    //длина не менее 1 и не более 3
    public static final Pattern SIZE_FROM_1_TO_3_SYMBOLS = Pattern.compile("^[01]{1,3}$");

    //нечетной длины
    public static final Pattern ODD_SIZE = Pattern.compile("^[01]([01]{2})*$");

    //начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину
    public static final Pattern STARTS_WITH_0_ODD_SIZE_OR_STARTS_WITH_1_EVEN_SIZE = Pattern.compile("^((0([01]{2})*)|(1[10]([01]{2})*))$");


    //количество 0 кратно 3
    private static final String threeZeros = "(000)";
    private static final String anyTrioWith1Zero = "((011)|(101)|(110))";
    public static final Pattern ZERO_MULTIPLE_3 = Pattern.compile("");

    //любая строка, кроме 11 или 111
    public static final Pattern ANY_STRING_EXCEPT_2_OR_3_ONES = Pattern.compile("^(?!(111?))$");

    //каждый нечетный символ равен 1
    public static final Pattern EVERY_ODD_SYMBOL_IS_1 = Pattern.compile("^1([01]1)*$");

    //содержит не менее двух 0 и не более одной 1
    public static final Pattern AT_LEAST_2_ZEROS_AND_LESS_THAT_2_ONES = Pattern.compile("^(0+10+)|(10{2,})|(0{2,}1)|(0{2,})$");

    //нет последовательных 1
    public static final Pattern NOT_CONSECUTIVE_ONES = Pattern.compile("^[10]*(?!(1{2,}))[10]*$");


    private Main() {
    }

}
