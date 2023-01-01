package com.Easy.webcarpool.service;

import com.Easy.webcarpool.dto.ProfileResponseDto;
import com.Easy.webcarpool.dto.ProfileUpdateRequestDto;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;

    /**
     * 프로필 조회
     * */
    public ProfileResponseDto getUserProfile(String username) {
        User findByUsername = userRepository.findByUsername(username);
        return new ProfileResponseDto().toDto(findByUsername);

    }

    /**
     * 프로필 업데이트
     * */
    @Transactional
    public void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto) {
        /*User updateProfile = profileUpdateRequestDto.toEntity();
        userRepository.save(updateProfile);*/
        
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
