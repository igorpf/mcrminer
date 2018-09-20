package com.mcrminer.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public final class ApprovalStatus {

    @Id
    private String label;
    private String description;
    private Integer value;
    private boolean isApproval;
    private boolean isVeto;
}
