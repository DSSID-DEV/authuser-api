package com.ead.authuser.services.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.controllers.common.ConflictChecked;
import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import com.ead.authuser.utils.constants.ConstantsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private CourseClient courseClient;

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    @Override
    public void delete(UserModel userModel) {
        boolean deleteUserCourseInCourse = false;
        List<UserCourseModel> userCourseModels = userCourseRepository.findAllUserCourseIntoUser(userModel.getUserId());
        if (!userCourseModels.isEmpty()){
            userCourseRepository.deleteAll(userCourseModels);
            deleteUserCourseInCourse = true;
        }
        userRepository.delete(userModel);
        if (deleteUserCourseInCourse) {
            courseClient.deleteUserInCourse(userModel.getUserId());
        }
    }

    @Override
    public void save(UserModel userModel) {
        userRepository.save(userModel);
    }

    @Override
    public ConflictChecked existsConflict(UserDTO userDTO) {
        ConflictChecked conflictChecked = new ConflictChecked();
        conflictChecked.setHas(false);

        if(userRepository.existsByUsername(userDTO.getUsername())) {
            conflictChecked.setConflict(ConstantsMessage.CONFLIT_USERNAME);
            conflictChecked.setMsgLog(ConstantsMessage.LOG_WARN_USERNAME_IS_ALREADY_TAKEN.replace("{}", userDTO.getUsername()));
            conflictChecked.setHas(true);
            return conflictChecked;
        }

        if(userRepository.existsByEmail(userDTO.getEmail())) {
            conflictChecked.setConflict(ConstantsMessage.CONFLIT_EMAIL);
            conflictChecked.setMsgLog(ConstantsMessage.LOG_WARN_EMAIL_IS_ALREADY_TAKEN.replace("{}", userDTO.getEmail()));
            conflictChecked.setHas(true);
            return conflictChecked;
        }
        return conflictChecked;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
