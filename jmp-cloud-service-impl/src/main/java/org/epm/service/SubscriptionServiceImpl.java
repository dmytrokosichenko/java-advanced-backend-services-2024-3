package org.epm.service;

import org.epm.Subscription;
import org.epm.SubscriptionRequestDto;
import org.epm.SubscriptionResponseDto;
import org.epm.SubscriptionService;
import org.epm.User;
import org.epm.exception.ServiceException;
import org.epm.mapper.SubscriptionMapper;
import org.epm.repository.SubscriptionRepository;
import org.epm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UserRepository userRepository, SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.subscriptionMapper = subscriptionMapper;
    }

    public SubscriptionResponseDto createSubscription(SubscriptionRequestDto subscriptionRequestDto) {
        return Optional.of(subscriptionRequestDto)
                .map(this::prepareSubscriptionEntity)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toDto)
                .orElseThrow(() -> new ServiceException("Error cann't create subscription ", "500"));
    }

    public SubscriptionResponseDto updateSubscription(Long id, SubscriptionRequestDto subscriptionRequestDto) {
        return Optional.of(new SubscriptionRequestDto(id, subscriptionRequestDto.getUserId()))
                .map(this::updateSubscriptionEntity)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toDto)
                .orElseThrow(() -> new ServiceException("Not found", "404"));
    }

    public SubscriptionResponseDto getSubscription(Long id) {
        return subscriptionRepository.findById(id)
                .map(subscriptionMapper::toDto)
                .orElseThrow(() -> new ServiceException("Not found", "404"));
    }

    public List<SubscriptionResponseDto> getAllSubscription() {
        return subscriptionRepository.findAll().stream()
                .map(subscriptionMapper::toDto)
                .toList();
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.findById(id)
                .ifPresentOrElse(subscriptionRepository::delete,
                        () -> {
                            throw new ServiceException("Not found", "404");
                        });
    }

    private Subscription prepareSubscriptionEntity(SubscriptionRequestDto subscriptionRequestDto) {
        User user = findUserForSubscription(subscriptionRequestDto);
        return prepareSubscriptionWithUser(subscriptionRequestDto, user);
    }

    private Subscription updateSubscriptionEntity(SubscriptionRequestDto subscriptionRequestDto) {
        Subscription subscription = findById(subscriptionRequestDto.getId());
        User user = findUserForSubscription(subscriptionRequestDto);
        subscription.setUser(user);
        return subscription;
    }

    private Subscription prepareSubscriptionWithUser(SubscriptionRequestDto subscriptionRequestDto, User user) {
        Subscription subscription = subscriptionMapper.toEntity(subscriptionRequestDto);
        subscription.setUser(user);
        return subscription;
    }

    private User findUserForSubscription(SubscriptionRequestDto subscriptionRequestDto) {
        return userRepository.findById(subscriptionRequestDto.getId())
                .orElseThrow(() -> new ServiceException("Not found", "404"));
    }

    private Subscription findById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Not found", "404"));
    }
}