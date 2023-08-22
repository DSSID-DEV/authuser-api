package com.ead.authuser.repositories.queries;

public class USerCourseQueries {
    public static final String FIND_ALL_USERCOURSE_INTO_USER = "select * from tb_users_courses where user_user_id = :userId";
}
