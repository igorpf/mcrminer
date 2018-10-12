package com.mcrminer.model.projections;

import com.mcrminer.model.enums.FileStatus;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class FileProjection {
    Integer id, linesInserted, linesRemoved;
    String newFilename, oldFilename;
    FileStatus status;
}
