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

    private static final String NEW_LINE = System.getProperty("line.separator");

    @Override
    public String mostCalledApiKeyExtractor(List<InputLogDto> inputLogDtoList) {
        
        StringBuilder sb = new StringBuilder("최다호출 API KEY").append(NEW_LINE);

        String result = statusCodeFilter(inputLogDtoList)
                .map(ild -> new ExtractLogDto(ild.getUrl()))
                .collect(groupingBy(ExtractLogDto::getApiKey, counting()))
                .entrySet().stream()
                .max(comparingInt(api -> api.getValue().intValue()))
                .map(Map.Entry::getKey)
                .orElseThrow(RuntimeException::new);

        sb.append(result);

        return sb.toString();
    }

    @Override
    public String topThreeServiceIdCountExtractor(List<InputLogDto> inputLogDtoList) {

        StringBuilder sb = new StringBuilder("상위 3개의 API Service ID와 각각의 요청 수").append(NEW_LINE);

        ArrayList<Map.Entry<String, Long>> topThreeServiceId =
                statusCodeFilter(inputLogDtoList)
                        .map(ild -> new ExtractLogDto(ild.getUrl()))
                        .collect(groupingBy(ExtractLogDto::getServiceId, counting()))
                        .entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(reverseOrder()))
                        .limit(3)
                        .collect(toCollection(ArrayList::new));

        for (Map.Entry<String, Long> map : topThreeServiceId) {
            sb.append(map.getKey()).append(" : ").append(map.getValue()).append(NEW_LINE);
        }

        return  sb.toString();
    }

    @Override
    public String usageRateByWebBrowser(List<InputLogDto> inputLogDtoList) {

        StringBuilder sb = new StringBuilder("웹브라우저별 사용 비율").append(NEW_LINE);

        ArrayList<Map.Entry<String, Double>> collect = statusCodeFilter(inputLogDtoList)
                .collect(teeing(
                        counting(),
                        groupingBy(InputLogDto::getWebBrowser, counting()),
                        (count, groupResult) -> {
                            Map<String, Double> result = new HashMap<>();
                            groupResult.forEach(
                                    (k, v) -> result.put(k, (double) (v * 100) / count));
                            return result;
                        })
                ).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(reverseOrder()))
                .collect(toCollection(ArrayList::new));

        for (Map.Entry<String, Double> map : collect) {
            sb.append(map.getKey()).append(" : ").append(strFormat(map.getValue())).append(NEW_LINE);
        }

        return  sb.toString();
    }

    private Stream<InputLogDto> statusCodeFilter(List<InputLogDto> inputLogDtoList){
        return inputLogDtoList.stream().filter(i -> i.getStatus() == 200);
    }

    private String strFormat(double value){
        return String.format("%.1f",value) + "%";
    }
    
}
