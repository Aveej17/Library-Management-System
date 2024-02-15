package com.jeeva.LibraryManagementSystem.request;
import com.jeeva.LibraryManagementSystem.model.Student;
import com.jeeva.LibraryManagementSystem.model.StudentType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNo;
    private String address;

    public Student toStudent() {
        return Student.builder().
                name(this.name).
                email(this.email).
                phoneNo(this.phoneNo).
                address(this.address).
                status(StudentType.ACTIVE).
                build();
    }
}
