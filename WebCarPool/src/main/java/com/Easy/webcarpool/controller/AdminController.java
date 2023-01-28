package com.Easy.webcarpool.controller;

import com.Easy.webcarpool.model.Role;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.repository.UserRepository;
import com.Easy.webcarpool.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;

    private final UserRepository userRepository;

    @GetMapping("/home")
    public String admin(){
        return "admin/home";
    }

    @GetMapping("/management")
    public String adminManagement(Model model){

        List<User> users = adminService.findUsers();


        Map<Long, String> roleMap = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            Long id = users.get(i).getId();
            User user = userRepository.findById(id).get();

            List<Role> roles = user.getRoles();
            logger.debug("user 정보 : {}", user.getId());

            for (int j = 0; j < roles.size(); j++) {
                String roleName = roles.get(j).getName();
                logger.debug("role 정보 : {}", roleName);

                roleMap.put(user.getId(), roleName);
            }
        }

        List<User> userList = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {

            Long id = users.get(i).getId();
            String roleName = roleMap.get(id);

            if (roleName.equals("ROLE_USER")) {
                User user = userRepository.findById(id).get();
                userList.add(user);


            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("users", users);
        model.addAttribute("roleMap", roleMap);




        return "admin/management";
    }
    @GetMapping("/usage")
    public String adminUsage(){
        return "admin/usage";
    }

    @GetMapping("/chat")
    public String adminChat(){
        return "admin/chat";
    }

}
