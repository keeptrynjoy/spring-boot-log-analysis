package com.example.loganalysis.util;

import com.example.loganalysis.dto.InputLog;

import java.util.List;

public interface LogAnalysisUtils {
    List<InputLog> readLogFile();

    Object analyzeLog();

    void writeLogContent();

    void createLogFile();
}
