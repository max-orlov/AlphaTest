package app;

import org.xml.sax.SAXException;
import xmlparsing.XMLParser;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, SAXException {

         //Заполнить базу данных
        XMLParser.DocParser();

    }
}
