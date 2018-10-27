package com.mcrminer.statistics;

public interface StatisticsCalculationStrategy <ROOT, STAT> {
    void calculate(ROOT root, STAT stat);
}
