package com.adventours.calendar.gift.api;

import com.adventours.calendar.gift.domain.GiftType;
import com.adventours.calendar.gift.service.UpdateGiftRequest;
import io.restassured.RestAssured;

import static com.adventours.calendar.common.ApiTest.accessToken;

public class UpdateGiftApi {
    private long calendarId = 1L;
    private long giftId = 1L;
    public GiftType giftType = GiftType.TEXT;

    private String title = "제목";
    private String textBody = "내용";
    private String contentUrl = null;

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

    public UpdateGiftApi contentUrl(final String contentUrl) {
        this.contentUrl = contentUrl;
        return this;
    }



    public void request() {
        final UpdateGiftRequest request = new UpdateGiftRequest(
                giftType,
                title,
                textBody,
                contentUrl
        );

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/calendar/{calendarId}/gifts/{giftId}", calendarId, giftId)
                .then()
                .statusCode(200);
    }
}
