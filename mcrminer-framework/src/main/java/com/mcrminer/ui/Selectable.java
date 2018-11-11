package com.mcrminer.ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;

@Data
public class Selectable <T> {
    T value;
    BooleanProperty selected = new SimpleBooleanProperty(true);

    public Selectable(T value) {
        this.value = value;
    }

    public final boolean isSelected() {
        return selected.get();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
