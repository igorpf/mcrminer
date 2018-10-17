package com.mcrminer.export.perspectives.file;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.Diff;
import com.mcrminer.model.File;
import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import com.mcrminer.repository.factory.impl.TransientObjectsFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class FilePerspectiveServiceUnitTest {

    private DomainObjectsFactory domainObjectsFactory = new TransientObjectsFactory();
    private FilePerspectiveService filePerspectiveService;
    private List<PerspectiveCreationStrategy<File, FilePerspective>> strategies = new ArrayList<>();
    private File file;

    @Before
    public void setUp() throws Exception {
        strategies.add(new FileAssociationsPerspectiveCreationStrategy());
        filePerspectiveService = new FilePerspectiveService(strategies, null);
        Project project = domainObjectsFactory.createProject("www.example.com", "Sample project");
        ReviewRequest reviewRequest = domainObjectsFactory.createReviewRequest(project);
        Diff diff = domainObjectsFactory.createDiff(reviewRequest);
        file = domainObjectsFactory.createFile("somefile.txt", diff);
    }

    @Test
    public void testFilePerspective() {
        assertNotNull(filePerspectiveService.createPerspective(file));
    }
}