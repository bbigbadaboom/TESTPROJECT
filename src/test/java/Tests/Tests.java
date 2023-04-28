package Tests;

import ObjectsForTests.Posts;
import Steps.Steps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Tests {
    Steps step = new Steps();
    String URL = "https://jsonplaceholder.typicode.com/posts";
    Posts post = new Posts("Привет я Макс", "давай дружить", 111);
    @Test
    @DisplayName("GET /posts [статус кода : 200]")
    public void verify200StatusCodeForGetRequestTest() {
        step.sendGetHttpRequest(URL);
        step.verifyStatusCode(200);
    }

    @Test
    @DisplayName("GET /posts [валидация json schema]")
    public void verifyGetResponseMatchesJsonSchemaTest() {
        step.sendGetHttpRequest(URL);
        step.verifyStatusCode(200);
        step.verifyResponseMatchesJsonSchema("/Users/maksimkorolkov/GIT/TESTPROJECT/src/main/java/APIFiles/GetPostsDTO.json");
    }

    @Test
    @DisplayName("GET /posts [тело ответа соответсвует реальным данным]")
    public void verifyGetResponseMatchesRealDataTest() throws IOException {
        step.sendGetHttpRequest(URL);
        step.verifyStatusCode(200);
        step.verifyResponseMatchesRealData("/Users/maksimkorolkov/GIT/TESTPROJECT/src/main/java/APIFiles/RealDataForGetRequest.json");

    }

    @Test
    @DisplayName("POST /posts [статус кода : 200]")
    public void verify200StatusCodeForPOSTRequestTest() throws IOException {
        step.sendPOSTHttpRequest(URL, post);
        step.verifyStatusCode(201);
    }

    @Test
    @DisplayName("POST /posts [статус кода : 200]")
    public void verifyPOSTResponseMatchesJsonSchemaTest() throws IOException {
        step.sendPOSTHttpRequest(URL, post);
        step.verifyStatusCode(201);
        step.verifyResponseMatchesJsonSchema("/Users/maksimkorolkov/GIT/TESTPROJECT/src/main/java/APIFiles/PostPostsDTO.json");
    }

    @Test
    @DisplayName("POST /posts [проверка, что пост реально был создан]")
    public void verifyThatPostWasCreatedTest() throws IOException {
        Posts p = new Posts();
        step.sendPOSTHttpRequest(URL, post);
        step.verifyStatusCode(201);
        Object post = step.saveAnswer(p);
        step.sendGetHttpRequest(URL);
        step.verifyStatusCode(200);
        step.verifyResponseContainsElement(post);
    }
}
