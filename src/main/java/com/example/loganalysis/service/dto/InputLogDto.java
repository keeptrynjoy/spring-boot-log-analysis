package com.example.loganalysis.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
public class InputLogDto {
    private int status;
    private String url;
    private String webBrowser;
    private LocalDateTime invokeDateTime;
}
