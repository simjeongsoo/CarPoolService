package com.Easy.webcarpool.api.repository;

import com.Easy.webcarpool.api.domain.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
}
