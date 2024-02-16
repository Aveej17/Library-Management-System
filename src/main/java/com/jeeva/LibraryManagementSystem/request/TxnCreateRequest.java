package com.jeeva.LibraryManagementSystem.request;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnCreateRequest {


    private String bookNo;
    private Integer amount;
}
