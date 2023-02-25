package com.Easy.webcarpool.api.repository;

import com.Easy.webcarpool.api.domain.entity.AccuseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccuseRepository extends JpaRepository<AccuseEntity, Long> {
}
