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

    @Query("SELECT mr FROM MeterReading mr LEFT JOIN Meter m ON mr.meter.id = m.id WHERE m.user.id = :userId AND"
            + "(:type IS NULL OR mr.meter.service.name=:type) AND "
            + "(CAST(:startDate AS date) IS NULL OR mr.dateTime >= :startDate) AND "
            + "(CAST(:endDate AS date) IS NULL OR mr.dateTime <= :endDate)")
    Page<MeterReading> findAllMetersReadingByUserId(@Param("userId") Long userId, @Param("type") String type,
                                                    @Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate,
                                                    Pageable pageable);

    @Query("SELECT COUNT(cv) FROM MeterReading cv LEFT JOIN Meter ct ON cv.meter.id = ct.id WHERE ct.user.id = :userId AND"
            + "(:type IS NULL OR cv.meter.service.name=:type) AND "
            + "(CAST(:startDate AS date) IS NULL OR cv.dateTime >= :startDate) AND "
            + "(CAST(:endDate AS date) IS NULL OR cv.dateTime <= :endDate)")
    int findAmountAllMeterReadingByUserId(@Param("userId") Long userId, @Param("type") String type,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);
}