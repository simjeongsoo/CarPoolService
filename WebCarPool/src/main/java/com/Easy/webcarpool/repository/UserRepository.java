package com.Easy.webcarpool.repository;

import com.Easy.webcarpool.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //데이터 조회중에 n+1 문제 발생시 @EntityGraph이용하여 여러개의 쿼리가 발생하지 않고 하나의 join으로 데이터를 조회를 해서 성능을 향상
    @EntityGraph(attributePaths = {"boards", "userCar"})
    List<User> findAll();

//    User findByUsername(String username);

    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying // Modifying queries can only use void or int/Integer as return type!
    @Query("update User set profileImgOrgNm =null ,profileImgSavedNm =null ,profileImgSavedPath =null where username=:username")
    void deleteImageDetailsByUsername(@Param("username") String username); // image 데이터 제거
}
