package com.jeeva.LibraryManagementSystem.Service;


import com.jeeva.LibraryManagementSystem.model.Operator;
import com.jeeva.LibraryManagementSystem.model.Student;
import com.jeeva.LibraryManagementSystem.model.StudentFilter;
import com.jeeva.LibraryManagementSystem.repository.StudentRepository;
import com.jeeva.LibraryManagementSystem.request.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Student createStudent(StudentCreateRequest studentCreateRequest) {
        List<Student> studentList = studentRepository.findByPhoneNo(studentCreateRequest.getPhoneNo());
        Student studentFromDB = null;
        if(studentList == null || studentList.isEmpty()){
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
                        return studentRepository.findByEmail(value);
                }
            default:
                return new ArrayList<>();
        }
    }
}
