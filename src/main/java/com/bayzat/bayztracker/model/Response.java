package com.bayzat.bayztracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Response<T> {

    private T data;

    private String message;

    private boolean isError;

}