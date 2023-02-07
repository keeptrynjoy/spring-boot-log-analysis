package com.example.loganalysis.util;

import com.example.loganalysis.dto.InputLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
public class LogAnalysisUtilsImpl implements LogAnalysisUtils {

    private static final String path = "src/main/resources/";

    @Override
    public List<InputLog> readLogFile() {

        FileReader fr = null;
        BufferedReader br= null;
        List<InputLog> inputLogList = new ArrayList<>();

        try {
            fr = new FileReader(path + "input.log");
            br = new BufferedReader(fr);

            String line ="";
            while ((line=br.readLine()) != null){

                List<String> list = Arrays.asList(splitLogStr(line));

                // 읽어온 로그 파일의 문자를 InputLog와 매핑해준다.
                inputLogList.add(InputLog.builder()
                        .status(Integer.parseInt(list.get(0)))
                        .url(list.get(1))
                        .webBrowser(list.get(2))
                        .invokeDateTime(convertStrToDateTime(list.get(3)))
                        .build());
            }

            br.close();
            fr.close();

            return inputLogList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object analyzeLog(){


        return"";
    }


    @Override
    public void writeLogContent(){

    }

    @Override
    public void createLogFile(){

    }

    /*
    *  로그 파일의 문자를 구분자를 기준으로 분할시키는 메서드
    * */
    private String[] splitLogStr(String strLog){
        return strLog.replaceAll("\\[","").split("\\]");
    }

    /*
    *  로그 파일의 문자열 날짜시간을 InputLog와 매핑시키기위한 변환 메서드
    * */
    private LocalDateTime convertStrToDateTime(String strDateTime){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.parse(strDateTime,formatter);
    }
}
