package service;

import java.util.Date;

import vehicle.AbstractVehicle;

public class PricingCalculator {
    public int calcPrice(AbstractVehicle vehicle, Date start, Date end) {
        long duration = (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
        duration = Math.max(1, duration); // 最低1日料金
        return (int) duration * vehicle.getRentalPrice();
    }
}
