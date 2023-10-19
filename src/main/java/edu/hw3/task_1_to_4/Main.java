package edu.hw3.task_1_to_4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private Main() {
    }

    public static String atbash(String str) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isLatinLetter(c)) {
                if (Character.isLowerCase(c)) {
                    ans.append((char) ('a' + 'z' - c));
                } else {
                    ans.append((char) ('A' + 'Z' - c));
                }
            } else {
                ans.append(c);
            }
        }
        return ans.toString();
    }

    private static boolean isLatinLetter(char c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z');
    }

    @SuppressWarnings("MultipleStringLiterals")
    public static List<String> clusterize(String str) {
        if (str == null || !str.matches("[()]+")) {
            throw new IllegalStateException("Invalid input!");
        }
        List<String> ans = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        int bracketCounter = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            bracketCounter += c == ')' ? -1 : 1;
            temp.append(c);
            if (bracketCounter == 0) {
                ans.add(temp.toString());
                temp.setLength(0);
            } else if (bracketCounter < 0) {
                throw new IllegalStateException("Invalid input!");
            }
        }
        if (bracketCounter != 0) {
            throw new IllegalStateException("Invalid input!");
        }
        return ans;
    }

    public static <T> Map<T, Integer> freqDict(T[] a) {
        Map<T, Integer> ans = new HashMap<>();
        for (T t : a) {
            ans.merge(t, 1, Integer::sum);
        }
        return ans;
    }

    @SuppressWarnings({"MagicNumber", "ParameterAssignment"})
    public static String convertToRoman(int num) {
        Map<Integer, String> nums = new HashMap<>();
        nums.put(0, "");
        nums.put(1, "I");
        nums.put(5, "V");
        nums.put(10, "X");
        nums.put(50, "L");
        nums.put(100, "C");
        nums.put(500, "D");
        nums.put(1000, "M");
        StringBuilder ans = new StringBuilder();
        int rank = 1;
        while (num > 0) {
            int curr = num % 10 * rank;
            num /= 10;
            if (nums.containsKey(curr)) {
                ans.append(nums.get(curr));
            } else {
                if (curr < 5 * rank) {
                    if (curr == 4 * rank) {
                        ans.append(nums.get(rank * 5)).append(nums.get(rank));
                    } else {
                        ans.append(nums.get(rank).repeat(curr / rank));
                    }
                } else {
                    if (curr == 9 * rank) {
                        ans.append(nums.get(rank * 10)).append(nums.get(rank));
                    } else {
                        ans
                            .append(nums
                                .get(rank)
                                .repeat(curr / rank - 5))
                            .append(nums.get(rank * 5));
                    }
                }
            }
            rank *= 10;
        }
        return ans.reverse().toString();
    }
}
