package com.mcrminer.service;

import java.io.PrintWriter;
import java.util.List;

public interface ExportService {
    <T> void exportAsCsv(PrintWriter printWriter, List<T> data, Class<T> clazz);
}
