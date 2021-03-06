package com.mcrminer.persistence.model;

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

    public ApprovalStatus() {
    }

    public ApprovalStatus(String label, String description, Integer value, boolean isApproval, boolean isVeto) {
        this.label = label;
        this.description = description;
        this.value = value;
        this.isApproval = isApproval;
        this.isVeto = isVeto;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean isApproval() {
        return isApproval;
    }

    public void setApproval(boolean approval) {
        isApproval = approval;
    }

    public boolean isVeto() {
        return isVeto;
    }

    public void setVeto(boolean veto) {
        isVeto = veto;
    }
}
