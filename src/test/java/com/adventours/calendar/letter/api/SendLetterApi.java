package com.adventours.calendar.letter.api;

import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.letter.SendLetterRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.UUID;

public class SendLetterApi {

    private String content = "content";
    private UUID calendarId = null;
    private String accessToken = ApiTest.accessToken;

    public SendLetterApi content(final String content) {
        this.content = content;
        return this;
    }

    public SendLetterApi calendarId(final UUID calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    public SendLetterApi accessToken(final String user2AccessToken) {
        accessToken = user2AccessToken;
        return this;
    }

    public Scenario request() {
        assert calendarId != null;
        SendLetterRequest request = new SendLetterRequest(content);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(request)
                .when()
                .post("/calendar/{calendarId}/letter", calendarId.toString())
                .then()
                .log().all()
                .statusCode(200);

        return new Scenario();
    }
}
