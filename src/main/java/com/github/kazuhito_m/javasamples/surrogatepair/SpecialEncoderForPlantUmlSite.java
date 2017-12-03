package com.github.kazuhito_m.javasamples.surrogatepair;

public class SpecialEncoderForPlantUmlSite {

    public String encode64(String target) {
        String r = "";
        for (int i = 0; i < target.length(); i += 3) {
            if (i + 2 == target.length()) {
                r += append3bytes(target.charAt(i), target.charAt(i + 1), 0);
            } else if (i + 1 == target.length()) {
                r += append3bytes(target.charAt(i), 0, 0);
            } else {
                r += append3bytes(target.charAt(i), target.charAt(i + 1), target.charAt(i + 2));
            }
        }
        return r;
    }

    private String append3bytes(int b1, int b2, int b3) {
        int c1 = b1 >> 2;
        int c2 = ((b1 & 0x3) << 4) | (b2 >> 4);
        int c3 = ((b2 & 0xF) << 2) | (b3 >> 6);
        int c4 = b3 & 0x3F;
        String r = "";
        r += encode6bit(c1 & 0x3F);
        r += encode6bit(c2 & 0x3F);
        r += encode6bit(c3 & 0x3F);
        r += encode6bit(c4 & 0x3F);
        return r;
    }

    private String encode6bit(int b) {
        if (b < 10) {
            return charOf(48 + b);
        }
        b -= 10;
        if (b < 26) {
            return charOf(65 + b);
        }
        b -= 26;
        if (b < 26) {
            return charOf(97 + b);
        }
        b -= 26;
        if (b == 0) {
            return "-";
        }
        if (b == 1) {
            return "_";
        }
        return "?";
    }

    private String charOf(int code) {
        return Character.toString((char) code);
    }

}