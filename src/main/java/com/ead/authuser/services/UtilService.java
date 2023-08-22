package com.ead.authuser.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilService {

    public  String createUrlAllCoursesByUser(UUID userId, Pageable pageable);

}
