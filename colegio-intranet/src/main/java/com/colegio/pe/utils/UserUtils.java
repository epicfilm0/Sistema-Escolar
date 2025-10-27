package com.colegio.pe.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
public class UserUtils {

    public String generarUsername(String prefix) {
        int randomNum = new Random().nextInt(9000) + 1000;
        return prefix + LocalDate.now().getYear() + randomNum;
    }
}
