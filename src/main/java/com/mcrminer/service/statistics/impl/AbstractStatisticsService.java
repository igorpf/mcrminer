package com.mcrminer.service.statistics.impl;

import com.mcrminer.service.statistics.StatisticsCalculationStrategy;
import com.mcrminer.service.statistics.StatisticsService;

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
