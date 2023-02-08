package com.example.loganalysis.extractor;

import com.example.loganalysis.dto.ExtractLogDto;
import com.example.loganalysis.dto.InputLogDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;


@Component
public class LogExtractMethodImpl implements LogExtractMethod{

    @Override
    public String mostCalledApiKeyExtractor(List<InputLogDto> inputLogDtoList) {

        String result = statusCodeFilter(inputLogDtoList)
                .map(ild -> new ExtractLogDto(ild.getUrl()))
                .collect(groupingBy(ExtractLogDto::getApiKey, counting()))
                .entrySet().stream()
                .max(comparingInt(api -> api.getValue().intValue()))
                .map(topApi -> topApi.getKey())
                .orElseThrow(RuntimeException::new);

        return strNewlineBuilder(Collections.singleton(result),"최다호출 API KEY");
    }

    @Override
    public String topThreeServiceIdCountExtractor(List<InputLogDto> inputLogDtoList) {

        ArrayList<Map.Entry<String, Long>> topThreeServiceId = statusCodeFilter(inputLogDtoList)
                .map(ild -> new ExtractLogDto(ild.getUrl()))
                .collect(groupingBy(ExtractLogDto::getServiceId, counting()))
                .entrySet().stream()
                    .sorted(comparing(m -> m.getValue(), reverseOrder()))
                    .limit(3)
                    .collect(toCollection(ArrayList::new));

        return  strNewlineBuilder(topThreeServiceId,"상위 3개의 API Service ID와 각각의 요청 수");
    }

    @Override
    public void usageRateByWebBrowser(List<InputLogDto> inputLogDtoList) {

    }

    private Stream<InputLogDto> statusCodeFilter(List<InputLogDto> inputLogDtoList){
        return inputLogDtoList.stream().filter(i -> i.getStatus() == 200);
    }

    private String strNewlineBuilder(Collection<?> result, String title){

        // OS에 맞게 개행 문자 반환
        String newLine = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder(title).append(newLine);

        for(Object s : result){
            sb.append(s).append(newLine);
        }

        return String.valueOf(sb);
    }
}
