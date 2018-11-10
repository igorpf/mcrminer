package com.mcrminer.service.statistics;

public interface StatisticsService <ROOT, STAT> {
    STAT calculateStatistics(ROOT rootEntity);
}
