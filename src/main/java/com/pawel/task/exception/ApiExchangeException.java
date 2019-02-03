package com.pawel.task.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiExchangeException extends RuntimeException {

    private Integer status;
    private String code;
    private String description;



}
