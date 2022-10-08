package com.nagaroo.utils;

import java.time.format.DateTimeFormatter;

public interface StatementConstants {
    DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatter1 = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String ADMIN = "ADMIN";
    String USER = "USER";
}
