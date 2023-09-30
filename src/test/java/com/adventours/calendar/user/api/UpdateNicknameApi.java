package com.adventours.calendar.user.api;

import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.user.service.UpdateNicknameRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import static com.adventours.calendar.common.ApiTest.accessToken;

public class UpdateNicknameApi {
    private String nickname = "updatedNickname";

    public static UpdateNicknameApi updateNickname() {
        return new UpdateNicknameApi();
    }

    public Scenario request() {
        final UpdateNicknameRequest request = new UpdateNicknameRequest(nickname);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .header("Authorization", accessToken)
                .put("/user/nickname")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
