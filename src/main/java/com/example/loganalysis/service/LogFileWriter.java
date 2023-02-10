package com.example.loganalysis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class LogFileWriter{

    private static final Logger LOGGER = LoggerFactory.getLogger(LogFileReader.class);

    /* 로그 파일을 작성하기 위한 메서드 */
    public static void writeLogFile(String fileName, String extractedContent, Boolean bAppend) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,bAppend));){

            bw.write(extractedContent);
            bw.flush();

        } catch (IOException e) {
            LOGGER.error("로그 파일을 작성하는 동안 오류가 발생했습니다.",e);
        }
    }
}
