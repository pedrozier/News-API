package com.standard.newsAPI.security.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.standard.newsAPI.security.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    UserModel findByUsername(String username);
}
