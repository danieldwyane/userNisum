package com.user.repository;

import java.util.List;

import com.user.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
	List<Users> findByEmail(String email);
}
