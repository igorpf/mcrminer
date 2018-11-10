package com.mcrminer.service.export.perspectives.file;

import com.mcrminer.service.export.PerspectiveService;
import com.mcrminer.service.export.PerspectiveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilePerspectiveType implements PerspectiveType {

    private final FilePerspectiveService filePerspectiveService;

    @Autowired
    public FilePerspectiveType(FilePerspectiveService filePerspectiveService) {
        this.filePerspectiveService = filePerspectiveService;
    }

    @Override
    public String getTitleLocalizationKey() {
        return "perspective.file";
    }

    @Override
    public Class<?> getPerspectiveClass() {
        return FilePerspective.class;
    }

    @Override
    public PerspectiveService<?, ?> getPerspectiveService() {
        return filePerspectiveService;
    }
}
