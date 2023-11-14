package edu.project3;

import java.time.LocalDate;

public class ADOCLogPrinter implements LogPrinter {
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
            ==== Общая информация

            |===
            | Метрика | Значение

            """);
        text.append("|Файл(-ы)\n|").append(String.join(", ", statistics.getPaths())).append("\n\n");
        text.append("|Начальная дата\n|").append(getStartDate(statistics.getFrom())).append("\n\n");
        text.append("|Конечная дата\n|").append(getEndDate(statistics.getTo())).append("\n\n");
        text.append("|Количество запросов\n|").append(statistics.getTotalEntries()).append("\n\n");
        text.append("|Средний размер\n|").append(statistics.getAverageResponseSize()).append("\n\n");
        text.append("|Количество уникальных пользователей\n|").append(statistics.getUniqueUsersCounter()).append("\n|===");
        text.append("""


            ==== Запрашиваемые ресурсы

            |===
            | Ресурс | Количество
            """);
        for (var entry : statistics.getFirstKMostRequiredResources(mostRequiredResourcesAmount).entrySet()) {
            text
                .append("\n|")
                .append(entry.getKey())
                .append("\n|")
                .append(entry.getValue())
                .append("\n");
        }
        text.append("|===");
        text.append("""


            ==== Коды ответа

            |===
            | Коды | Имя | Количество
            """);
        for (var entry : statistics.getFirstKMostStatusCodes(mostStatusCodesAmount).entrySet()) {
            text
                .append("\n|")
                .append(entry.getKey())
                .append("\n|")
                .append(HttpStatusInfoUtil.getByCode(entry.getKey()))
                .append("\n|")
                .append(entry.getValue())
                .append("\n");
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
                    .append("\n|")
                    .append(entry.getKey())
                    .append("\n|")
                    .append(entry.getValue().getExact())
                    .append("\n");
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
