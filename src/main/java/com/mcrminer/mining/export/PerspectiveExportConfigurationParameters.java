package com.mcrminer.mining.export;

import com.mcrminer.mining.export.perspectives.enums.PerspectiveType;

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
