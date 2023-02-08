package com.example.loganalysis.extractor;

import com.example.loganalysis.dto.InputLogDto;

import java.util.List;

public interface LogExtractMethod {

    public String mostCalledApiKeyExtractor(List<InputLogDto> inputLogDtoList);

    public String topThreeServiceIdCountExtractor(List<InputLogDto> inputLogDtoList);

    void usageRateByWebBrowser(List<InputLogDto> inputLogDtoList);
}
