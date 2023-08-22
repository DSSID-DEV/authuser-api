package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.dtos.interfaces.UserView;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.spacifications.SpecificationTemplate;
import com.ead.authuser.utils.constants.ConstantsMessage;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(
            @PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserView.UserPut.class) @JsonView(UserView.UserPut.class)
            UserDTO userDTO) {
        log.debug(ConstantsMessage.LOG_DEBUG_PUT_UPDATEUSER, userDTO.toString());
        Optional<UserModel> optionalUserModel = userService.findById(userId);

        if (!optionalUserModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantsMessage.NOT_FOUND);
        }

        var userModel = optionalUserModel.get();
        preencherPropriedades(userModel, userDTO);
        userService.save(userModel);
        log.debug(ConstantsMessage.LOG_DEBUG_PUT_USER_UDPATED, userModel.getUserId());
        log.info(ConstantsMessage.LOG_INFO_USED_SAVED_SUCCESSFULY, userModel.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(
            @PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserView.PasswordPut.class)
            @JsonView(UserView.PasswordPut.class)
            UserDTO userDTO) {
        Optional<UserModel> optionalUserModel = userService.findById(userId);

        if (!optionalUserModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantsMessage.NOT_FOUND);
        }

        if (optionalUserModel.get().getPassword().equals(userDTO.getPassword())) {
            log.warn(ConstantsMessage.LOG_WARN_MISTATCHED_OLD_PASSWORD_USERID, userId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ConstantsMessage.CONFLIT_PASSWORD);
        }
        var userModel = optionalUserModel.get();
        userModel.setPassword(userDTO.getPassword());
        userModel.setLastUpdateDate(LocalDateTime.now((ZoneId.of("UTC"))));
        userService.save(userModel);
        log.debug(ConstantsMessage.LOG_DEBUG_PASSWORD_SAVED, userModel.getUserId());
        log.info(ConstantsMessage.LOG_INFO_PASSWORD_SAVED, userModel.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(ConstantsMessage.PASSWORD_UPDATED);
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(
            @PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserView.ImagePut.class)
            @JsonView(UserView.ImagePut.class)
            UserDTO userDTO) {
        Optional<UserModel> optionalUserModel = userService.findById(userId);

        if (!optionalUserModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantsMessage.NOT_FOUND);
        }

        var userModel = optionalUserModel.get();
        userModel.setImageUrl(userDTO.getImageUrl());
        userModel.setLastUpdateDate(LocalDateTime.now((ZoneId.of("UTC"))));
        userService.save(userModel);
        log.debug(ConstantsMessage.LOG_DEBUG_IMAGE_SAVED, userModel.getUserId());
        log.info(ConstantsMessage.LOG_INFO_IMAGE_SAVED, userModel.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(
            SpecificationTemplate.UserSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Page<UserModel> userModelPage = userService.findAll(spec, pageable);
        if(userModelPage.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        for (UserModel user : userModelPage.toList()) {
            user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        return userModelOptional
                .<ResponseEntity<Object>>map(
                        userModel ->
                                ResponseEntity.status(HttpStatus.OK)
                                        .body(userModel)).orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(ConstantsMessage.NOT_FOUND));
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        log.debug(ConstantsMessage.LOG_DEBUG_DELETE_DELETEUSER, userId);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantsMessage.NOT_FOUND);
        }
        userService.delete(userModelOptional.get());
        log.debug(ConstantsMessage.LOG_DEBUG_DELETED_USER, userId);
        log.info(ConstantsMessage.LOG_USER_DELETED, userId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantsMessage.USER_DELETED);
    }

    private void preencherPropriedades(UserModel userModel, UserDTO userDTO) {
        userModel.setFullName(checkedStringData(userModel.getFullName(), userDTO.getFullName()));
        userModel.setCpf(checkedStringData(userModel.getCpf(), userDTO.getCpf()));
        userModel.setPhoneNumber(checkedStringData(userModel.getPhoneNumber(), userDTO.getPhoneNumber()));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    }

    private String checkedStringData(String oldData, String newData) {
        return newData.isBlank() ? oldData : newData;
    }

}
