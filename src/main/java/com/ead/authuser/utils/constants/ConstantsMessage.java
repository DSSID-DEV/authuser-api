package com.ead.authuser.utils.constants;

import com.ead.authuser.dtos.CourseDTO;
import org.springframework.data.domain.Page;

public class ConstantsMessage {
    public static final String NOT_FOUND = "User not found!";
    public static final String USER_DELETED = "User deleted successfuly!";
    public static final String CONFLIT_USERNAME = "Error: Username is Already Taken!";
    public static final String CONFLIT_EMAIL = "Error: Email is Already Taken!";
    public static final String CONFLIT_PASSWORD = "Error: Mismatched old password!";
    public static final String PASSWORD_UPDATED = "Password updated successfuly!";

    //VALIDATION MESSAGE

    public static final String VALIDATION_USERNAME_NOT_NULL_NOR_NOT_EMPTY = "The username field cant't be null nor empty!";
    public static final String VALIDATION_USERNAME_SIZE = "The username field must contain between 6 and 50 characters!";
    public static final String VALIDATION_EMAIL_NOT_NULL_NOR_NOT_EMPTY = "The email field cant't be null nor empty!";
    public static final String VALIDATION_EMAIL_SIZE = "The email field must contain between 6 and 50 characters!";
    public static final String VALIDATION_EMAIL_IS_NOT_VALID = "The email informated not is valid!";
    public static final String VALIDATION_PASSWORD_NOT_NULL_NOR_NOT_EMPTY = "The password field cant't be null nor empty!";
    public static final String VALIDATION_OLD_PASSWORD_NOT_NULL_NOR_NOT_EMPTY = "The old password field cant't be null nor empty!";
    public static final String VALIDATION_FULL_NAME_NOT_NULL_NOR_NOT_EMPTY = "The full name field cant't be null nor empty!";
    public static final String VALIDATION_FULL_NAME_COMPLETE = "The full name field must contain at least the first and last name!";
    public static final String VALIDATION_PHONENUMBER_SIZE = "The username field must contain between 8 and 20 characters!";
    public static final String VALIDATION_CPF_SIZE = "The CPF field must contain between 11 and 14 characters!";
    public static final String VALIDATION_IMAGE_NOT_NULL_NOR_NOT_EMPTY = "The image field cant't be null nor empty!";
    public static final String VALIDATION_USERNAME_CONSTRAINTS = "The username field cannot be null, it cannot have a space or an empty string!";
    public static final String POST_REGISTER_USER_USER_DTO_RECEIVED = "POST registerUser userDTO received {} ";
    public static final String LOG_DEBUG_POST_REGISTER_USERMODEL_SAVED = "POST User saved successfuly userId {} ";
    public static final String LOG_DEBUG_USED_SAVED_SUCCESSFULY = "User saved successfuly userId {} ";
    public static final String LOG_WARN_USERNAME_IS_ALREADY_TAKEN = "Username {} is Already Taken ";
    public static final String LOG_WARN_EMAIL_IS_ALREADY_TAKEN = "Email {} is Already Taken ";
    public static final String LOG_DEBUG_PUT_UPDATEUSER ="PUT updateUser userModel received {} ";
    public static final String LOG_DEBUG_PUT_USER_UDPATED = "PUT updateUser User updated UserId {} ";
    public static final String LOG_WARN_MISTATCHED_OLD_PASSWORD_USERID = "Mistatched old password userId {} ";
    public static final String LOG_DEBUG_DELETE_DELETEUSER = "DELETE deleteUser userI received {} ";
    public static final String LOG_DEBUG_DELETED_USER = "DEBUG User deleted successfuly userId {} ";
    public static final String LOG_USER_DELETED = "User deleted successfuly userId {} ";
    public static final String LOG_INFO_USED_SAVED_SUCCESSFULY = "User updated successfuly userId {} ";
    public static final String LOG_DEBUG_PASSWORD_SAVED = "DEBUG Password's user updated successfuly userId {} ";
    public static final String LOG_INFO_PASSWORD_SAVED = "Password's user updated successfuly userId {} ";
    public static final String LOG_DEBUG_IMAGE_SAVED = "DEBUG Image's user updated successfuly userId {} ";
    public static final String LOG_INFO_IMAGE_SAVED = "Image's user updated successfuly userId {} ";
    public static final String SUBSCRITON_ALREADY_EXISTS = "Error: subscrition already exists!";
    public static final String USERCOURSE_NOT_FOUND = "UserCourse not found!";
    public static final String USERCOURSE_DELETED_SUCESSFULY = "UserCourse deleted sucessfuly!";
}
