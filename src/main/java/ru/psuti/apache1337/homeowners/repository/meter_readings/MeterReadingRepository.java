package ru.psuti.apache1337.homeowners.repository.meter_readings;

import ru.psuti.apache1337.homeowners.entity.Meter;
import ru.psuti.apache1337.homeowners.entity.MeterReading;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MeterReadingRepository extends CrudRepository<MeterReading, Long> {

    @Query("SELECT mr FROM MeterReading mr WHERE mr.meter=:meter AND mr.dateTime="
            + "(SELECT max(mr.dateTime) FROM MeterReading mr WHERE mr.meter=:meter)")
    MeterReading findLatestMeterReadingByMeter(@Param("meter") Meter meter);
}