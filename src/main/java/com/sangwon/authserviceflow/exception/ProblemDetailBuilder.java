package com.sangwon.authserviceflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;


public class ProblemDetailBuilder {
    private final ProblemDetail problemDetail;

    private ProblemDetailBuilder(final HttpStatusCode statusCode) {
        this.problemDetail = ProblemDetail.forStatus(statusCode);
    }

    public static ProblemDetailBuilder forStatus(final HttpStatusCode status) {
        return new ProblemDetailBuilder(status);
    }

    public static ProblemDetailBuilder forStatusAndDetail(final HttpStatus status, final String detail) {
        ProblemDetailBuilder builder = new ProblemDetailBuilder(status);
        builder.problemDetail.setDetail(detail);
        return builder;
    }

    public ProblemDetailBuilder withErrorType(final ErrorType errorType) {
        this.problemDetail.setType(errorType.getUri());
        return this;
    }

    public ProblemDetailBuilder withProperty(final String name, final Object value) {
        this.problemDetail.setProperty(name, value);
        return this;
    }

    public ProblemDetail build() {
        return this.problemDetail;
    }
}
