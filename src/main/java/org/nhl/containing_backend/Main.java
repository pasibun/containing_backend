package org.nhl.containing_backend;

import org.nhl.containing_backend.xml.Xml;

import java.io.FileInputStream;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        try {
            List<Container> containers = Xml.parse(Main.class.getResourceAsStream("/xml1.xml"));
            System.out.println(containers);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
