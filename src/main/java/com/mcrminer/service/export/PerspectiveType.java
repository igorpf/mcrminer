package com.mcrminer.service.export;

public interface PerspectiveType {
    String getTitleLocalizationKey();
    Class<?> getPerspectiveClass();
    PerspectiveService<? ,?> getPerspectiveService();
}
