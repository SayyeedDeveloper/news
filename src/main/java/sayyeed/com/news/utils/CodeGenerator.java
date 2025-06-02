package sayyeed.com.news.utils;

import java.util.Random;

public class CodeGenerator {

    private static final Random random = new Random();

    public static String fiveDigit(){
        return String.format("%05d", 10000 + random.nextInt(90000));
    }
}
