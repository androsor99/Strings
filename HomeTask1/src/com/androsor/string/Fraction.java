package com.androsor.string;

import java.util.GregorianCalendar;

public class Fraction {
    private int x = 10;
    private String y = "0";
    public static void main(String[] args) {
       Fraction fr1 = new Fraction();

        double m;
        double n = -3.0;
        System.out.println(m = n/2);
        System.out.println(fr1.getClass());
        //System.out.println(s instanceof String);
        Function fun = new Function();

//        System.out.println(fun instanceof Object); // true
//        System.out.println(fun.getClass()); // class com.androsor.string.Fraction
//
//                System.out.println(fun.equals(null));
        GregorianCalendar cal = new GregorianCalendar();
//        System.out.println(cal.getTime());
//        System.out.println(cal.getClass());
//
        Integer i = 5;
//        System.out.println(i);
//        System.out.println(i.getClass());
//        System.out.println(i.equals(null));
        System.out.println(cal.getTime().toString());
    }


}
    class Function extends Fraction {
    public int z = 100;
    public String s = "function";
     //System.out.println(s instanceof Fraction);

}
