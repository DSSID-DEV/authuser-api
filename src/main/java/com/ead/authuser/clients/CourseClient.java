package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDTO;
import com.ead.authuser.dtos.ResponsePageDTO;
import com.ead.authuser.services.UtilService;
import com.ead.authuser.utils.constants.ConstantsLogs;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class CourseClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilService utilService;

    @Value("ead.url.api.course")
    private String HOST_COURSE;

    public Page<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable) {

        List<CourseDTO> searchResult =null;

        String url = HOST_COURSE + utilService.createUrlAllCoursesByUser(userId, pageable);
        log.debug(ConstantsLogs.DEBUG_URL, url);
        log.debug(ConstantsLogs.INFO_URL, url);
        try{
            ParameterizedTypeReference<ResponsePageDTO<CourseDTO>> responseType =
                    new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>() {};
            ResponseEntity<ResponsePageDTO<CourseDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null,  responseType);
            searchResult = result.getBody().getContent();

            log.debug(ConstantsLogs.DEBUG_RESPONSE_NUMBER_OF_ELEMENTS, searchResult.size());
        } catch(HttpStatusCodeException e) {
            log.error(ConstantsLogs.ERROR_REQUEST, e);
        }
        log.info(ConstantsLogs.INFO_ENDING_REQUEST, userId);
        return new PageImpl<>(searchResult);
    }


    public void deleteUserInCourse(UUID userId) {
        String url = HOST_COURSE + "/coures/users/"+userId;
        restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
