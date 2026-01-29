package com.anadev.accountservice.repository;

import com.anadev.accountservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
