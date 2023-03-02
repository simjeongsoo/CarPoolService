package com.Easy.webcarpool.web.repository;

import com.Easy.webcarpool.web.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
