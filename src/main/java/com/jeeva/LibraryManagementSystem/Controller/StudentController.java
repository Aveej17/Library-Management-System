package com.jeeva.LibraryManagementSystem.Controller;


import com.jeeva.LibraryManagementSystem.Service.StudentService;
import com.jeeva.LibraryManagementSystem.exception.ParameterMissingException;
import com.jeeva.LibraryManagementSystem.model.Operator;
import com.jeeva.LibraryManagementSystem.model.Student;
import com.jeeva.LibraryManagementSystem.model.StudentFilter;
import com.jeeva.LibraryManagementSystem.request.StudentCreateRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;


    @PostMapping("/create")
    public Student createStudent(@RequestBody  @Valid StudentCreateRequest studentCreateRequest) throws ParameterMissingException {

        if(studentCreateRequest.getEmail() == null || StringUtils.isEmpty(studentCreateRequest.getEmail())){
            throw new ParameterMissingException("Student email cannot be null");
        }
        return studentService.createStudent(studentCreateRequest);
    }

    @GetMapping("/filter")

    public List<Student> filter(@RequestParam("filterBy") StudentFilter studentFilter,
                                @RequestParam("operator") Operator operator,
                                @RequestParam("value") String value){

        return studentService.filter(studentFilter, operator, value);

    }


}
