package Tests;

import Steps.Steps;
import org.junit.jupiter.api.Test;

public class Tests {
    Steps s = new Steps();
    @Test
    public void test() {
        s.sendGetHttpRequest("https://stackoverflow.com/questions/65677156/could-not-transfer-artifact-from-to-central-intellij");
        s.verifyStatusCode(205);

    }
}
