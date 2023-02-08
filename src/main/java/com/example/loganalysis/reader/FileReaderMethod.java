package com.example.loganalysis.reader;

import com.example.loganalysis.dto.InputLogDto;

import java.time.LocalDateTime;
import java.util.List;

public interface FileReaderMethod {

    public List<InputLogDto> readLogFile(String path);
}
