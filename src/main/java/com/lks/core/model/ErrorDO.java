package com.lks.core.model;

/**
 * Created by lokkur on 8/25/2015.
 */
public class ErrorDO extends AbstractDO {
    private String exceptionCode;
    private String statusText;

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
