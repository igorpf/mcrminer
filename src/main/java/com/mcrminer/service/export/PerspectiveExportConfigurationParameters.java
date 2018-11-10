package com.mcrminer.service.export;

public interface PerspectiveExportConfigurationParameters {
    Long getProjectId();
    PerspectiveType getPerspectiveType();
    String[] getColumns();
    String getFilename();
    Character getQuoteCharacter();
    Character getEscapeCharacter();
    String getLineEnd();
    Character getSeparatorCharacter();
    Class<?> getPerspectiveClass();
}
