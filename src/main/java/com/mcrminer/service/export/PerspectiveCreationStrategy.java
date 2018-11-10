package com.mcrminer.service.export;

public interface PerspectiveCreationStrategy <ROOT, P> {
    void fillPerspective(ROOT rootEntity, P perspective);
}
