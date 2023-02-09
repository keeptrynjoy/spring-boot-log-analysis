package com.example.loganalysis.service;

import com.example.loganalysis.service.dto.InputLogDto;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class LogFileReader{

    /* 로그 파일을 읽기 위한 메서드 */
    public static List<InputLogDto> readLogFile(String path) {

        BufferedReader br = null;
        List<InputLogDto> inputLogList = new ArrayList<>();

        try {
            FileReader fr = new FileReader(path);
            br = new BufferedReader(fr);

            String line = "";
            while ((line = br.readLine()) != null) {
                // 로그 파일에서 읽어온 문자를 객체화히여 list에 담음
                inputLogList.add(convertStrToDto(line));
            }

            br.close();
            fr.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return inputLogList;
    }

    /* 로그 파일의 문자를 객체화하기 위한 메서드 */
    private static InputLogDto convertStrToDto(String strLog) {

        List<String> list = Arrays.asList(strLog.replaceAll("\\[", "").split("\\]"));

        return InputLogDto.builder()
                .status(Integer.parseInt(list.get(0)))
                .url(list.get(1))
                .webBrowser(list.get(2))
                .invokeDateTime(convertStrToDateTime(list.get(3)))
                .build();
    }

    /* 로그 파일의 문자 객체화시 LocalDateTime 타입으로 변환시키기 위한 메서드 */
    private static LocalDateTime convertStrToDateTime(String strDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(strDateTime, formatter);
    }
}
