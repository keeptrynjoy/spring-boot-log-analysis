package com.example.loganalysis;

import com.example.loganalysis.service.dto.InputLogDto;
import com.example.loganalysis.service.LogExtractMethod;
import com.example.loganalysis.service.LogFileReader;
import com.example.loganalysis.service.LogFileWriter;

import java.util.List;

public class Launcher {

    /* service 패키지에 작성된 모듈을 작동시키기 위한 main 메서드 */
    public static void main(String[] args) {

        // project 폴더의 'input.log' 파일을 읽어 객체화 후 List에 담음.
        List<InputLogDto> inputLogDtoList = LogFileReader.readLogFile("input.log");

        StringBuilder sb = new StringBuilder();

        // 내용별로 공간을 주기 위한 개행 문자
        String newLine = System.getProperty("line.separator");

        // 각 요구사항별 추출 내용을 StringBuilder에 추가
        sb.append(LogExtractMethod.mostCalledApiKeyExtractor(inputLogDtoList)).append(newLine);
        sb.append(LogExtractMethod.topThreeServiceIdCountExtractor(inputLogDtoList)).append(newLine);
        sb.append(LogExtractMethod.usageRateByWebBrowser(inputLogDtoList)).append(newLine);

        // 합쳐진 추출 내용을 로그 파일로 생성
        LogFileWriter.writeLogFile("output.log", sb.toString(), true);
    }
}
