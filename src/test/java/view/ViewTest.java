package view;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

// FIXME: 22.03.18 does not test overrided methods form child classes
//

public class ViewTest {
    private ViewAdmin view;
    private final  ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp(){
        view = mock(ViewAdmin.class);
        System.setOut(new PrintStream(outContent));
    }
    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void testIf_getInputFromUser_returnProperString() {
        when(view.getInputFromUser("")).thenReturn("hola");
        assertEquals("hola", view.getInputFromUser(""));

    }

    @Test
    public void testIf_getInputFromUser_returnProperInt() {
        when(view.getIntInputFromUser("")).thenReturn(5);
        assertEquals(5, view.getIntInputFromUser(""));
    }

    @Test
    public void testIf_displayText_worksCorretly() {
        View view = new ViewAdmin();
        String expectedOutput = "Nie mam czasu";
        view.displayText("Nie mam czasu");
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testIf_displayList_iterateProperly() {
        List<String> list = new ArrayList<>(Arrays.asList("one", "two"));
        View view = new ViewAdmin();
        view.displayList(list);
        assertEquals("one\ntwo", outContent.toString().trim());

    }
}