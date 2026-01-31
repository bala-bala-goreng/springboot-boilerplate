package com.boilerplate.app.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ApiResponseDto<T> {

    @JsonIgnore
    private ObjectMapper objectMapper = new ObjectMapper();
    private boolean success = false;
    private String message;
    private Page paging;
    private Object data;
    private Error error;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss Z", timezone = "Asia/Jakarta")
    private Date timestamp;

    public ApiResponseDto() {
        this.success = true;
    }

    public ApiResponseDto(String message) {
        this.success = true;
        this.message = message;
    }

    public ApiResponseDto(String message, List<T> data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public ApiResponseDto(List<T> data) {
        this.success = true;
        this.data = data;
    }

    public ApiResponseDto(String erroCode, String errorMessage) {
        this.success = false;
        this.error = new Error(erroCode, errorMessage);
    }

    @Getter
    @Setter
    public static class Page {

        private int totalRecords = 0;
        private int page = 1;
        private int totalPages = 1;
        private int pageSize = 10;

        public Page(int totalRecords, int page, int totalPages, int pageSize) {
            this.totalRecords = totalRecords;
            this.page = page;
            this.totalPages = totalPages;
            this.pageSize = pageSize;
        }

    }

    @Getter
    @Setter
    public static class Error {

        private String code;
        private String message;

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    @Getter
    @Setter
    public static class Success {

        private String code;
        private String message;

        public Success(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    public void setPaging(int totalCount, int currentPage, int pageCount, int pageSize) {
        this.paging = new Page(totalCount, currentPage, pageCount, pageSize);
    }

    public Date getTimestamp() {
        return new Date();
    }

    public void addData(Object item) {
        if (this.data == null) {
            this.data = item;
        } else if (this.data instanceof List<?> existingList) {
            List<Object> safeList = new ArrayList<>(existingList);
            safeList.add(item);
            this.data = safeList;
        } else {
            List<Object> newList = new ArrayList<>();
            newList.add(this.data);
            newList.add(item);
            this.data = newList;
        }
    }

    public String toString() {
        String json = "{\"success\":false,\"error\":{\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"Internal Server Error\"}}";
        try {
            json = objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            log.error("Error when parsing response model.", e);
        }
        return json;
    }

}
