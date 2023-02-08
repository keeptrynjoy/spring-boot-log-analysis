package com.example.loganalysis.extractor;

import com.example.loganalysis.dto.InputLogDto;
import com.example.loganalysis.reader.FileReaderMethodImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LogExtractorLauncherTest {

    @Autowired
    private LogExtractMethod logExtractMethod;

    @Autowired
    private FileReaderMethodImpl fileReaderMethod;

    @Test
    void extractLogList() {

        //given
//        FileReaderLauncher fileReader = new FileReaderLauncher("src/main/resources/input.log");
//        List<InputLogDto> inputLogDtoList = fileReader.readLogFile();
        List<InputLogDto> inputLogDtoList = fileReaderMethod.readLogFile("src/main/resources/input.log");

        //when
        logExtractMethod.mostCalledApiKeyExtractor(inputLogDtoList);
    }

    @Test
    void topThreeServiceIdCountExtractorTest(){

        //given
        List<InputLogDto> inputLogDtoList = fileReaderMethod.readLogFile("src/main/resources/input.log");

        //when
        logExtractMethod.topThreeServiceIdCountExtractor(inputLogDtoList);

    }
}