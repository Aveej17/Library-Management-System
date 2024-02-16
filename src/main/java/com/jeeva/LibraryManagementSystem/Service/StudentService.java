package com.jeeva.LibraryManagementSystem.Service;


import com.jeeva.LibraryManagementSystem.model.Operator;
import com.jeeva.LibraryManagementSystem.model.Student;
import com.jeeva.LibraryManagementSystem.model.StudentFilter;
import com.jeeva.LibraryManagementSystem.repository.StudentRepository;
import com.jeeva.LibraryManagementSystem.request.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService implements UserDetailsService {
    @Autowired
    StudentRepository studentRepository;
    @Value("${student.authority}")
    private String studentAuthority;

    public Student createStudent(StudentCreateRequest studentCreateRequest) {
        List<Student> studentList = studentRepository.findByPhoneNo(studentCreateRequest.getPhoneNo());
        Student studentFromDB = null;
        if(studentList == null || studentList.isEmpty()){
            studentCreateRequest.setAuthority(studentAuthority);
            studentFromDB = studentRepository.save(studentCreateRequest.toStudent());
            return studentFromDB;
        }
        studentFromDB = studentList.get(0);
        return studentFromDB;
    }


    public List<Student> filter(StudentFilter studentFilter, Operator operator, String value) {
        switch (operator){
            case EQUALS :
                switch (studentFilter){
                    case NAME:
                        return studentRepository.findByName(value);
                    case ID:
                        ArrayList<Student> l = new ArrayList<>();
                        l.add(studentRepository.findById(Integer.valueOf(value)).get());
                        return l;
                    case EMAIL:
                        ArrayList<Student> li = new ArrayList<>();
                        li.add(studentRepository.findByEmail(value));
                        return li;
                }
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return studentRepository.findByEmail(email);
    }
}
