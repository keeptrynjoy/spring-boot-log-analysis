package com.example.loganalysis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class InputLogDto {
    private int status;
    private String url;
    private String webBrowser;
    private LocalDateTime invokeDateTime;
}
