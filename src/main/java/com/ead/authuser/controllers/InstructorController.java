package com.ead.authuser.controllers;


import com.ead.authuser.dtos.InstructorDTO;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.utils.constants.ConstantsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@RequestMapping("/instroctors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InstructorController {

    @Autowired
    private UserService userService;

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDTO instructorDTO)  {
        Optional<UserModel> optionalUserModel = userService.findById(instructorDTO.getUserId());

        if (!optionalUserModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantsMessage.NOT_FOUND);
        }
        var userModel = optionalUserModel.get();
        setTypeUserInstuctor(userModel);
        userService.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    private void setTypeUserInstuctor(UserModel userModel) {
    userModel.setUserType(UserType.INSTRUCTOR);
    userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    }
}
