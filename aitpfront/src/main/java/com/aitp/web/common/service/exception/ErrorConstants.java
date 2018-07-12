package com.aitp.web.common.service.exception;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "http://www.jhipster.tech/problem";
    public static final URI WECHAT_ERROR_TYPE = URI.create(PROBLEM_BASE_URL + "/wechat-error-type");

    private ErrorConstants() {
    }
}
