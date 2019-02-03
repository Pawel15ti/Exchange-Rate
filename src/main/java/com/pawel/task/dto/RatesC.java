package com.pawel.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatesC {

    private String no;
    private Date effectiveDate;
    private Double bid;
    private Double ask;
}
