package com.adventours.calendar.global;

import com.adventours.calendar.exception.ResCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> {
    private StatusResponse status = new StatusResponse(ResCode.HBD200);
    private final T data;

    public CommonResponse() {
        this.data = null;
    }

    public CommonResponse(T data) {
        this.data = data;
    }

    public CommonResponse(ResCode resCode) {
        this();
        this.status = new StatusResponse(resCode);
    }

    @Getter
    @Setter
    public static class StatusResponse {
        private final String resCode;
        private final String resMessage;

        public StatusResponse(ResCode resCode) {
            this(resCode.name(), resCode.getMessage());
        }

        public StatusResponse(String resCode, String resMessage) {
            this.resCode = resCode;
            this.resMessage = resMessage;
        }
    }

}
