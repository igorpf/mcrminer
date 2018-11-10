package com.mcrminer.service.export;

import com.mcrminer.service.export.perspectives.enums.PerspectiveType;

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
