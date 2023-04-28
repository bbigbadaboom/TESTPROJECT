package Steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Steps {
    static Response response;
    StringWriter writer = new StringWriter();
    ObjectMapper mapper = new ObjectMapper();
    String answer;

    @Step("Выполнен GET запрос на {url}")
    public void sendGetHttpRequest(String url) {

        response =
                given()
                        .when()
                        .get(url)
                        .then()
                        .extract().response();
    }

    @Step("Выполнен POST запрос на {url}")
    public void sendPOSTHttpRequest(String url, Object body) throws IOException {
        mapper.writeValue(writer, body);
        String jsonBody = writer.toString();

        response =
                given()
                        .when()
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                        .post(url)
                        .then().log().body()
                        .extract().response();
    }

    @Step("Ответ имеет статус код {statusCode}")
    public void verifyStatusCode(int statusCode) {
        int realStatusCode = response
                .then()
                .extract().statusCode();
        assertEquals(realStatusCode, statusCode, "Ответ имеет статус код " + realStatusCode + " вместо " + statusCode);
    }

    @Step("Ответ соответствует json schema")
    public void verifyResponseMatchesJsonSchema(String jsonFilePath) {
        response.then().assertThat()
                .body(JsonSchemaValidator.
                        matchesJsonSchema(new File(jsonFilePath)));
    }

    @Step("Данные ответа соотсвтуют реальными")
    public void verifyResponseMatchesRealData(String filePathWithRealData) throws IOException {
        boolean equality = true;
        answer = response.then().extract().asString();
        List<Object> objectsFromAnswer = mapper.readValue(answer, new TypeReference<List<Object>>() {
        });
        File file = new File(filePathWithRealData);
        List<Object> realObjects = mapper.readValue(file, new TypeReference<List<Object>>() {
        });
        if (objectsFromAnswer.size() == realObjects.size()) {
            for (int i = 0; i < objectsFromAnswer.size(); i++) {
                if (!(objectsFromAnswer.get(i).equals(realObjects.get(i)))) {
                    equality = false;
                }
            }
        } else {
            equality = false;
        }
        assertEquals(equality, true, "Данные из ответа не соответствуют с реальными данными");

    }

    @Step("Тело ответа содержит элемент")
    public void verifyResponseContainsElement(Object object) throws JsonProcessingException {
        answer = response.then().extract().asString();
        List<Object> objectsFromAnswer = mapper.readValue(answer, new TypeReference<List<Object>>() {
        });
        assertTrue(objectsFromAnswer.contains(object), "Тело ответа не содержит элемента " + object.toString());
    }

    @Step("Cохранено тело ответа")
    public Object saveAnswer(Object object) throws JsonProcessingException {
      String ans = response.then().extract().body().asString();
      object = mapper.readValue(ans, Object.class);
      return object;
    }


}
