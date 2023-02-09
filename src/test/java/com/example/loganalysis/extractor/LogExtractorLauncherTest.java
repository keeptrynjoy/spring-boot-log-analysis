package com.example.loganalysis.extractor;

import com.example.loganalysis.service.LogExtractMethod;
import com.example.loganalysis.service.dto.InputLogDto;
import com.example.loganalysis.service.LogFileReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LogExtractorLauncherTest {

    @Autowired
    private LogExtractMethod logExtractMethod;

    @Autowired
    private LogFileReader logFileReader;

    @Test
    void extractLogList() {

        //given
        List<InputLogDto> inputLogDtoList = logFileReader.readLogFile("src/main/resources/input.log");

        //when
        logExtractMethod.mostCalledApiKeyExtractor(inputLogDtoList);
    }

    @Test
    void topThreeServiceIdCountExtractorTest(){

        //given
        List<InputLogDto> inputLogDtoList = logFileReader.readLogFile("src/main/resources/input.log");

        //when
        logExtractMethod.topThreeServiceIdCountExtractor(inputLogDtoList);

    }

    @Test
    void usageRateByWebBrowserTest(){

        //given
        List<InputLogDto> inputLogDtoList = logFileReader.readLogFile("src/main/resources/input.log");

        //when
        logExtractMethod.usageRateByWebBrowser(inputLogDtoList);
    }
}