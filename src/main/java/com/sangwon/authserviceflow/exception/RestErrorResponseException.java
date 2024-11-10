package com.sangwon.authserviceflow.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class RestErrorResponseException extends ErrorResponseException {
    public RestErrorResponseException(final ProblemDetail problemDetail) {
        super(HttpStatusCode.valueOf(problemDetail.getStatus()), problemDetail, null);

    }
}
