package dao;

import model.Artifact;
import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DaoArtifactTest extends DaoTest {

    private IDaoArtifact dao;

    public void setUp() {
        dao = new DaoArtifact();
    }

    public void tearDown() {
        dao = null;
    }

    @Test
    public void constructorTest() {
        assertNotNull(dao);
    }

    @Test
    public void createArtifactUsingFourParameters() {
        String itemName = "test";
        int itemValue = 100;
        String itemDescription = "4testing purposes";
        String itemType = "t";
        Artifact item = dao.createArtifact(itemName, itemValue, itemDescription, itemType);
        assertNotNull(item);
        assertEquals(itemName, item.getName());
        assertEquals(itemValue, item.getValue());
        assertEquals(itemDescription, item.getDescription());
        assertEquals(itemType, item.getType());
    }

    @Test
    public void createArtifactUsingFiveParameters() {
        int itemId = 1000;
        String itemName = "test";
        int itemValue = 100;
        String itemDescription = "4testing purposes";
        String itemType = "t";
        Artifact item = dao.createArtifact(itemId, itemName, itemValue, itemDescription, itemType);
        assertNotNull(item);
        assertEquals(itemId, item.getItemId());
        assertEquals(itemName, item.getName());
        assertEquals(itemValue, item.getValue());
        assertEquals(itemDescription, item.getDescription());
        assertEquals(itemType, item.getType());
    }

    @Test
    public void importArtifact() {

        int id = 1;
        Artifact artifact = dao.importArtifact(id);
        assertNotNull(artifact);
        String name = "Combat training";
        int value = 50;
        String description = "Private mentoring";
        String type = "individual";

        assertEquals(name, artifact.getName());
        assertEquals(value, artifact.getValue());
        assertEquals(description, artifact.getDescription());
        assertEquals(type, artifact.getType());
    }


    @Test
    public void getAllArtifacts() {
        List<Artifact> artifacts = dao.getAllArtifacts();
        assertNotNull(artifacts);
        artifacts.forEach(Assert::assertNotNull);
        assertTrue(artifacts
                .stream().filter(a -> a.getValue() > 0)
                .collect(Collectors.toList())
                .size() > 0);
    }

    @Test
    public void updateArtifact() {
        assertTrue(dao.updateArtifact(createArtifactThatAlreadyExistsInDatabase()));
    }

    @Test
    public void exportArtifactWhichIsNotInDatabase() {
        assertTrue(dao.exportArtifact(createArtifactWhichIsNotInDatabase()));
    }

    @Test
    public void getArtifactsByType() {
        String artifactsType = "individual";
        List<Artifact> artifacts = dao.getArtifacts(artifactsType);
        assertNotNull(artifacts);
        artifacts.forEach(Assert::assertNotNull);
        assertTrue(artifacts.stream().allMatch(a -> a.getType().equals(artifactsType)));
    }

    @Test
    public void getArtifactsByTypeThatNotExists() {
        String artifactsType = "testing101";
        List<Artifact> artifacts = dao.getArtifacts(artifactsType);
        assertNotNull(artifacts);
        assertTrue(artifacts.size() == 0);
    }

    private Artifact createArtifactWhichIsNotInDatabase() {
        int id = 10;
        String name = "Combat test";
        int value = 50;
        String description = "Test mentoring";
        String type = "test";
        return new Artifact(id, name, value, description, type);
    }

    private Artifact createArtifactThatAlreadyExistsInDatabase() {
        int id = 1;
        String name = "Combat training";
        int value = 50;
        String description = "Private mentoring";
        String type = "individual";
        return new Artifact(id, name, value, description, type);
    }
}
