package com.mcrminer.service.export.perspectives.file;

import com.mcrminer.persistence.model.Diff;
import com.mcrminer.persistence.model.File;
import com.mcrminer.persistence.model.Project;
import com.mcrminer.persistence.model.ReviewRequest;
import com.mcrminer.persistence.repository.factory.DomainObjectsFactory;
import com.mcrminer.persistence.repository.factory.impl.TransientObjectsFactory;
import com.mcrminer.service.export.PerspectiveCreationStrategy;
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