package com.jeeva.LibraryManagementSystem.request;


import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TxnReturnRequest {
    private String studentContact;
    private String bookNo;
    private String txnId;

}
