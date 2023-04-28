package Listener;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;

import java.util.ArrayList;
import java.util.List;

public class LogReportListener {
    private static boolean enabled = false;

    public LogReportListener() {
    }

    public static synchronized void turnOn() {
        if(!enabled) {
            List<Filter> filters = new ArrayList<>();
            filters.add(new AllureRestAssured());
            RestAssured.filters(filters);
            enabled = true;
        }
    }
}
