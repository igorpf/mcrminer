package com.mcrminer.model;

import com.mcrminer.model.enums.FileStatus;

public final class File {

    private Diff diff;

    private String newFilename;
    private String oldFilename; //if renamed
    private Integer linesInserted;
    private Integer linesRemoved;
    private FileStatus status;
}
