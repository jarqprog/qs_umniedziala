//package model;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class ArtifactTest extends ModelTest {
//
//    private Artifact artifact;
//
//    @Before
//    public void createArtifact() {
//        artifact = new Artifact(1,"test", 100, "desc", "type" );
//    }
//
//    @Test
//    public void testGetItemId() {
//        assertEquals(1, artifact.getItemId());
//    }
//
//    @Test
//    public void testGetName() {
//        assertEquals("test", artifact.getName());
//    }
//
//    @Test
//    public void testGetValue() {
//        assertEquals(100, artifact.getValue());
//    }
//
//    @Test
//    public void testGetDescription() {
//        assertEquals("desc", artifact.getDescription());
//    }
//
//    @Test
//    public void testGetType() {
//        assertEquals("type", artifact.getType());
//    }
//
//    @Test
//    public void testSetName() {
//        artifact.setName("newName");
//        assertEquals("newName", artifact.getName());
//    }
//
//    @Test
//    public void testSetValue() {
//        artifact.setValue(555);
//        assertEquals(555, artifact.getValue());
//    }
//
//    @Test
//    public void testSetDescription() {
//        artifact.setDescription("newDesc");
//        assertEquals("newDesc", artifact.getDescription());
//    }
//
//    @Test
//    public void testSetType() {
//        artifact.setType("newType");
//        assertEquals("newType", artifact.getType());
//    }
//
//    @Test
//    public void testSetItemId() {
//        artifact.setItemId(99);
//        assertEquals(99, artifact.getItemId());
//
//    }
//
//    @Test
//    public void testToString() {
//        assertEquals("Item id: 1, name: test, value: 100, description: desc, type: type", artifact.toString());
//    }
//}