package com.example.loganalysis.reader;

import com.example.loganalysis.dto.InputLogDto;
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
public class FileReaderMethodImpl implements FileReaderMethod{

    @Override
    public List<InputLogDto> readLogFile(String path) {
        BufferedReader br = null;
        List<InputLogDto> inputLogList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(path);
            br = new BufferedReader(fr);

            String line = "";
            while ((line = br.readLine()) != null) {
                InputLogDto log = convertStrToDto(line);
                inputLogList.add(log);
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inputLogList;
    }

    private InputLogDto convertStrToDto(String strLog) {
        List<String> list = Arrays.asList(strLog.replaceAll("\\[", "").split("\\]"));

        return InputLogDto.builder()
                .status(Integer.parseInt(list.get(0)))
                .url(list.get(1))
                .webBrowser(list.get(2))
                .invokeDateTime(convertStrToDateTime(list.get(3)))
                .build();
    }

    private LocalDateTime convertStrToDateTime(String strDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(strDateTime, formatter);
    }
}
