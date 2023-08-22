package com.ead.authuser.controllers;


import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDTO;
import com.ead.authuser.dtos.UserCourseDTO;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.UserService;
import com.ead.authuser.utils.constants.ConstantsLogs;
import com.ead.authuser.utils.constants.ConstantsMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCourseService userCourseService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(
            @PageableDefault(page = 0, size = 10, sort = "couserId", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(value = "userId") UUID userId) {
        Page<CourseDTO> userCourses = courseClient.getAllCoursesByUser(userId, pageable);
        if (userCourses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(userCourses);
    }

    @PostMapping("/users/{userId}/courses/subscrition")
    public ResponseEntity<Object> saveSubscritionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                              @RequestBody @Valid UserCourseDTO userCourseDTO) {

        Optional<UserModel> optionalUserModel = userService.findById(userId);

        if (!optionalUserModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantsMessage.NOT_FOUND);
        }

        if (userCourseService.existsByUserAndCourseId(optionalUserModel.get(), userCourseDTO.getCourseId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ConstantsMessage.SUBSCRITON_ALREADY_EXISTS);
        }

        UserCourseModel userCourseModel = userCourseService.save(optionalUserModel.get()
                .convertToUserCourseModel(userCourseDTO.getCourseId()));
        log.debug(ConstantsLogs.DEBUG_USER_COURSE_SAVED, userCourseModel.getCourseId());
        log.info(ConstantsLogs.INFO_USER_COURSE_SAVED, userCourseModel.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userCourseModel);
    }

    @DeleteMapping("/users/courses/{courseId}")
    public ResponseEntity<Object> deleteUserCourseByCourse(@PathVariable(value = "courseId") UUID courseId) {
        if (!userCourseService.existsByCourseId(courseId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantsMessage.USERCOURSE_NOT_FOUND);
        }
        userCourseService.deleteUserCourseByCourse(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(ConstantsMessage.USERCOURSE_DELETED_SUCESSFULY);
    }

}