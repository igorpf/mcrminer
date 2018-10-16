package com.mcrminer.export;

import java.io.PrintWriter;
import java.util.List;

public interface FileWriterService {
    <T> void exportAsCsv(PrintWriter printWriter, List<T> data, PerspectiveExportConfigurationParameters parameters);
}
