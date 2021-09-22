/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.generator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 *
 * @author Theodoric
 */
public class RandomNumberGenerator
{

    private final Random random;

    private final double min;
    private final double max;

    private final long minLong;
    private final long maxLong;

    private final double range;

    public RandomNumberGenerator(int min, int max)
    {
        this.random = new Random();
        this.min = min;
        this.max = max;
        this.range = max - min;
        this.minLong = 0L;
        this.maxLong = 0L;
    }

    public RandomNumberGenerator(long min, long max)
    {
        this.random = new Random();
        this.min = 0;
        this.max = 0;
        this.range = max - min;
        this.minLong = min;
        this.maxLong = max;
    }

    public RandomNumberGenerator(double min, double max)
    {
        this.random = new Random();
        this.min = min;
        this.max = max;
        this.range = max - min;
        this.minLong = 0L;
        this.maxLong = 0L;
    }

    public double getRandomDouble(int precision)
    {
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return BigDecimal.valueOf(shifted).setScale(precision, RoundingMode.HALF_EVEN).doubleValue();
    }

    public Integer getRandomInteger()
    {
        return (int) Math.round(random.nextInt((int) Math.round(range + 1)) + min);
    }

    public Long getRandomLong()
    {
        Long number;

        do
        {
            number = random.nextLong();
            number *= maxLong - minLong;
            number += minLong;
            number = number < 0L ? number * -1L : number;
        }
        while (number < minLong || number > maxLong);

        return number;
    }

}
