package com.jeeva.LibraryManagementSystem.Controller;


import com.jeeva.LibraryManagementSystem.Service.TxnService;
import com.jeeva.LibraryManagementSystem.exception.TxnException;
import com.jeeva.LibraryManagementSystem.request.TxnCreateRequest;
import com.jeeva.LibraryManagementSystem.request.TxnReturnRequest;
import com.jeeva.LibraryManagementSystem.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/txn")
public class TxnController {

    @Autowired
    private TxnService txnService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponse<String>> createTxn(@RequestBody TxnCreateRequest txnCreateRequest) throws TxnException {
            String txnId = txnService.createTxn(txnCreateRequest);

            GenericResponse<String> response = new GenericResponse<>(txnId, "","success", "200" );
            ResponseEntity entity = new ResponseEntity(response, HttpStatus.OK);
            return entity;
    }

    @PutMapping("/return")
    public int returnTxn(@RequestBody TxnReturnRequest txnReturnRequest) throws TxnException{
        return txnService.returnBook(txnReturnRequest);
    }


}
