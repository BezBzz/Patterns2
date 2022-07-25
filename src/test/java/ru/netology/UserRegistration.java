package ru.netology;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserRegistration {
    private static RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void registration(UserData user) {
        Gson gson = new Gson();
        String userBody = gson.toJson(user);
        given()
                .spec(spec)
                .body(userBody)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}
