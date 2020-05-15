package xmlparsing;

import dao.DAO;
import entities.Box;
import entities.Item;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XMLParser {

    private static File file = new File("C:\\Users\\Макс\\IdeaProjects\\AlphaTest\\text.xml");
    private static Set<Integer> result = new HashSet<Integer>();

    private static List<Box> boxes = new ArrayList<>();
    private static List<Item> items = new ArrayList<>();

    public XMLParser() {
    }

    /*
     *Заполнение БД из XML файла
     */
    public static void DocParser() throws IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = documentBuilder.parse(file);

        NodeList nodeList = document.getElementsByTagName("Storage");
        NodeList childNodes = null;
        Node node;
        for (int i = 0; i < nodeList.getLength(); i++) {
            childNodes = nodeList.item(i).getChildNodes();
        }

        for (int i = 0; i < childNodes.getLength(); i++) {
            node = childNodes.item(i);
            if (node.getNodeName().equals("Item")) {
                Item item = new Item();
                item.setId(Long.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()));
                if (node.getAttributes().getNamedItem("color") != null) {
                    item.setColor(node.getAttributes().getNamedItem("color").getNodeValue());
                }
                items.add(item);
            } else if (node.getNodeName().equals("Box")) {
                Box box = new Box();
                box.setId(Long.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()));
                boxes.add(box);
                saveChildNodesDataToDataBase(node);
            }
        }
        DAO.addObjects(boxes);
        DAO.addObjects(items);

    }

    private static void saveChildNodesDataToDataBase(Node node) {

        NodeList boxNodes = node.getChildNodes();
        Node innerNode;

        for (int i = 0; i < boxNodes.getLength(); i++) {
            innerNode = boxNodes.item(i);

            if (innerNode.getNodeName().equals("Box")) {
                Box box = new Box();
                box.setId(Long.valueOf(innerNode.getAttributes().getNamedItem("id").getNodeValue()));
                box.setContained_in(Long.valueOf(innerNode.getParentNode().getAttributes().getNamedItem("id").getNodeValue()));
                boxes.add(box);
                if(innerNode.hasChildNodes()) {
                    saveChildNodesDataToDataBase(innerNode);
                }
            } else if(innerNode.getNodeName().equals("Item")) {
                Item item = new Item();
                item.setId(Long.valueOf(innerNode.getAttributes().getNamedItem("id").getNodeValue()));
                if(innerNode.getAttributes().getNamedItem("color") != null) {
                    item.setColor(innerNode.getAttributes().getNamedItem("color").getNodeValue());
                }
                item.setBox(Long.valueOf(innerNode.getParentNode().getAttributes().getNamedItem("id").getNodeValue()));
                items.add(item);
            }
        }
    }

    /*
     * Метод для получения результата из XML файла
     */
    public static Set<Integer> getResultSetFromXML(Integer boxId, String itemColor) throws IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = documentBuilder.parse(file);

        NodeList items;

        if (boxId == null) {
            items = document.getElementsByTagName("Item");
            for (int i = 0; i < items.getLength(); i++) {
                Node node = items.item(i);
                NamedNodeMap attributes = node.getAttributes();
                for (int j = 0; j < attributes.getLength(); j++) {
                    if (attributes.item(j).getNodeValue().equals(itemColor)
                            && node.getParentNode().getNodeName().equals("Storage")) {
                        result.add(Integer.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()));
                    }
                }
            }
        } else {
            NodeList boxes = document.getElementsByTagName("Box");
            Node box = null;
            for (int i = 0; i < boxes.getLength(); i++) {
                if (Integer.valueOf(boxes.item(i).getAttributes().getNamedItem("id").getNodeValue()).equals(boxId)) {
                    box = boxes.item(i);
                }
            }

            if (box.hasChildNodes()) {
                getChildNodesDataFromXML(box.getChildNodes(), itemColor);
            }
        }
        return result;
    }

    public static void getChildNodesDataFromXML(NodeList list, String itemColor) {

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("Item")) {
                NamedNodeMap attributes = node.getAttributes();
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node attr = attributes.item(j);
                    if (attr.getNodeValue().equals(itemColor)) {
                        result.add(Integer.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()));
                    }
                }
            }
            if (node.hasChildNodes())
                getChildNodesDataFromXML(node.getChildNodes(), itemColor);
        }
    }

}


