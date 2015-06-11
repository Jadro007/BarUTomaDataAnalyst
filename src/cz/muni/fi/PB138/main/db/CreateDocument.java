package cz.muni.fi.PB138.main.db;

import cz.muni.fi.PB138.main.entities.Drink;
import cz.muni.fi.PB138.main.entities.Order;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CreateDocument {

    public static final String ADMIN_XSD = "admin.xsd";//Todo upravit cestu
    public static final String USER_XSD = "user.xsd";//todo
    public static final String BAR_XSD = "bar.xsd";//Todo
    public static final String SCHEMA_LOCATION = "xsi:noNamespaceSchemaLocation";
    public static final String XMLNS_XSI = "xmlns:xsi";
    public static final String SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";

    private static final Logger logger = Logger.getLogger(CreateDocument.class.getName());

    //todo test
    public static void main(String[] args) {
        CreateDocument create = new CreateDocument();



        Drink myDrink = new Drink("Pina Colada", new BigDecimal("120.00"), 0.16);
        Drink myDrink2 = new Drink("Jager + Cola", new BigDecimal("55.00"), 0.12);
        List<Drink> drinkList = new ArrayList<>();
        drinkList.add(myDrink);
        drinkList.add(myDrink2);

        Order myOrder = new Order(LocalDate.now(), new BigDecimal("150.00"), 2, 5, drinkList);
        List<Order> orderList = new ArrayList<>();
        orderList.add(myOrder);

        System.out.println("ADMIN XML");
        create.createAdminDocument(orderList);

        System.out.println("\n USER XML");
        create.createUserDocument(orderList);
    }

    /**
     * Create new empty xml document
     * @return new instance of Document
     */
    private Document newDocument() {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.newDocument();
            return document;
        } catch (ParserConfigurationException ex) {
            logger.log(Level.SEVERE, "cannot create DocumentBuilder ", ex);
        }
        return null;
    }

    /**
     * Create root element
     * @param document XML document in memory
     * @param name Name of root element
     * @param schemaRole Choose XSD schema, 0=user; 1=admin; 2=bar
     * @return Root element
     */
    private Element createRootElement(Document document, String name, Integer schemaRole) {
        Element rootElement = document.createElement(name);
        document.appendChild(rootElement);
        rootElement.setAttribute(XMLNS_XSI, SCHEMA_INSTANCE);
        if (schemaRole == 0) {
            rootElement.setAttribute(SCHEMA_LOCATION, USER_XSD);
        } else if (schemaRole == 1) {
            rootElement.setAttribute(SCHEMA_LOCATION, ADMIN_XSD);
        }
        return rootElement;
    }

    /**
     * Create element with one attribute
     * @param document XML document in memory
     * @param name Name of element
     * @param attrName Name of attribute
     * @param attrValue Value of attribute
     * @return new element
     */
    private Element createElementWithOneAttribute(Document document, String name, String attrName, String attrValue) {
        Element element = document.createElement(name);
        element.setAttribute(attrName, attrValue);
        return element;
    }

    /**
     * Create element with two attribute and text
     * @param document XML document in memory
     * @param name Name of element
     * @param attrName1 Name of first attribute
     * @param attrValue1 Value of first attribute
     * @param attrName2 Name of second attribute
     * @param attrValue2 Value of second attribute
     * @param text Text
     * @return new element
     */
    private Element createElementWithTwoAttributeAndText(Document document, String name, String attrName1, String attrValue1, String attrName2, String attrValue2, String text) {
        Element element = createElementWithOneAttribute(document, name, attrName1, attrValue1);
        element.setAttribute(attrName2, attrValue2);
        element.setTextContent(text);
        return element;
    }

    /**
     * Create element with text
     * @param document XML document in memory
     * @param name Name of element
     * @param text Text
     * @return new element
     */
    private Element createElementWithText(Document document, String name, String text) {
        Element element = document.createElement(name);
        element.setTextContent(text);
        return element;
    }

    /**
     * Create element with attribute and text
     * @param document XML document in memory
     * @param name Name of element
     * @param attrName Name of attribute
     * @param attrValue Value of attribute
     * @param text Text
     * @return new element
     */
    private Element createElementWithAttributeAndText(Document document, String name, String attrName, String attrValue, String text) {
        Element element = createElementWithOneAttribute(document, name, attrName, attrValue);
        element.setTextContent(text);
        return element;
    }

    //Todo Dorobit ziskat order example
    public void createUserDocument(List<Order> orderList) {
        Document document = newDocument();

        Element rootElement = createRootElement(document, "data", 0);

        for (Order order : orderList) {
            Element userElement =
                    createElementWithOneAttribute(document, "user", "user_id", "" + order.getUserID());
            rootElement.appendChild(userElement);

            Element dateElement =
                    createElementWithOneAttribute(document, "date", "value", order.getDatetime().toString());
            userElement.appendChild(dateElement);

            Element paymentElement =
                    createElementWithText(document, "payment", order.getPrice().toString());
            dateElement.appendChild(paymentElement);

            Element drinksElement = document.createElement("drinks");
            dateElement.appendChild(drinksElement);

            for (Drink drink : order.getDrinkList()) {
                drinksElement.appendChild(createElementWithAttributeAndText(document, "drink", "name", drink.getName(), countOfDrink(order.getDrinkList(), drink.getName())));//pocet drinkov
            }
        }

      transformToConsoleStream(document);
    }

    //Todo Dorobit ziskat order example
    public void createAdminDocument(List<Order> orderList) {
        Document document = newDocument();

        Element rootElement = createRootElement(document, "data", 1);

        for (Order order : orderList) {
            Element userElement =
                    createElementWithOneAttribute(document, "user", "user_id", "" + order.getUserID());
            rootElement.appendChild(userElement);

            Element dateElement =
                    createElementWithOneAttribute(document, "date", "value", order.getDatetime().toString());
            userElement.appendChild(dateElement);

            Element barElement =
                    createElementWithOneAttribute(document, "bar", "bar_id", "" + order.getBarID());
            dateElement.appendChild(barElement);

            Element incomeElement =
                    createElementWithText(document, "income", order.getPrice().toString()); //todo celkova cena objednavky alebo budeme pocitat cenu podla drinkov??
            barElement.appendChild(incomeElement);

            //todo celkovy alkohol drinkov
            /*Element alcoholElement =
                    createElementWithText(document, "alcohol", "" + drink.getAlcoholQuantity());
            barElement.appendChild(alcoholElement);*/

            Element drinksElement = document.createElement("drinks");
            barElement.appendChild(drinksElement);

            Element ingredientsElement = document.createElement("ingredients");
            barElement.appendChild(ingredientsElement);

            for (Drink drink : order.getDrinkList()) {

                drinksElement.appendChild(createElementWithAttributeAndText(document, "drink", "name", drink.getName(), countOfDrink(order.getDrinkList(), drink.getName())));//pocet drinkov

                //todo map iterator na ingrediencie
                ingredientsElement.appendChild(createElementWithTwoAttributeAndText(document, "ingredient", "name", "rum", "amount", "0.04", "2"));
            }
        }

        transformToConsoleStream(document);
    }

    public void createBarDocument(){

    }

    private String countOfDrink(List<Drink> drinkList, String nameOfDrink) {
        int counter = 0;
        for (Drink drink : drinkList) {
            if (drink.getName().equals(nameOfDrink)) counter++;
        }
        return "" + counter;
    }


    public void transformToConsoleStream(Document document) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            logger.log(Level.SEVERE, "", ex);//Todo
        } catch (TransformerException ex) {
            logger.log(Level.SEVERE, "", ex);//Todo
        }
    }

    public InputStream transformToInputStream(Document document) throws TransformerException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(outputStream);

        transformer.transform(source, result);

        System.out.println("OK");
        return new ByteArrayInputStream(outputStream.toByteArray());
    }


}
