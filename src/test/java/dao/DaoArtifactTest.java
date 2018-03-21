package dao;

import model.Artifact;
import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.util.List;

import static org.junit.Assert.*;

public class DaoArtifactTest {

    private IDaoArtifact dao;

    @Before
    public void setUp() {
        dao = new DaoArtifact();
    }

    @After
    public void tearDown() {
        dao = null;
    }

    @Test
    public void constructorTest() {
        assertNotNull(dao);
    }

    @Test
    public void createArtifact() {
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
    public void createArtifact1() {
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
    }


    @Test
    public void getAllArtifactsAssumingDaoShouldReturnAtLeastEmptyListThatShouldntContainsNullObjects() {
        List<Artifact> artifacts = dao.getAllArtifacts();
        assertNotNull(artifacts);
        artifacts.forEach(Assert::assertNotNull);
    }

    @Test
    public void updateArtifact() {  // todo
    }

    @Test
    public void exportArtifact() {  // todo
    }


    @Test
    public void getArtifactsByTypeAssumingDaoShouldReturnAtLeastEmptyList() {
        String artifactsType = "team";
        List<Artifact> artifacts = dao.getArtifacts(artifactsType);
        assertNotNull(artifacts);
        artifacts.forEach(Assert::assertNotNull);

        if(artifacts.size() > 0) {
            assertTrue(artifacts.stream().anyMatch(a -> a.getType().equals(artifactsType)));
        }
    }

    @Rule
    public MethodRule watchman = new TestWatchman() {
        public void starting(FrameworkMethod method) {
            System.out.println("Starting test: " + method.getName());
            }
    };
}
