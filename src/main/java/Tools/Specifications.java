package Tools;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specifications {

    public static RequestSpecification specWithURL(String URL) {
    return new RequestSpecBuilder()
            .setBaseUri(URL)
            .build();
    }

    public static RequestSpecification specWithURLandContentType(String URL, ContentType contentType) {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(contentType)
                .build();
    }

    public static void instalSpecifications(RequestSpecification request) {
        RestAssured.requestSpecification = request;
    }
}
