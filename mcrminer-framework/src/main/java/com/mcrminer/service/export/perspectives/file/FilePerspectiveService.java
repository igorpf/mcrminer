package com.mcrminer.service.export.perspectives.file;

import com.mcrminer.persistence.model.File;
import com.mcrminer.persistence.repository.FileRepository;
import com.mcrminer.service.export.PerspectiveCreationStrategy;
import com.mcrminer.service.export.impl.AbstractPerspectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilePerspectiveService extends AbstractPerspectiveService<File, FilePerspective> {

    private final FileRepository fileRepository;
    private final List<PerspectiveCreationStrategy<File, FilePerspective>> strategies;

    @Autowired
    public FilePerspectiveService(List<PerspectiveCreationStrategy<File, FilePerspective>> strategies,
                                  FileRepository fileRepository) {
        super(FilePerspective.class);
        this.strategies = strategies;
        this.fileRepository = fileRepository;
    }

    @Override
    protected Iterable<PerspectiveCreationStrategy<File, FilePerspective>> getCreationStrategies() {
        return strategies;
    }

    @Override
    public List<File> getRootEntitiesForProject(Long projectId) {
        return fileRepository.findAllByDiffReviewRequestProjectId(projectId);
    }
}
