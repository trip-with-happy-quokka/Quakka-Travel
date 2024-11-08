package com.sparta.quokkatravel.domain.common.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MetricService {

    private final MeterRegistry meterRegistry;

    /*
    * name : 카운터 이름
    * description : 카운터 설명
    * tags : 태그 맵(태그 이름, 태그 값)
    * */
    public void incrementCounter(String name, String description, Map<String, String> tags) {
        Counter.Builder counterBuilder = Counter.builder(name)
                .description(description);

        // 태그 추가
        tags.forEach(counterBuilder::tag);

        // 메트릭 레지스트리에 등록하고 증가
        counterBuilder.register(meterRegistry).increment();

    }
}
