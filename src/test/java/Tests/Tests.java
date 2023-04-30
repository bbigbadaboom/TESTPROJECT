package Tests;

import ObjectsForTests.Posts;
import Steps.Steps;
import Tools.Specifications;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static Tools.PropertiesGetter.getUrlProperty;
import static Tools.Specifications.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests extends BaseTest {
    Steps step = new Steps();
    Posts post = new Posts("Привет я Макс", "давай дружить", 111);
    @Test
    @DisplayName("GET /posts [статус кода : 200]")
    public void verify200StatusCodeForGetRequestTest() {
        Specifications.instalSpecifications(specWithURL(getUrlProperty("postUrl")));
        step.sendGetHttpRequest();
        step.verifyStatusCode(200);
    }

    @Test
    @DisplayName("GET /posts [валидация json schema]")
    public void verifyGetResponseMatchesJsonSchemaTest() {
        Specifications.instalSpecifications(specWithURL(getUrlProperty("postUrl")));
        step.sendGetHttpRequest();
        step.verifyStatusCode(200);
        step.verifyResponseMatchesJsonSchema("src/main/java/APIFiles/GetPostsDTO.json");
    }

    @Test
    @DisplayName("GET /posts [Тело ответа соответствует ожидаемому результату]")
    public void verifyGetResponseMatchesRealDataTest() throws IOException {
        Specifications.instalSpecifications(specWithURL(getUrlProperty("postUrl")));
        step.sendGetHttpRequest();
        step.verifyStatusCode(200);
        step.verifyResponseMatchesRealData("src/main/java/APIFiles/RealDataForGetRequest.json");

    }

    @Test
    @DisplayName("POST /posts [статус кода : 200]")
    public void verify200StatusCodeForPOSTRequestTest() throws IOException {
        Specifications.instalSpecifications(specWithURLandContentType(getUrlProperty("postUrl"), ContentType.JSON));
        step.sendPOSTHttpRequest(post);
        step.verifyStatusCode(201);
    }

    @Test
    @DisplayName("POST /posts [без параметров : 400]")
    public void verify400StatusCodeForPOSTRequestWithoutAllParamsTest() throws IOException {
        Posts emptyPost = new Posts();
        Specifications.instalSpecifications(specWithURLandContentType(getUrlProperty("postUrl"), ContentType.JSON));
        step.sendPOSTHttpRequest(emptyPost);
        step.verifyStatusCode(400);
    }

    @Test
    @DisplayName("POST /posts [без title : 400]")
    public void verify400StatusCodeForPOSTRequestWithoutTitleTest() throws IOException {
        Posts postWithoutTitle = new Posts("title", 11);
        Specifications.instalSpecifications(specWithURLandContentType(getUrlProperty("postUrl"), ContentType.JSON));
        step.sendPOSTHttpRequest(postWithoutTitle);
        step.verifyStatusCode(400);
    }

    @Test
    @DisplayName("POST /posts [без title, int body : 400]")
    public void verify400StatusCodeForPOSTRequestWithoutTitleAndIntBodyTest() throws IOException {
        Posts postWithoutTitle = new Posts(11, 11);
        Specifications.instalSpecifications(specWithURLandContentType(getUrlProperty("postUrl"), ContentType.JSON));
        step.sendPOSTHttpRequest(postWithoutTitle);
        step.verifyStatusCode(400);
    }

    @Test
    @DisplayName("POST /posts [без title, String userId : 400]")
    public void verify400StatusCodeForPOSTRequestWithoutTitleAndStringUserIdTest() throws IOException {
        Posts postWithoutTitle = new Posts("body", "id");
        Specifications.instalSpecifications(specWithURLandContentType(getUrlProperty("postUrl"), ContentType.JSON));
        step.sendPOSTHttpRequest(postWithoutTitle);
        step.verifyStatusCode(400);
    }

    //Дальнейший смысл написания проверок на отрицательные коды не вижу, так как функционал по моей логике работет некорреткно и будет с остальным (реальных требований нет)

    @Test
    @DisplayName("POST /posts [валидация json schema]")
    public void verifyPOSTResponseMatchesJsonSchemaTest() throws IOException {
        Specifications.instalSpecifications(specWithURLandContentType(getUrlProperty("postUrl"), ContentType.JSON));
        step.sendPOSTHttpRequest(post);
        step.verifyStatusCode(201);
        step.verifyResponseMatchesJsonSchema("src/main/java/APIFiles/PostPostsDTO.json");
    }

    @Test
    @DisplayName("POST /posts [проверка тела ответа]")
    public void verifyPOSTResponseBodyTest() throws IOException {
        Specifications.instalSpecifications(specWithURLandContentType(getUrlProperty("postUrl"), ContentType.JSON));
        step.sendPOSTHttpRequest(post);
        step.verifyStatusCode(201);
        step.assertParamEquals(post.getTitle(),step.getValue("title"), "Значение title не совпадает");
        step.assertParamEquals(post.getUserId().toString(),step.getValue("userId"), "Значение id не совпадает");
        step.assertParamEquals(post.getBody(),step.getValue("body"), "Значение body не совпадает");
    }

    @Test
    @DisplayName("POST /posts [проверка, что пост реально был создан]")
    public void verifyThatPostWasCreatedTest() throws IOException {
        Posts p = new Posts();
        Specifications.instalSpecifications(specWithURLandContentType(getUrlProperty("postUrl"), ContentType.JSON));
        step.sendPOSTHttpRequest(post);
        step.verifyStatusCode(201);
        Object post = step.saveAnswer(p);

        Specifications.instalSpecifications(specWithURL(getUrlProperty("postUrl")));
        step.sendGetHttpRequest();
        step.verifyStatusCode(200);
        step.verifyResponseContainsElement(post);
    }
}
