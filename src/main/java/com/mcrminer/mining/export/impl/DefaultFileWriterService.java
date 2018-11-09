package com.mcrminer.mining.export.impl;

import com.mcrminer.mining.export.FileWriterService;
import com.mcrminer.mining.export.PerspectiveExportConfigurationParameters;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;


@SuppressWarnings("unchecked")
@Service
public class DefaultFileWriterService implements FileWriterService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultFileWriterService.class);

    @Override
    public <T> void exportAsCsv(PrintWriter printWriter, List<T> data, PerspectiveExportConfigurationParameters parameters) {
        try {
            writeCsvHeader(printWriter, parameters);
            LOG.debug("Mapped columns {} from: {}", parameters.getColumns(), parameters.getPerspectiveClass());

            StatefulBeanToCsv<T> statefulBeanToCsv = new StatefulBeanToCsvBuilder<T>(printWriter)
                    .withMappingStrategy(getMappingStrategy(parameters.getPerspectiveClass(), parameters.getColumns()))
                    .withQuotechar(parameters.getQuoteCharacter())
                    .withSeparator(parameters.getSeparatorCharacter())
                    .build();

            statefulBeanToCsv.write(data);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException csvException) {
            LOG.error("Error exporting data to CSV", csvException);
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    private MappingStrategy getMappingStrategy(Class<?> clazz, String[] columns) {
        ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(clazz);
        mappingStrategy.setColumnMapping(columns);
        return mappingStrategy;
    }

    private void writeCsvHeader(PrintWriter printWriter, PerspectiveExportConfigurationParameters parameters) {
        CSVWriter csvWriter = new CSVWriter(
                printWriter,
                parameters.getSeparatorCharacter(),
                parameters.getQuoteCharacter(),
                parameters.getEscapeCharacter(),
                CSVWriter.DEFAULT_LINE_END);
        csvWriter.writeNext(parameters.getColumns());
    }


}
