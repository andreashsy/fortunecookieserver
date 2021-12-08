package fortunecookie;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.io.*;
import java.util.Arrays;


public class CookieTest {

    @Test
    public void testCreateCookie() throws IOException {
        String Wdir = System.getProperty("user.dir") + "\\cookietest";
        new File(Wdir).mkdirs();
        Cookie createTest = new Cookie();
        createTest.createCookie(Wdir);
        File createdTest = new File(Wdir + "\\cookie_file.txt");
        assertTrue(createdTest.exists());
        System.out.println("Create test passed");
    }
    @Test
    public void testLoadFile() throws IOException{
        String Wdir = System.getProperty("user.dir") + "\\cookietest";
        new File(Wdir).mkdirs();
        Cookie loadTest = new Cookie();
        loadTest.createCookie(Wdir);
        String loadedCookie = loadTest.loadFile(Wdir).replace("\n", "").replace("\r", "");
        System.out.println(loadedCookie);
        assertTrue(loadedCookie.equals("cookie1cookie2cookie3cookie4"));
        System.out.println("Load test passed");
    }
    @Test
    public void testRandomCookie() throws IOException {
        String Wdir = System.getProperty("user.dir") + "\\cookietest";
        new File(Wdir).mkdirs();
        Cookie randTest = new Cookie();
        randTest.createCookie(Wdir);
        String randList = randTest.loadFile(Wdir);
        String randCookie = randTest.randomCookie(randList);
        String[] originalList = "cookie1,cookie2,cookie3,cookie4".split(",");
        assertTrue(Arrays.stream(originalList).anyMatch(randCookie::equals));
        System.out.println("Random picker test passed");
    }
    @Test
    public void testAddCookie() throws IOException {
        String Wdir = System.getProperty("user.dir") + "\\cookietest";
        new File(Wdir).mkdirs();
        Cookie addTest = new Cookie();
        addTest.createCookie(Wdir);
        addTest.addCookie("addcookietest", Wdir);
        String addTestList = addTest.loadFile(Wdir);
        assertTrue(addTestList.contains("addcookietest"));
        System.out.println("Add cookie test passed");
    }
}
