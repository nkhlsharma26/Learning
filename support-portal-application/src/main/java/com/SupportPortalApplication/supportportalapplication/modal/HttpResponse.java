package com.SupportPortalApplication.supportportalapplication.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;

}
