package com.aitp.dlife.integration.file.dto;

import java.util.Map;

public class ExcelImportErrorData {

    private Map<String, String> line;
    private Long lineNum;
    private String errorMessage;

    public Long getLineNum() {
        return lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }

    public Map<String, String> getLine() {
        return line;
    }

    public void setLine(Map<String, String> line) {
        this.line = line;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
