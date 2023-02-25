package com.Easy.webcarpool.api.repository;

import com.Easy.webcarpool.api.domain.entity.ReservedPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservedPostRepository extends JpaRepository<ReservedPostEntity, Long> {

    @Query("SELECT p FROM ReservedPostEntity p WHERE p.driver = :email OR p.passenger = :email")
    public List<ReservedPostEntity> findReservedPostByEmail(@Param("email") String email);

    @Query("SELECT p FROM ReservedPostEntity p WHERE p.postId = :postId AND p.postType = :postType")
    public Optional<ReservedPostEntity> findReservedPostByIdAndType(@Param("postId") Long postId, @Param("postType") String postType);

}
