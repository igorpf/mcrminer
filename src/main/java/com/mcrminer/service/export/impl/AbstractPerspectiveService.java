package com.mcrminer.service.export.impl;

import com.mcrminer.service.export.PerspectiveCreationStrategy;
import com.mcrminer.service.export.PerspectiveService;

public abstract class AbstractPerspectiveService<ROOT, P> implements PerspectiveService<ROOT, P> {
    private final Class<P> perspectiveClass;

    public AbstractPerspectiveService(Class<P> perspectiveClass) {
        this.perspectiveClass = perspectiveClass;
    }

    @Override
    public P createPerspective(ROOT rootEntity) {
        P perspective = getInstance();
        for (PerspectiveCreationStrategy<ROOT, P> strategy : getCreationStrategies()) {
            strategy.fillPerspective(rootEntity, perspective);
        }
        return perspective;
    }

    private P getInstance() {
        try {
            return perspectiveClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException();
        }
    }
    protected abstract Iterable<PerspectiveCreationStrategy<ROOT, P>> getCreationStrategies();
}
