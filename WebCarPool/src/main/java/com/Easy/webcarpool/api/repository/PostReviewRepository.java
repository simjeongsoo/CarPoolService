package com.Easy.webcarpool.api.repository;

import com.Easy.webcarpool.api.domain.entity.PostReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReviewRepository extends JpaRepository<PostReviewEntity, Long> {
}
