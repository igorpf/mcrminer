package com.mcrminer.export;

import com.mcrminer.export.perspectives.enums.PerspectiveType;

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
