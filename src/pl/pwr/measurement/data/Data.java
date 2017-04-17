package pl.pwr.measurement.data;

import java.time.Duration;
import java.time.Instant;

public class Data {

    private final double RESISTANCE = 10;
    private double capacity;
    private double sourceVoltage;
    private double chartPoint;
    private Instant startMeasurement;
    private double time;

    public double getCapacity() {
        return capacity;
    }
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
    public double getSourceVoltage() {
        return sourceVoltage;
    }
    public void setSourceVoltage(double sourceVoltage) {
        this.sourceVoltage = sourceVoltage;
    }
    public Instant getStartMeasurement() {
        return startMeasurement;
    }
    public void setStartMeasurement(Instant startMeasurement) {
        this.startMeasurement = startMeasurement;
    }
    public double getResistance() {
        return RESISTANCE;
    }
    public double getTime() {
        Duration duration = Duration.between(startMeasurement, Instant.now());
        time = duration.toMillis()/1000;
        return time;
    }
    public double getChartPoint() {
        chartPoint = (sourceVoltage*(1-Math.exp((-time/1000)/(RESISTANCE*capacity))));
        return chartPoint;
    }
    public Data() {
        capacity = 0;
        sourceVoltage = 0;
    }
}
