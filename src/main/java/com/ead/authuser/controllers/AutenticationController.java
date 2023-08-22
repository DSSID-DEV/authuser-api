package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.dtos.interfaces.UserView;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.services.UserService;
import com.ead.authuser.utils.constants.ConstantsMessage;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AutenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(
            @RequestBody @Validated(UserView.RegistrationPost.class)
            @JsonView(UserView.RegistrationPost.class)
            UserDTO userDTO) {
        log.debug(ConstantsMessage.POST_REGISTER_USER_USER_DTO_RECEIVED, userDTO.toString());
        var conflictChecker = userService.existsConflict(userDTO);

        if(conflictChecker.isHas()) {
            log.warn(conflictChecker.getMsgLog());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictChecker.getConflict());
        }

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        preencherPropriedades(userModel);
        userService.save(userModel);
        log.debug(ConstantsMessage.LOG_DEBUG_POST_REGISTER_USERMODEL_SAVED, userModel.getUserId());
        log.info(ConstantsMessage.LOG_DEBUG_USED_SAVED_SUCCESSFULY, userModel.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    private void preencherPropriedades(UserModel userModel) {
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.ADMIN);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    }
}
