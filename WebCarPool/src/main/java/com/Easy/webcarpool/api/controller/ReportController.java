package com.Easy.webcarpool.api.controller;

import com.Easy.webcarpool.api.dto.report.AccuseDto;
import com.Easy.webcarpool.api.dto.report.ReportDto;
import com.Easy.webcarpool.api.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/report")
public class ReportController {

    private final ReportService reportService;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/accuse")
    public ResponseEntity<String> accuseUser(@RequestBody AccuseDto dto){
        String message = reportService.accuseUser(dto);
        return ResponseEntity.ok(message);
    }   //사용자 신고글 저장

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/reportAdmin")
    public ResponseEntity<String> reportAdmin(@RequestBody ReportDto dto){
        String message = reportService.reportAdmin(dto);
        return ResponseEntity.ok(message);
    }   //관리자 문의글 저장

}
