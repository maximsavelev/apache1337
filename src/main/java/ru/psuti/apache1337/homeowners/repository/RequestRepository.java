package ru.psuti.apache1337.homeowners.repository;


import ru.psuti.apache1337.homeowners.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    List<Request> findAll();

}

