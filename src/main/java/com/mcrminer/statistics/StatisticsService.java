package com.mcrminer.statistics;

public interface StatisticsService <ROOT, STAT> {
    STAT calculateStatistics(ROOT rootEntity);
}
