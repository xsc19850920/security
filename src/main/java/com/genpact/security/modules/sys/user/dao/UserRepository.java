package com.genpact.security.modules.sys.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genpact.security.modules.sys.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findOne(int id);
    User findByUsername(String username);
}
