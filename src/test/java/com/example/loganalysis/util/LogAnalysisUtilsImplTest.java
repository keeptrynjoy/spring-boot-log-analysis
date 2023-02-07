package com.example.loganalysis.util;

import com.example.loganalysis.dto.InputLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogAnalysisUtilsImplTest {

    @Autowired
    private LogAnalysisUtils logAnalysisUtils;

    @Test
    void readLogFile() {

        //given

        //when
        logAnalysisUtils.readLogFile();

        //then

    }
}