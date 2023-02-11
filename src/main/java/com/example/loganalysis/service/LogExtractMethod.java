package com.example.loganalysis.service;

import com.example.loganalysis.service.dto.ExtractLogDto;
import com.example.loganalysis.service.dto.InputLogDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;


@Component
public class LogExtractMethod{

    /* 개행 문자(OS에 맞게 변환) */
    private static final String NEW_LINE = System.lineSeparator();

    /* 최다호출 API KEY 추출 메서드 */
    public static String mostCalledApiKeyExtractor(List<InputLogDto> inputLogDtoList) {
        
        StringBuilder sb = new StringBuilder("최다호출 API KEY").append(NEW_LINE);

        String result = statusCodeFilter(inputLogDtoList) // 상태 200의 로그만 추출
                .map(ild -> new ExtractLogDto(ild.getUrl())) // 로그의 URL에서 API KEY 추출
                .collect(groupingBy(ExtractLogDto::getApiKey, counting())) // API KEY 별 개수 추출
                .entrySet().stream()// API KEY와 요청 수가 key,value 형태로된 Map을 반환
                .max(Map.Entry.comparingByValue()) // 최다 호출 API KEY의 요청 수를 찾기 위해 비교
                .map(Map.Entry::getKey) // 찾아낸 최다 호출 API KEY의 이름만 반환
                .orElseThrow(RuntimeException::new);

        sb.append(result).append(NEW_LINE);

        return sb.toString();
    }

    /* 상위 3개 API Service ID 및 요청 수 추출 메서드 */
    public static String topThreeServiceIdCountExtractor(List<InputLogDto> inputLogDtoList) {

        StringBuilder sb = new StringBuilder("상위 3개의 API Service ID와 각각의 요청 수").append(NEW_LINE);

        List<Map.Entry<String, Long>> topThreeServiceIdCounts = statusCodeFilter(inputLogDtoList) // 상태 200의 로그만 추출
                        .map(ild -> new ExtractLogDto(ild.getUrl())) // 로그의 URL에서 Service ID 추출
                        .collect(groupingBy(ExtractLogDto::getServiceId, counting())) // Service ID 별 개수 추출
                        .entrySet().stream() // ServiceID와 요청 수가 key,value 형태로된 Map을 반환
                        .sorted(Map.Entry.comparingByValue(reverseOrder())) // ServiceID의 요청 수를 기준으로 역정렬
                        .limit(3) // 정렬된 사항에서 앞의 3개 Service ID만 추출
                        .collect(toList());

        for (Map.Entry<String, Long> map : topThreeServiceIdCounts) {
            sb.append(map.getKey())
                    .append(" : ")
                    .append(map.getValue())
                    .append(NEW_LINE);
        }

        return  sb.toString();
    }

    /* 웹브라우저별 사용 비율 추출 메서드 */
    public static String usageRateByWebBrowser(List<InputLogDto> inputLogDtoList) {

        StringBuilder sb = new StringBuilder("웹브라우저별 사용 비율").append(NEW_LINE);

        List<Map.Entry<String, Double>> collect = statusCodeFilter(inputLogDtoList) // 상태 200의 로그만 추출
                .collect(teeing( // 두 번의 계산 결과를 한번에 처리하기 위해 Java 12 Collectors.teeing()을 사용
                        counting(), // 로그 전체 개수 추출
                        groupingBy(InputLogDto::getWebBrowser, counting()), // Web Browser별 개수 추출
                        (totalCount, groupResult) -> {
                            Map<String, Double> result = new HashMap<>(); // 계산된 값을 담을 Map 생성
                            groupResult.forEach( // Web Browser와 요청 수가 key,value 형태로된 Map을 반복문으로 추출
                                    // Web Browser와 계산된 value를 result에 담아서 반환
                                    (k, v) -> result.put(k, (double) (v * 100) / totalCount));
                            return result;
                        })
                ).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(reverseOrder())) // 요청 수가 높은 순으로 list에 담기 위해 역정렬
                .collect(toList());

        for (Map.Entry<String, Double> map : collect) {
            sb.append(map.getKey())
                    .append(" : ")
                    .append(strFormat(map.getValue()))
                    .append(NEW_LINE);
        }

        return  sb.toString();
    }

    /* status(상태)가 200인 값만 추출하기 위한 메서드 */
    private static Stream<InputLogDto> statusCodeFilter(List<InputLogDto> inputLogDtoList){
        return inputLogDtoList.stream().filter(i -> i.getStatus() == 200);
    }

    /* Web Browser 추출 과정에서 계산된 요청 수를 출력하기 위한 변환 메서드  */
    private static String strFormat(double value){
        return String.format("%.1f",value) + "%";
    }
    
}
