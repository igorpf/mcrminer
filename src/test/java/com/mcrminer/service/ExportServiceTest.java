package com.mcrminer.service;

import com.mcrminer.model.Review;
import com.mcrminer.service.impl.DefaultExportService;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ExportServiceTest {

    private ExportService exportService = new DefaultExportService();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        PrintWriter printWriter = new PrintWriter(System.out);
        List<Review> reviews = new ArrayList<>();
        exportService.exportAsCsv(printWriter, reviews);
    }
}