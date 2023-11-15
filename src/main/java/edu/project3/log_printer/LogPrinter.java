package edu.project3.log_printer;

public interface LogPrinter {

    String print();

    String print(int codesByRemoteAddressLimit,
        int mostRequiredResourcesAmount,
        int mostStatusCodesAmount);
}
