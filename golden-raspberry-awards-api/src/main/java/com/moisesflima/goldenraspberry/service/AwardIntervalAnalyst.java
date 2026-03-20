package com.moisesflima.goldenraspberry.service;

import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse;
import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse.ProducerIntervalDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AwardIntervalAnalyst {

    public static final class Builder {
        private int minInterval = Integer.MAX_VALUE;
        private int maxInterval = Integer.MIN_VALUE;
        private final List<ProducerIntervalDto> minList = new ArrayList<>();
        private final List<ProducerIntervalDto> maxList = new ArrayList<>();

        public void accept(ProducerIntervalDto dto) {
            int interval = dto.interval();

            if (interval < minInterval) {
                minInterval = interval;
                minList.clear();
                minList.add(dto);
            } else if (interval == minInterval) {
                minList.add(dto);
            }

            if (interval > maxInterval) {
                maxInterval = interval;
                maxList.clear();
                maxList.add(dto);
            } else if (interval == maxInterval) {
                maxList.add(dto);
            }
        }

        public MaxMinWinIntervalForProducersResponse build() {
            if (minInterval == Integer.MAX_VALUE) {
                return new MaxMinWinIntervalForProducersResponse(List.of(), List.of());
            }
            return new MaxMinWinIntervalForProducersResponse(
                    List.copyOf(minList),
                    List.copyOf(maxList)
            );
        }
    }

    public Builder newBuilder() {
        return new Builder();
    }
}
