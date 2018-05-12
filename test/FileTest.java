
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alirzea
 */
public class FileTest {
    public static void main(String[] args) {
        File file = new File("web/files/adjhfjasdf");
        System.out.println(file.mkdir());
    }
}
