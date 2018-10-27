package com.mcrminer.statistics.impl.project;

import com.mcrminer.model.File;
import com.mcrminer.model.Project;
import com.mcrminer.repository.FileRepository;
import com.mcrminer.statistics.StatisticsCalculationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
@Order(0)
public class ProjectFileStatisticsCalculationStrategy implements StatisticsCalculationStrategy<Project, ProjectStatistics> {

    private final FileRepository fileRepository;

    @Autowired
    public ProjectFileStatisticsCalculationStrategy(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void calculate(Project project, ProjectStatistics statistics) {
        List<File> allFiles = fileRepository.findAllByDiffReviewRequestProjectId(project.getId());
        List<File> commentedFiles = allFiles.stream().filter(file -> isNotEmpty(file.getComments())).collect(Collectors.toList());

        statistics.setChangedFiles(allFiles.size());
        statistics.setComments(allFiles.stream().map(File::getComments).mapToLong(Collection::size).sum());
        statistics.setAverageCommentSize(allFiles.stream().map(File::getComments).flatMap(Collection::stream).mapToLong(c -> c.getText().length()).average().orElse(0.0));
        statistics.setChangedLinesOfCode(allFiles.stream().mapToLong(file -> getValueOrZeroIfNull(file.getLinesRemoved()) + getValueOrZeroIfNull(file.getLinesInserted())).sum());
        statistics.setCommentedFilesPercentage(commentedFiles.size() / (double) allFiles.size());
    }

    private long getValueOrZeroIfNull(Long number) {
        return number != null? number: 0L;
    }

}
