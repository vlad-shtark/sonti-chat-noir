package com.diasonti.chatnoir.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResponse {

    private int status;
    private Object data;

    public static RestResponse ok() {
        return new RestResponse(200, null);
    }

    public static RestResponse ok(Object data) {
        return new RestResponse(200, data);
    }

    public static RestResponse notFound() {
        return new RestResponse(404, null);
    }

    public static RestResponse internalError() {
        return new RestResponse(500, null);
    }

}
