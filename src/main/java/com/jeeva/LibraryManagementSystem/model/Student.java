package com.jeeva.LibraryManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;




@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 30)
    private String name;
    @Column(length = 30, unique = true)
    private String email;
    @Column(length = 15, unique = true, nullable = false)
    private String phoneNo;
    private String address;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
    @Enumerated(value = EnumType.STRING) // By default Ordinal

    private StudentType status;

    @OneToMany(mappedBy = "student")
    private List<Book> list;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Txn> txnList;
}