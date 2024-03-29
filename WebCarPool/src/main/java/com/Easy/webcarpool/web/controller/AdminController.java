package com.Easy.webcarpool.web.controller;

import com.Easy.webcarpool.web.dto.ProfileResponseDto;
import com.Easy.webcarpool.web.domain.User;
import com.Easy.webcarpool.web.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/home")
    public String admin(){
        return "admin/home";
    }

    @GetMapping("/management")
    public String adminManagement(Model model){

        List<User> userList = adminService.findUserList();
        List<User> adminList = adminService.findAdminList();

        model.addAttribute("userList", userList);
        model.addAttribute("adminList", adminList);

        return "admin/management";
    }

    @ResponseBody
    @GetMapping("/getUserInfo/{id}")
    public ProfileResponseDto getUserInfo(@PathVariable Long id) {
        return adminService.findUserInfo(id);
    }


    @PostMapping("/add/{id}")
    public ResponseEntity<Boolean> addRole(@PathVariable Long id) {
        Boolean result = adminService.addAdmin(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<Boolean> removeRole(@PathVariable Long id) {
        Boolean result = adminService.removeAdmin(id);
        return ResponseEntity.ok(result);
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
