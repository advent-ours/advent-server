package com.adventours.calendar.gift.api;

import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.domain.GiftType;
import com.adventours.calendar.gift.service.UpdateGiftRequest;
import io.restassured.RestAssured;

public class UpdateGiftApi {
    private long calendarId = 1L;
    private long giftId = 1L;
    public GiftType giftType = GiftType.TEXT;

    private String title = "제목";
    private String textBody = "내용";
    private String link;
    private String uploadKey;
    private String extension;
    private String accessToken = ApiTest.accessToken;

    public UpdateGiftApi calendarId(final long calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    public UpdateGiftApi giftId(final long giftId) {
        this.giftId = giftId;
        return this;
    }

    public UpdateGiftApi giftType(final GiftType giftType) {
        this.giftType = giftType;
        return this;
    }

    public UpdateGiftApi title(final String title) {
        this.title = title;
        return this;
    }

    public UpdateGiftApi textBody(final String textBody) {
        this.textBody = textBody;
        return this;
    }

    public UpdateGiftApi link(final String link) {
        this.link = link;
        return this;
    }

    public UpdateGiftApi uploadKey(final String uploadKey) {
        this.uploadKey = uploadKey;
        return this;
    }

    public UpdateGiftApi extension(final String extension) {
        this.extension = extension;
        return this;
    }

    public UpdateGiftApi accessToken(final String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public Scenario request() {
        final UpdateGiftRequest request = new UpdateGiftRequest(
                giftType,
                title,
                textBody,
                link,
                uploadKey,
                extension
        );

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/calendar/{calendarId}/gift/{giftId}", calendarId, giftId)
                .then()
                .statusCode(200);

        return new Scenario();
    }
}
