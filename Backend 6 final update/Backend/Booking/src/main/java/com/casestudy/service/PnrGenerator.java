package com.casestudy.service;


public class PnrGenerator {
    public static String generatePNR() {
        String prefix = "PNR";
        int randomNum = (int)(Math.random() * 900000 + 100000); // 6-digit random
        return prefix + randomNum;
    }
}