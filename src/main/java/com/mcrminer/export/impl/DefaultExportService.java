package com.mcrminer.export.impl;

import com.mcrminer.export.ExportService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class DefaultExportService implements ExportService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultExportService.class);

    @Override
    public <T> void exportAsCsv(PrintWriter printWriter, List<T> data, Class<T> clazz) {
        try {
            String[] columns = getHeaderFieldsFor(clazz);
            writeCsvHeader(printWriter, columns);
            LOG.debug("Mapped columns {} from: {}", columns, clazz);

            StatefulBeanToCsv<T> statefulBeanToCsv = new StatefulBeanToCsvBuilder<T>(printWriter)
                    .withMappingStrategy(getMappingStrategy(clazz, columns))
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(',')
                    .build();

            statefulBeanToCsv.write(data);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException csvException) {
            LOG.error("Error exporting data to CSV", csvException);
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    private <T> MappingStrategy<T> getMappingStrategy(Class<T> clazz, String[] columns) {
        ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(clazz);
        mappingStrategy.setColumnMapping(columns);
        return mappingStrategy;
    }

    private <T> String[] getHeaderFieldsFor(Class<T> clazz) {
        Field[] classFields = clazz.getDeclaredFields();
        return Arrays.stream(classFields).map(Field::getName).toArray(String[]::new);
    }

    private void writeCsvHeader(PrintWriter printWriter, String[] columns) {
        CSVWriter csvWriter = new CSVWriter(
                printWriter,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        csvWriter.writeNext(columns);
    }


}
