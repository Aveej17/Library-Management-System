package com.jeeva.LibraryManagementSystem.response;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GenericResponse<T> {

     private T data;

     private String error;

     private String message; // Success/ failure

    private String code;

}
