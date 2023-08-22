package com.ead.authuser.dtos;

import com.ead.authuser.validation.FullNameConstrant;
import com.ead.authuser.dtos.interfaces.UserView;
import com.ead.authuser.utils.constants.ConstantsMessage;
import com.ead.authuser.validation.UserNameConstrant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private UUID userId;
    @UserNameConstrant(groups = UserView.RegistrationPost.class,
    message = ConstantsMessage.VALIDATION_USERNAME_CONSTRAINTS)
    @NotBlank(groups = UserView.RegistrationPost.class,
            message = ConstantsMessage.VALIDATION_USERNAME_NOT_NULL_NOR_NOT_EMPTY)
    @Size(min = 6, max = 50, message = ConstantsMessage.VALIDATION_USERNAME_SIZE)
    @JsonView(UserView.RegistrationPost.class)
    private String username;
    @Size(groups = UserView.RegistrationPost.class, max = 50,message = ConstantsMessage.VALIDATION_EMAIL_SIZE)
    @NotBlank(groups = UserView.RegistrationPost.class, message = ConstantsMessage.VALIDATION_EMAIL_NOT_NULL_NOR_NOT_EMPTY)
    @Email(groups = UserView.RegistrationPost.class, message = ConstantsMessage.VALIDATION_EMAIL_IS_NOT_VALID)
    @JsonView(UserView.RegistrationPost.class)
    private String email;
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class},
            message = ConstantsMessage.VALIDATION_PASSWORD_NOT_NULL_NOR_NOT_EMPTY)
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;
    @NotBlank(groups = UserView.PasswordPut.class, message = ConstantsMessage.VALIDATION_OLD_PASSWORD_NOT_NULL_NOR_NOT_EMPTY)
    @JsonView(UserView.PasswordPut.class)
    private String oldPassword;
    @FullNameConstrant(groups = {UserView.RegistrationPost.class, UserView.UserPut.class},
            message = ConstantsMessage.VALIDATION_FULL_NAME_COMPLETE)
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.UserPut.class},
            message = ConstantsMessage.VALIDATION_FULL_NAME_NOT_NULL_NOR_NOT_EMPTY)
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;
    @Size(groups = {UserView.RegistrationPost.class, UserView.UserPut.class},
            min = 8, max = 20, message = ConstantsMessage.VALIDATION_PHONENUMBER_SIZE)
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;
    @Size(groups = {UserView.RegistrationPost.class, UserView.UserPut.class},
            min = 11, max = 14, message = ConstantsMessage.VALIDATION_CPF_SIZE)
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String cpf;
    @NotBlank(groups = UserView.ImagePut.class,
    message = ConstantsMessage.VALIDATION_IMAGE_NOT_NULL_NOR_NOT_EMPTY)
    @JsonView(UserView.ImagePut.class)
    private String imageUrl;

}
