package org.epm.mapper;

import org.epm.Subscription;
import org.epm.SubscriptionRequestDto;
import org.epm.SubscriptionResponseDto;
import org.epm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    private final UserRepository userService;

    @Autowired
    public SubscriptionMapper(UserRepository userService) {
        this.userService = userService;
    }

    public SubscriptionResponseDto toDto(Subscription subscription) {
        SubscriptionResponseDto dto = new SubscriptionResponseDto();
        dto.setId(subscription.getId());
        dto.setUserId(subscription.getUser().getId());
        dto.setStartDate(subscription.getStartDate());
        return dto;
    }

    public Subscription toEntity(SubscriptionRequestDto dto) {
        return new Subscription(dto.getId(), userService.getById(dto.getUserId()), dto.getDate());
    }

}
