package ru.psuti.apache1337.homeowners.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    private static final Integer EXPIRE_TIME_MINUTES = 1;

    private final int PASSWORD_MIN_VALUE = 10000;
    private  final int PASSWORD_MAX_VALUE = 90000;
    private final LoadingCache<String, Integer> otpCache;

    private final Random random;
    public OtpService() {
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_TIME_MINUTES, TimeUnit.MINUTES).build(new CacheLoader<>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
        random = new Random();
    }

    public int generateOTP(String key) {
        int otp = random.nextInt(PASSWORD_MAX_VALUE) + PASSWORD_MIN_VALUE;
        otpCache.put(key, otp);
        return otp;
    }

    public int getOtp(String key) {
        try {
            return otpCache.get(key);
        } catch (ExecutionException e) {
            return 0;
        }
    }
}
