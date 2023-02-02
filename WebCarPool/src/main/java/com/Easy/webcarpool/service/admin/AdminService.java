package com.Easy.webcarpool.service.admin;

import com.Easy.webcarpool.dto.ProfileResponseDto;
import com.Easy.webcarpool.model.Role;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findUserList() {

        List<User> users = findAllUsers();
        Map<Long, String> roleMap = new HashMap<>();
        List<User> userList = new ArrayList<>();

        for (User value : users) {
            Long id = value.getId();
            User user = userRepository.findById(id).get();

            List<Role> roles = user.getRoles();

            for (Role role : roles) {
                String roleName = role.getName();

                roleMap.put(user.getId(), roleName);
            }
        }

        for (User value : users) {

            Long id = value.getId();
            String roleName = roleMap.get(id);

            if (roleName.equals("ROLE_USER")) {
                User user = userRepository.findById(id).get();
                userList.add(user);
            }
        }

        return userList;
    }

    public List<User> findAdminList() {
        List<User> users = findAllUsers();
        Map<Long, String> roleMap = new HashMap<>();
        List<User> adminList = new ArrayList<>();

        for (User value : users) {
            Long id = value.getId();
            User user = userRepository.findById(id).get();

            List<Role> roles = user.getRoles();

            for (Role role : roles) {
                String roleName = role.getName();

                roleMap.put(user.getId(), roleName);
            }
        }

        for (User value : users) {

            Long id = value.getId();
            String roleName = roleMap.get(id);

            if (roleName.equals("ROLE_ADMIN")) {
                User user = userRepository.findById(id).get();
                adminList.add(user);
            }
        }

        return adminList;
    }

    public ProfileResponseDto findUserInfo(Long id) {
        User user = userRepository.findById(id).get();
        return new ProfileResponseDto().toDto(user);
    }

}
