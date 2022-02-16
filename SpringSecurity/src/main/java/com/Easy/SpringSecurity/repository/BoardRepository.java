package com.Easy.SpringSecurity.repository;

import com.Easy.SpringSecurity.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
