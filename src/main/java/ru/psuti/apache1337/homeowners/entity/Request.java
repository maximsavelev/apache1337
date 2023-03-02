package ru.psuti.apache1337.homeowners.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.psuti.apache1337.homeowners.service.AddressService;

import javax.annotation.security.DenyAll;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "requests")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "request_date")
    private LocalDateTime date;

    @JoinColumn(name = "address")
    @ManyToOne
    private Address address;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status", nullable = false)
    private Integer status;

    @JoinColumn(name = "client_id")
    @ManyToOne
    private User client;

    @Column(name = "file")
    private String fileName;

}