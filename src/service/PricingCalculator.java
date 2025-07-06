package service;

import vehicle.AbstractVehicle;
import java.util.Date;

public class PricingCalculator {
    public int calcPrice(AbstractVehicle vehicle, Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        int days = (int)(diff / (1000 * 60 * 60 * 24));
        return vehicle.getRentalPrice() * Math.max(days, 1);
    }
}