package com.Easy.webcarpool.controller;

import com.Easy.webcarpool.model.Role;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/home")
    public String admin(){
        return "admin/home";
    }

    @GetMapping("/management")
    public String adminManagement(Model model){

        List<User> users = adminService.findUsers();
        model.addAttribute("users", users);

        for (User user : users) {
            List<Role> roles = user.getRoles();
            model.addAttribute("roles", roles);
        }


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
