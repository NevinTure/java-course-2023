package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public final class Main {
    private final static Logger LOGGER = LogManager.getLogger();

    private Main() {
    }

    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        LOGGER.info("Hello and welcome!");

        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 0; i <= 2; i++) {

            // Press Shift+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Ctrl+F8.
            LOGGER.info("i = {}", i);
        }
    }

    public static void helloWorld() {
        LOGGER.info("Привет, мир!");
    }

    public static long minutesToSeconds(String time) {
        String[] timeArray = time.split(":");
        long seconds;
        long minutes;
        try {
            minutes = Long.parseLong(timeArray[0]);
            seconds = Long.parseLong(timeArray[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return -1;
        }
        if (seconds >= 60 || seconds < 0 || minutes < 0) {
            return -1;
        }
        return seconds + minutes * 60;
    }

    public static int countDigits(int num) {
        int digitCounter = 0;
        do {
            digitCounter++;
            num = num / 10;
        } while (num != 0);
        return digitCounter;
    }

    public static boolean isNestable(int[] a1, int[] a2) {
        int minA1 = Integer.MAX_VALUE;
        int minA2 = Integer.MAX_VALUE;
        int maxA1 = Integer.MIN_VALUE;
        int maxA2 = Integer.MIN_VALUE;
        for (int i = 0; i < a1.length; i++) {
            minA1 = Math.min(minA1, a1[i]);
            maxA1 = Math.max(maxA1, a1[i]);
        }
        for (int i = 0; i < a2.length; i++) {
            minA2 = Math.min(minA2, a2[i]);
            maxA2 = Math.max(maxA2, a2[i]);
        }
        return minA1 > minA2 && maxA1 < maxA2;
    }

    public static String fixString(String s) {
        StringBuilder ans = new StringBuilder();
        for (int i = 1; i < s.length(); i += 2) {
            ans.append(s.charAt(i)).append(s.charAt(i - 1));
        }
        //Проверка: если строка имеет нечётный размер, то добавить
        //последний элемент из строки s в ans, так как в цикле он не обработается
        if (s.length() % 2 != 0) {
            ans.append(s.charAt(s.length() - 1));
        }
        return ans.toString();
    }

    public static boolean isPalindromeDescendant(int num) {
        int neighbor1;
        int neighbor2;
        int newNum;
        int rank;
        while (num > 10) {
            if (isPalindrome(num)) {
                return true;
            }
            newNum = 0;
            rank = 1;
            while (num > 0) {
                neighbor1 = num % 10;
                neighbor2 = num % 100 / 10;
                newNum += rank * (neighbor1 + neighbor2);
                rank *= 10;
                num /= 100;
            }
            num = newNum;
        }
        return false;
    }

    private static boolean isPalindrome(int num) {
        String s = String.valueOf(num);
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static int countK(int num) {
        if (num == 6174) {
            return 0;
        }
        int numAsc = 0;
        int numDesc = 0;
        int[] numArrAsc = new int[4];
        for (int i = 0; i < 4; i++) {
            numArrAsc[i] = num % 10;
            num /= 10;
        }
        Arrays.sort(numArrAsc);
        int rank = 1000;
        for (int i = 0; i < 4; i++) {
            numAsc += rank * numArrAsc[i];
            numDesc += rank * numArrAsc[4 - 1 - i];
            rank /= 10;
        }
        return 1 + countK(numDesc - numAsc);
    }

    public static int rotateLeft(int n, int shift) {
        String s = Integer.toBinaryString(n);
        StringBuilder shiftedStr = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            shiftedStr.append(s.charAt((i + shift) % s.length()));
        }
        return Integer.parseInt(shiftedStr.toString(), 2);
    }

    public static int rotateRight(int n, int shift) {
        String s = Integer.toBinaryString(n);
        StringBuilder shiftedStr = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            shiftedStr.append(s.charAt((s.length() + i - shift) % s.length()));
        }
        return Integer.parseInt(shiftedStr.toString(), 2);
    }

    public static boolean knightBoardCapture(int[][] board) {
        int[] moveCol = {-2,-1,1,2,2,1,-1,-2};
        int[] moveRow = {-1,-2,-2,-1,1,2,2,1};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1) {
                    for (int k = 0; k < 8; k++) {
                        if (i + moveRow[k] >= board.length ||
                            i + moveRow[k] < 0 ||
                            j + moveCol[k] >= board[0].length ||
                            j + moveCol[k] < 0) {
                            continue;
                        }
                        if (board[i + moveRow[k]][j + moveCol[k]] == 1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
