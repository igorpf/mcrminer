package com.mcrminer.mining.statistics;

public interface StatisticsService <ROOT, STAT> {
    STAT calculateStatistics(ROOT rootEntity);
}
