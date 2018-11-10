package com.mcrminer.service.statistics;

public interface StatisticsCalculationStrategy <ROOT, STAT> {
    void calculate(ROOT root, STAT stat);
}
