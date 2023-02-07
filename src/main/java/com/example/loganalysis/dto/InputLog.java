package com.example.loganalysis.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class InputLog {

    int status;
    String url;
    String webBrowser;
    LocalDateTime invokeDateTime;

}
