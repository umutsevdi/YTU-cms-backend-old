package com.cms.controllers.global_exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class SuccessDetails {
    private final LocalDateTime timeStamp;
    private final String message;
    private final String principal;
    private final String path;
    private final String status;

}
