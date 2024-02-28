package org.epm;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionService {
    SubscriptionResponseDto createSubscription(SubscriptionRequestDto request);

    SubscriptionResponseDto updateSubscription(Long id, SubscriptionRequestDto request);

    void deleteSubscription(Long id);

    SubscriptionResponseDto getSubscription(Long id);

    List<SubscriptionResponseDto> getAllSubscription();
}