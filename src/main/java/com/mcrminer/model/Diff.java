package com.mcrminer.model;

import java.util.Set;

public final class Diff extends BaseAuditingEntity {

    private Integer id;
    private PullRequest pullRequest;

    private Set<File> files;
}
