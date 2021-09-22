/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.generator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Theodoric
 */
public class RandomDateGenerator
{

    private final LocalDateTime date1;
    private final LocalDateTime date2;
    private final int days;
    
    public RandomDateGenerator(LocalDateTime date1, LocalDateTime date2)
    {
        this.date1 = date1;
        this.date2 = date2;
        this.days = (int) Duration.between(date1, date2).toDays();
    }
    
    public RandomDateGenerator(int d1, int m1, int y1, int d2, int m2, int y2)
    {
        this.date1 = LocalDateTime.of(y1, m1, d1, 0, 0);
        this.date2 = LocalDateTime.of(y2, m2, d2, 0, 0);
        this.days = (int) Duration.between(date1, date2).toDays();
    }
    
    public LocalDateTime getDate()
    {
        LocalDateTime date = date1.plusDays(ThreadLocalRandom.current().nextInt(days + 1));
        date = date.plusHours(ThreadLocalRandom.current().nextInt(days + 1));
        date = date.plusMinutes(ThreadLocalRandom.current().nextInt(days + 1));
        date = date.plusSeconds(ThreadLocalRandom.current().nextInt(days + 1));
        date = date.plusNanos(ThreadLocalRandom.current().nextInt(days + 1));
        return date;
    }
    
}
