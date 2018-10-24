package com.mcrminer.statistics.impl;

import com.mcrminer.statistics.StatisticsCalculationStrategy;
import com.mcrminer.statistics.StatisticsService;

public abstract class AbstractStatisticsService <ROOT, STAT> implements StatisticsService<ROOT, STAT> {

    private final Class<STAT> statClass;

    public AbstractStatisticsService(Class<STAT> statClass) {
        this.statClass = statClass;
    }

    @Override
    public STAT calculateStatistics(ROOT rootEntity) {
        STAT perspective = getInstance();
        for (StatisticsCalculationStrategy<ROOT, STAT> strategy : getCalculationStrategies()) {
            strategy.calculate(rootEntity, perspective);
        }
        return perspective;
    }

    private STAT getInstance() {
        try {
            return statClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException();
        }
    }

    protected abstract Iterable<StatisticsCalculationStrategy<ROOT, STAT>> getCalculationStrategies();
}
