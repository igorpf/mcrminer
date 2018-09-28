package com.mcrminer.service.impl;

import com.mcrminer.service.ExportService;
import com.opencsv.bean.ColumnPositionMappingStrategy;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;

public class DefaultExportService implements ExportService {
    @Override
    public <T> void exportAsCsv(PrintWriter printWriter, List<T> data, Class<T> clazz) {
        try {
            ColumnPositionMappingStrategy<T> mapStrategy = new ColumnPositionMappingStrategy<>();
            mapStrategy.generateHeader();
            mapStrategy.setType(clazz);
            Field[] classFields = clazz.getDeclaredFields();

        } catch (Exception e) {

        }
    }
}
