package com.twilio.ivrrecording.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Arrays;
import java.util.List;

public class Config {

    private static final Dotenv dotenv = Dotenv.load();

    public static Dotenv getDotenv() {
        return dotenv;
    }
}
