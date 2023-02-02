package com.Easy.webcarpool.service.admin;

import com.Easy.webcarpool.dto.ProfileResponseDto;
import com.Easy.webcarpool.model.Role;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.repository.RoleRepository;
import com.Easy.webcarpool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
    * 전체 사용자 조회
    * */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 'ROLE_USER' 조회
     * */
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

    /**
     * 'ROLE_ADMIN' 조회
     * */
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

    /**
     * 사용자 정보 조회
     * */
    public ProfileResponseDto findUserInfo(Long id) {
        User user = userRepository.findById(id).get();
        return new ProfileResponseDto().toDto(user);
    }

    /**
     * 관리자 추가
     * */
    @Transactional
    public Boolean addAdmin(Long id) {
        boolean result = false;
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            Role role = new Role();
            role.setId(2L);
            result = user.getRoles().add(role);
        }
        return result;
    }

    /**
     * 관리자 삭제
     * */
    @Transactional
    public Boolean removeAdmin(Long id) {
        boolean result = false;
        logger.debug("id : {}", id);

        User user = userRepository.findById(id).orElse(null);
        Role role = roleRepository.findById(2L).orElse(null);

        if (user != null && role != null) {
            boolean removeUser = user.getRoles().remove(role);
            boolean removeRole = role.getUsers().remove(user);
            userRepository.save(user);
            roleRepository.save(role);
            if (removeRole && removeUser) {
                result = true;
            }
        }

        return result;
    }

}
