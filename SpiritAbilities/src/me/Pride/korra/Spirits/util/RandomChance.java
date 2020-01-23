package me.Pride.korra.Spirits.util;

import java.util.Random;

public class RandomChance {

    private double percent;

    // Grabbed from Numin's snippet code
    
    public RandomChance(double percent) {
        this.percent = percent * 10;
    }

    public boolean chanceReached() {
        int randomInt = new Random().nextInt(1000);
        return randomInt <= percent;
    }

}
