package edu.project3.log_printer;

import edu.project3.Statistics;
import edu.project3.log_util.HttpStatusInfoUtil;
import java.time.LocalDate;

public class MarkdownLogPrinter implements LogPrinter {

    private static final int DEFAULT_CODES_BY_REMOTE_ADDRESS_LIMIT = 15;
    private static final int DEFAULT_MOST_REQUIRED_RESOURCES_AMOUNT = 3;
    private static final int DEFAULT_MOST_STATUS_CODES_AMOUNT = 3;
    private static final String TABLE_ROW_SEPARATOR = "|\n";
    private final Statistics statistics;

    public MarkdownLogPrinter(Statistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public String print() {
        return print(
            DEFAULT_CODES_BY_REMOTE_ADDRESS_LIMIT,
            DEFAULT_MOST_REQUIRED_RESOURCES_AMOUNT,
            DEFAULT_MOST_STATUS_CODES_AMOUNT);
    }

    public String print(
        int codesByRemoteAddressLimit,
        int mostRequiredResourcesAmount,
        int mostStatusCodesAmount) {
        StringBuilder text = new StringBuilder();
        text.append("""
            #### Общая информация

            | Метрика | Значение |
            |:---:|:---:|
            """);
        text.append("|Файл(-ы)|").append(String.join(", ", statistics.getPaths())).append(TABLE_ROW_SEPARATOR);
        text.append("|Начальная дата|").append(getStartDate(statistics.getFrom())).append(TABLE_ROW_SEPARATOR);
        text.append("|Конечная дата|").append(getEndDate(statistics.getTo())).append(TABLE_ROW_SEPARATOR);
        text.append("|Количество запросов|").append(statistics.getTotalEntries()).append(TABLE_ROW_SEPARATOR);
        text.append("|Средний размер|").append(statistics.getAverageResponseSize()).append(TABLE_ROW_SEPARATOR);
        text.append("|Количество уникальных пользователей|").append(statistics.getUniqueUsersCounter())
            .append(TABLE_ROW_SEPARATOR);
        text.append("""

            #### Запрашиваемые ресурсы

            | Ресурс | Количество |
            |:---:|:---:|
            """);
        for (var entry : statistics.getFirstKMostRequiredResources(mostRequiredResourcesAmount).entrySet()) {
            text
                .append("|")
                .append(entry.getKey())
                .append("|")
                .append(entry.getValue())
                .append(TABLE_ROW_SEPARATOR);
        }
        text.append("""

            #### Коды ответа

            | Коды | Имя | Количество |
            |:---:|:---:|:---:|
            """);
        for (var entry : statistics.getFirstKMostStatusCodes(mostStatusCodesAmount).entrySet()) {
            text
                .append("|")
                .append(entry.getKey())
                .append("|")
                .append(HttpStatusInfoUtil.getByCode(entry.getKey()))
                .append("|")
                .append(entry.getValue())
                .append(TABLE_ROW_SEPARATOR);
        }
        if (statistics.getUniqueUsersCounter() <= codesByRemoteAddressLimit) {
            text.append("""

                #### Информация о пользователях

                | Ip пользователя | Самый частый код запроса |
                |:---:|:---:|
                """);
            for (var entry : statistics.getMostCodesByRemoteAddress().entrySet()) {
                text
                    .append("|")
                    .append(entry.getKey())
                    .append("|")
                    .append(entry.getValue().getExact())
                    .append(TABLE_ROW_SEPARATOR);
            }
        }
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
