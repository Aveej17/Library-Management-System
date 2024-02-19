package com.jeeva.LibraryManagementSystem.Service;

import com.jeeva.LibraryManagementSystem.exception.TxnException;
import com.jeeva.LibraryManagementSystem.model.*;
import com.jeeva.LibraryManagementSystem.repository.TxnRepository;
import com.jeeva.LibraryManagementSystem.request.TxnCreateRequest;
import com.jeeva.LibraryManagementSystem.request.TxnReturnRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class TxnService {

    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    @Value("${student.valid.days}")
    private String validUpTo;

    @Value("${student.delay.fine}")
    private Integer FinePerDays;

    public Student filterStudent(StudentFilter studentFilter, Operator operator, String value) throws TxnException {
        List<Student> studentList = studentService.filter(studentFilter, operator, value);

        if(studentList == null || studentList.isEmpty()){
            throw new TxnException("Student does not belong to My Library");
        }
        Student studentFromLib = studentList.get(0);
        return studentFromLib;
    }

    private Book filterBook(FilterType type, Operator operator, String value) throws TxnException {
        List<Book> bookList = bookService.filter(type, operator, value);

        if(bookList == null || bookList.isEmpty()){
            throw new TxnException("Book is not belongs to my Lib");
        }
        Book bookFromLib = bookList.get(0);

        return bookFromLib;
    }

    public int calculateSettlementAmount(Txn txn){
        long issueTime = txn.getCreatedOn().getTime();
        long returnTime = System.currentTimeMillis();

        long timeDiff = returnTime-issueTime;

        int daysPassed = (int) TimeUnit.DAYS.convert(timeDiff,TimeUnit.MILLISECONDS);

        if(daysPassed>Integer.valueOf(validUpTo)){
            int fineAmount = (daysPassed- Integer.valueOf(validUpTo))*FinePerDays;
            return txn.getPaidAmount() - fineAmount;
        }
        else {
            return txn.getPaidAmount();
        }
    }

    @Transactional(rollbackFor = {TxnException.class})
    public String createTxn(TxnCreateRequest txnCreateRequest) throws TxnException {

        Student studentFromLib = filterStudent(StudentFilter.EMAIL, Operator.EQUALS, txnCreateRequest.getStudentContact());


        Book bookFromLib =  filterBook(FilterType.BOOK_NO, Operator.EQUALS, txnCreateRequest.getBookNo());
        if(bookFromLib.getStudent()!= null){
            throw new TxnException("Book is already assigned to someone else");
        }

        String txnid = UUID.randomUUID().toString();
        Txn txn = Txn.builder().
                student(studentFromLib).
                book(bookFromLib).
                txnId(txnid).
                paidAmount(txnCreateRequest.getAmount()).
                status(TxnStatus.ISSUED).
                build();

        txn = txnRepository.save(txn);
        bookFromLib.setStudent(studentFromLib);
        bookService.saveUpdate(bookFromLib);
        return txn.getTxnId();
    }


    @Transactional(rollbackFor = {TxnException.class})
    public int returnBook(TxnReturnRequest txnReturnRequest) throws TxnException {
        Student studentFromDB = filterStudent(StudentFilter.EMAIL, Operator.EQUALS, txnReturnRequest.getStudentContact());
        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operator.EQUALS, txnReturnRequest.getBookNo());

        if(bookFromLib.getStudent() != null && bookFromLib.getStudent().getEmail().equals(studentFromDB.getEmail())){
            Txn txnFromDB = txnRepository.findByTxnId(txnReturnRequest.getTxnId());

            if(txnFromDB == null){
                throw new TxnException("No txn has been found for this txnId");
            }
            int amount = calculateSettlementAmount(txnFromDB);
            if(amount == txnFromDB.getPaidAmount()){
                txnFromDB.setStatus(TxnStatus.RETURNED);
            }
            else {
                txnFromDB.setStatus(TxnStatus.FINED);
            }

            txnFromDB.setPaidAmount(amount);

            // update the book, marking student null

            bookFromLib.setStudent(null);
            bookService.saveUpdate(bookFromLib);
            txnRepository.save(txnFromDB);

            return amount;
        }
        else{
            throw new TxnException("Book is either not assigned to anyone , or may be assigned to someone else !");
        }

    }
}
