package org.epm.service;

import org.epm.User;
import org.epm.UserRequestDto;
import org.epm.UserResponseDto;
import org.epm.UserService;
import org.epm.exception.ServiceException;
import org.epm.mapper.UserMapper;
import org.epm.repository.SubscriptionRepository;
import org.epm.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        return Optional.of(userRequestDto)
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException("User create exception", "500"));
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        return userRepository.findById(id)
                .map(existingUser -> updateUserWithDto(existingUser, userRequestDto))
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException("Not found exception", "404"));
    }

    public UserResponseDto getUser(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException("Not found exception", "404"));
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public void deleteUser(Long id) {
        userRepository.findById(id)
                .map(user -> {
                    subscriptionRepository.deleteAllByUser(user);
                    return user;
                })
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> {
                            throw new ServiceException("Not found exception", "404");
                        }
                );
    }

    private User updateUserWithDto(User user, UserRequestDto userRequestDto) {
        userMapper.updateEntityFromDto(userRequestDto, user);
        return user;
    }
}