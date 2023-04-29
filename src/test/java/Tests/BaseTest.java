package Tests;

import Listener.LogReportListener;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    @BeforeAll
    public static void logReportListnerTurnOn() {
        System.err.println("LogReportListener.turnOn()");
        LogReportListener.turnOn();
    }
}
