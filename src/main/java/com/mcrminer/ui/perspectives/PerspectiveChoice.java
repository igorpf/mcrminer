package com.mcrminer.ui.perspectives;

import com.mcrminer.service.export.perspectives.enums.PerspectiveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class PerspectiveChoice {
    PerspectiveType perspectiveType;
    String title;
    Class<?> perspectiveClass;

    @Override
    public String toString() {
        return title;
    }
}
