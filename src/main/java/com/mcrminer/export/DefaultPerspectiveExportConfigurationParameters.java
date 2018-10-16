package com.mcrminer.export;

import com.mcrminer.export.perspectives.enums.PerspectiveType;
import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class DefaultPerspectiveExportConfigurationParameters implements PerspectiveExportConfigurationParameters {
    Long projectId;
    PerspectiveType perspectiveType;
    @Builder.Default
    Character quote = CSVWriter.NO_QUOTE_CHARACTER,
            separator = CSVWriter.DEFAULT_SEPARATOR,
            escape = CSVWriter.DEFAULT_ESCAPE_CHARACTER;
    @Builder.Default
    String filename = "", lineEnd = CSVWriter.DEFAULT_LINE_END;
    String[] columns;
    Class<?> perspectiveClass;

    @Override
    public Long getProjectId() {
        return projectId;
    }

    @Override
    public PerspectiveType getPerspectiveType() {
        return perspectiveType;
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public Character getQuoteCharacter() {
        return quote;
    }

    @Override
    public Character getEscapeCharacter() {
        return escape;
    }

    @Override
    public String getLineEnd() {
        return lineEnd;
    }

    @Override
    public Character getSeparatorCharacter() {
        return separator;
    }

    @Override
    public Class<?> getPerspectiveClass() {
        return perspectiveClass;
    }
}
