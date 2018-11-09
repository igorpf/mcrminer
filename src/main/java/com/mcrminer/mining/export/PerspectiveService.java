package com.mcrminer.mining.export;

import java.util.List;

public interface PerspectiveService <ROOT, P> {
    P createPerspective(ROOT rootEntity);
    List<ROOT> getRootEntitiesForProject(Long projectId);
}
