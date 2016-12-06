package com.jerry.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jerrychien on 2016/12/6.
 */
public class RegexTest {
    public static void main(String[] args) {
        // 捕获组
        // 捕获组是通过从左至右计算其开括号来编号。例如，在表达式（（A）（B（C））），有四个这样的组：
        // ((A)(B(C)))
        // (A)
        // (B(C))
        // (C)
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String regex = "(\\D*)(\\d)(.*)(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        int groups = matcher.groupCount();
        System.out.println(matcher.groupCount());
        if (matcher.find()) {
            for (int i = 0; i <= groups; i++) {
                System.out.println("Found value {" + i + "} : " + matcher.group(i));
            }
        } else {
            System.out.println("No matches");
        }
    }
}
