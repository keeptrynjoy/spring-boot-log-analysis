package com.example.loganalysis.service;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class LogFileWriter{

    /* 로그 파일을 작성하기 위한 메서드 */
    public static void writeLogFile(String fileName, String extractedContent, Boolean bAppend) {

        BufferedWriter bw = null;

        try {

            FileWriter fw = new FileWriter(fileName,bAppend);
            bw = new BufferedWriter(fw);

            bw.write(extractedContent);

            bw.flush();
            bw.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
