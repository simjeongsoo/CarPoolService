package com.Easy.webcarpool.service.admin;

import com.Easy.webcarpool.dto.ProfileResponseDto;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public ProfileResponseDto findUserInfo(Long id) {
        User user = userRepository.findById(id).get();
        return new ProfileResponseDto().toDto(user);
    }
}
