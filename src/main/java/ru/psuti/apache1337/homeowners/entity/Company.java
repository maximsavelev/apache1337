package ru.psuti.apache1337.homeowners.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "companies")
@Data
@RequiredArgsConstructor
public class Company  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Long phone;
}
