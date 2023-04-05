package com.alternaonboarding.app.utils;

import java.text.DecimalFormat;
import java.util.Random;

public class CommonFunctions {
    public static String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}
