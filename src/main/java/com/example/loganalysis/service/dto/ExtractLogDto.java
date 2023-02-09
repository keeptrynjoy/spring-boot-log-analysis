package com.example.loganalysis.service.dto;

import lombok.Getter;

@Getter
public class ExtractLogDto {

    private String apiKey;
    private String serviceId;

    public ExtractLogDto(String url) {
        this.apiKey = extractApiKeyFromUrl(url);
        this.serviceId = extractServiceIdFromUrl(url);
    }

    private String extractServiceIdFromUrl(String url){

        int startIndex = url.lastIndexOf("/")+1;
        int endIndex = url.indexOf("?",startIndex);

        if(endIndex == -1){
            return "None";
        }

        return url.substring(startIndex,endIndex);
    }

    private String extractApiKeyFromUrl(String url){

        int startIndex = url.indexOf("=")+1;
        int endIndex = url.indexOf("&");

        if(endIndex == -1){
            return "None";
        }

        return url.substring(startIndex,endIndex);
    }
}
