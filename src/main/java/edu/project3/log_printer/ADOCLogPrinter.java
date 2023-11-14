package edu.project3.log_printer;

import edu.project3.Statistics;
import edu.project3.log_util.HttpStatusInfoUtil;
import java.time.LocalDate;

public class ADOCLogPrinter implements LogPrinter {
    private static final int DEFAULT_CODES_BY_REMOTE_ADDRESS_LIMIT = 15;
    private static final int DEFAULT_MOST_REQUIRED_RESOURCES_AMOUNT = 3;
    private static final int DEFAULT_MOST_STATUS_CODES_AMOUNT = 3;
    private static final String NEW_LINE = "\n\n";
    private static final String TABLE_ROW_SEPARATOR = "\n|";
    private final Statistics statistics;

    public ADOCLogPrinter(Statistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public String print() {
        return print(
            DEFAULT_CODES_BY_REMOTE_ADDRESS_LIMIT,
            DEFAULT_MOST_REQUIRED_RESOURCES_AMOUNT,
            DEFAULT_MOST_STATUS_CODES_AMOUNT);
    }

    @SuppressWarnings("MultipleStringLiterals")
    public String print(
        int codesByRemoteAddressLimit,
        int mostRequiredResourcesAmount,
        int mostStatusCodesAmount) {
        StringBuilder text = new StringBuilder();
        text.append("""
            ==== Общая информация

            |===
            | Метрика | Значение

            """);
        text.append("|Файл(-ы)\n|").append(String.join(", ", statistics.getPaths())).append(NEW_LINE);
        text.append("|Начальная дата\n|").append(getStartDate(statistics.getFrom())).append(NEW_LINE);
        text.append("|Конечная дата\n|").append(getEndDate(statistics.getTo())).append(NEW_LINE);
        text.append("|Количество запросов\n|").append(statistics.getTotalEntries()).append(NEW_LINE);
        text.append("|Средний размер\n|").append(statistics.getAverageResponseSize()).append(NEW_LINE);
        text.append("|Количество уникальных пользователей\n|").append(statistics.getUniqueUsersCounter())
            .append("\n|===");
        text.append("""


            ==== Запрашиваемые ресурсы

            |===
            | Ресурс | Количество
            """);
        for (var entry : statistics.getFirstKMostRequiredResources(mostRequiredResourcesAmount).entrySet()) {
            text
                .append(TABLE_ROW_SEPARATOR)
                .append(entry.getKey())
                .append(TABLE_ROW_SEPARATOR)
                .append(entry.getValue())
                .append(TABLE_ROW_SEPARATOR);
        }
        text.append("|===");
        text.append("""


            ==== Коды ответа

            |===
            | Коды | Имя | Количество
            """);
        for (var entry : statistics.getFirstKMostStatusCodes(mostStatusCodesAmount).entrySet()) {
            text
                .append(TABLE_ROW_SEPARATOR)
                .append(entry.getKey())
                .append(TABLE_ROW_SEPARATOR)
                .append(HttpStatusInfoUtil.getByCode(entry.getKey()))
                .append(TABLE_ROW_SEPARATOR)
                .append(entry.getValue())
                .append(TABLE_ROW_SEPARATOR);
        }
        text.append("|===");
        if (statistics.getUniqueUsersCounter() <= codesByRemoteAddressLimit) {
            text.append("""


                ==== Информация о пользователях

                |===
                | Ip пользователя | Самый частый код запроса
                """);
            for (var entry : statistics.getMostCodesByRemoteAddress().entrySet()) {
                text
                    .append(TABLE_ROW_SEPARATOR)
                    .append(entry.getKey())
                    .append(TABLE_ROW_SEPARATOR)
                    .append(entry.getValue().getExact())
                    .append(TABLE_ROW_SEPARATOR);
            }
        }
        text.append("|===");
        return text.toString();
    }

    private String getStartDate(LocalDate date) {
        if (date.equals(LocalDate.MIN)) {
            return "-";
        } else {
            return date.toString();
        }
    }

    private String getEndDate(LocalDate date) {
        if (date.equals(LocalDate.MAX)) {
            return "-";
        } else {
            return date.toString();
        }
    }
}
