package pl.training.cloud.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access;

@ApiModel(value = "User")
@Data
public class UserDto {

    private String firstName;
    private String lastName;
    @JsonProperty(access = Access.READ_ONLY)
    private String departmentName;
    @JsonIgnore
    private Long departmentId;
    private String login;
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = Access.READ_ONLY)
    private Set<AuthorityDto> authorities;

}
