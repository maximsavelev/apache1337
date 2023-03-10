package ru.psuti.apache1337.homeowners;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.psuti.apache1337.homeowners.dto.MeterReadingDto;
import ru.psuti.apache1337.homeowners.entity.Meter;
import ru.psuti.apache1337.homeowners.entity.MeterReading;
import ru.psuti.apache1337.homeowners.util.MeterReadingMapper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
