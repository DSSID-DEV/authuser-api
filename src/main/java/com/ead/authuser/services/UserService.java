package com.ead.authuser.services;

import com.ead.authuser.controllers.common.ConflictChecked;
import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

    Optional<UserModel> findById(UUID userId);

    void delete(UserModel userModel);

    void save(UserModel userModel);

    public ConflictChecked existsConflict(UserDTO userDTO);

    boolean existsByEmail(String email);
}
