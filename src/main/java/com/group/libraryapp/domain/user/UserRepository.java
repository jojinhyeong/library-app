package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //find 1개의 데이터만 가져옴 by 뒤가 select에서 where문
    //select * from user where name =?
    //User findByName(String name);

    Optional<User> findByName(String name);
    boolean existsByName(String name);

    
}
