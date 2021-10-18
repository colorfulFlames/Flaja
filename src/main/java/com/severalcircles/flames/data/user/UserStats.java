package com.severalcircles.flames.data.user;

import com.severalcircles.flames.data.FlamesData;
//import com.severalcircles.flames.data.global.GlobalData;

import java.util.Properties;

/**
 * Represents Stats data for a FlamesUser
 */
public class UserStats implements FlamesData {
    public static final double powerGrowth = 0.25;
    public static final double resistanceGrowth = 0.5;
    public static final double luckGrowth = 0.05;
    public static final double risingGrowth = 0.1;
    public static final double charismaGrowth = 0.5;

    private int exp = 0;
    private int level = 1;

    private int POW = 1;
    private int RES = 1;
    private int LUCK = 1;
    private int RISE = 1;
    private int CAR = 1;
    public UserStats() {}
    public UserStats(int exp, int level, int POW, int RES, int LUCK, int RISE, int CAR) {
        this.exp = exp;
        this.level = level;
        this.POW = POW;
        this.RES = RES;
        this.LUCK = LUCK;
        this.RISE = RISE;
        this.CAR = CAR;
    }
    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public int getPOW() {
        return POW;
    }

    public int getRES() {
        return RES;
    }

    public int getLUCK() {
        return LUCK;
    }

    public int getRISE() {
        return RISE;
    }

    public int getCAR() {
        return CAR;
    }

    /**
     *
     * @return Properties object which can be written to a file or returned by the Flames API
     */
    public Properties createData() {
        Properties data = new Properties();
        data.put("exp", exp + "");
        data.put("level", level + "");
        data.put("POW", POW + "");
        data.put("RES", RES + "");
        data.put("LUCK", LUCK + "");
        data.put("RISE", RISE + "");
        data.put("CAR", CAR + "");
        return data;
    }
}
