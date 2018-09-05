package com.mcrminer.model;

import java.time.LocalDateTime;

public abstract class BaseAuditingEntity {

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public BaseAuditingEntity() {
    }

    public BaseAuditingEntity(LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
