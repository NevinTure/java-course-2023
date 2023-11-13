package edu.project3;

import java.time.LocalDate;

public class MarkdownLogPrinter implements LogPrinter {

    private static final int DEFAULT_CODES_BY_REMOTE_ADDRESS_LIMIT = 15;
    private static final int DEFAULT_MOST_REQUIRED_RESOURCES_AMOUNT = 3;
    private static final int DEFAULT_MOST_STATUS_CODES_AMOUNT = 3;

    @Override
    public String print(Statistics statistics) {
        return print(
            statistics,
            DEFAULT_CODES_BY_REMOTE_ADDRESS_LIMIT,
            DEFAULT_MOST_REQUIRED_RESOURCES_AMOUNT,
            DEFAULT_MOST_STATUS_CODES_AMOUNT);
    }

    public String print(
        Statistics statistics,
        int codesByRemoteAddressLimit,
        int mostRequiredResourcesAmount,
        int mostStatusCodesAmount) {
        StringBuilder text = new StringBuilder();
        text.append("""
            #### Общая информация

            | Метрика | Значение |
            |:---:|:---:|
            """);
        text.append("|Файл(-ы)|").append(String.join(", ", statistics.getPaths())).append("|\n");
        text.append("|Начальная дата|").append(getStartDate(statistics.getFrom())).append("|\n");
        text.append("|Конечная дата|").append(getEndDate(statistics.getTo())).append("|\n");
        text.append("|Количество запросов|").append(statistics.getTotalEntries()).append("|\n");
        text.append("|Средний размер|").append(statistics.getAverageResponseSize()).append("|\n");
        text.append("|Количество уникальных пользователей|").append(statistics.getUniqueUsersCounter()).append("|\n");
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
                .append("|\n");
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
                .append("|\n");
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
                    .append("|\n");
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
