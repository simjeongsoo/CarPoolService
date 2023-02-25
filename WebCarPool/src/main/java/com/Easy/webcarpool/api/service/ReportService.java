package com.Easy.webcarpool.api.service;

import com.Easy.webcarpool.api.dto.report.AccuseDto;
import com.Easy.webcarpool.api.dto.report.ReportDto;
import com.Easy.webcarpool.api.repository.AccuseRepository;
import com.Easy.webcarpool.api.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final AccuseRepository accuseRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public String accuseUser(AccuseDto dto){
        accuseRepository.save(dto.toEntity());
        return "success";
    }

    @Transactional
    public String reportAdmin(ReportDto dto){
        reportRepository.save(dto.toEntity());
        return "success";
    }




}
