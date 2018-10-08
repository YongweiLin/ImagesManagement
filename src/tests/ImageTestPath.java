package tests;

import backEnd.Image;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ImageTestPath {

    private Image imageObject;


    @BeforeEach
    public void before() {
        imageObject = new Image("phase2/testImage/adfree @tag1 @tag2.png");
    }

  /*
  Tests of path.
   */

    @Test
    public void getterOfPath() throws Exception {
        String path = "phase2/testImage/adfree @tag1 @tag2.png";
        assertEquals(path, imageObject.getterOfPath());
    }

    @Test
    public void pathAddTagSuccess() throws Exception {
        imageObject.addTag("thisIsNew!");
        String path = "phase2/testImage/adfree @tag1 @tag2 @thisIsNew!.png";
        assertEquals(path, imageObject.getterOfPath());
    }

    @Test
    public void pathAddTagFail() throws Exception {
        imageObject.addTag("tag2");
        String path = "phase2/testImage/adfree @tag1 @tag2.png";
        assertEquals(path, imageObject.getterOfPath());
    }

    @Test
    public void pathDeleteTagSuccess() throws Exception {
        imageObject.deleteNameTag("tag2");
        String path = "phase2/testImage/adfree @tag1.png";
        assertEquals(path, imageObject.getterOfPath());
    }

    @Test
    public void pathDeleteTagFail() throws Exception {
        imageObject.deleteNameTag("tag3");
        String path = "phase2/testImage/adfree @tag1 @tag2.png";
        assertEquals(path, imageObject.getterOfPath());
    }

    @Test
    public void pathBackNameSuccess() throws Exception {
        imageObject.deleteNameTag("tag2");
        imageObject.deleteNameTag("tag1");
        imageObject.backOldName("phase2/testImage/adfree @tag1 @tag2.png");
        String path = "phase2/testImage/adfree @tag1 @tag2.png";
        assertEquals(path, imageObject.getterOfPath());
    }

    @Test
    public void pathBackNameFail() throws Exception {
        imageObject.deleteNameTag("tag2");
        imageObject.deleteNameTag("tag1");
        imageObject.backOldName("phase2/testImage/adfree @tag1 @whatIsThis @tag2.png");
        String path = "phase2/testImage/adfree.png";
        assertEquals(path, imageObject.getterOfPath());
    }

    @AfterAll
    public static void after() {
        File file = new File("phase1");
        deleteDir(file);
    }

    private static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

}
