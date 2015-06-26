package cz.muni.fi.PB138.main.db;

import cz.muni.fi.PB138.main.entities.Bar;
import cz.muni.fi.PB138.main.entities.Drink;
import cz.muni.fi.PB138.main.entities.Order;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create XML document in memory
 * @author Benjamin Varga
 * @version 17.6.2015
 */
public class CreateDocument {

    public static final String ADMIN_XSD = "database/admin.xsd";
    public static final String USER_XSD = "database/user.xsd";
    public static final String BAR_XSD = "database/bar.xsd";
    public static final String SCHEMA_LOCATION = "xsi:noNamespaceSchemaLocation";
    public static final String XMLNS_XSI = "xmlns:xsi";
    public static final String SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";

    private static final Logger logger = Logger.getLogger(CreateDocument.class.getName());

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
        } else if (schemaRole == 2) {
            rootElement.setAttribute(SCHEMA_LOCATION, BAR_XSD);
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

    /**
     * Create a new XML document for user
     * @param orderList list of orders
     * @return input stream which contain xml document
     */
    public InputStream createUserDocument(List<Order> orderList) {
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
                drinksElement.appendChild(createElementWithAttributeAndText(document, "drink", "name", drink.getName(), countOfDrink(order.getDrinkList(), drink.getName())));
            }
        }

        return transformToInputStream(document);
    }

    /**
     * Create a new XML Document for ADMIN
     * @param orderList list of orders
     * @return input stream which contain xml document
     */
    public InputStream createAdminDocument(List<Order> orderList) {
        Document document = newDocument();

        Element rootElement = createRootElement(document, "data", 1);
        for (Order order : orderList) {
            Element userElement =
                    createElementWithOneAttribute(document, "admin", "admin_id", "" + order.getUserID());
            rootElement.appendChild(userElement);

            Element dateElement =
                    createElementWithOneAttribute(document, "date", "value", order.getDatetime().toString());
            userElement.appendChild(dateElement);

            Element barElement =
                    createElementWithOneAttribute(document, "bar", "bar_id", "" + order.getBarID());
            dateElement.appendChild(barElement);

            Element incomeElement =
                    createElementWithText(document, "income", order.getPrice().toString());
            barElement.appendChild(incomeElement);

            Element alcoholElement =
                    createElementWithText(document, "alcohol", "" + alcoholQuantityOfOrder(order.getDrinkList()));
            barElement.appendChild(alcoholElement);

            Element drinksElement = document.createElement("drinks");
            barElement.appendChild(drinksElement);

            Element ingredientsElement = document.createElement("ingredients");
            barElement.appendChild(ingredientsElement);

            for (Drink drink : order.getDrinkList()) {
                drinksElement.appendChild(createElementWithAttributeAndText(document, "drink", "name", drink.getName(), countOfDrink(order.getDrinkList(), drink.getName())));//pocet drinkov

                //ingredientsElement.appendChild(createElementWithTwoAttributeAndText(document, "ingredient", "name", "rum", "amount", "0.04", "2"));
            }
        }
        return transformToInputStream(document);
    }

    public InputStream createBarDocument(List<Bar> barList){
        Document document = newDocument();

        Element rootElement = createRootElement(document, "data", 2);

        Element barsElements = document.createElement("bars");
        rootElement.appendChild(barsElements);

        for (Bar bar : barList) {
            Element barElement = createElementWithOneAttribute(document, "bar", "owner_id", String.valueOf(bar.getOwnerId()));

            Element barIdElement = createElementWithText(document, "bar_id", String.valueOf(bar.getId()));
            barElement.appendChild(barIdElement);

            Element nameElement = createElementWithText(document, "name", bar.getName());
            barElement.appendChild(nameElement);

            Element infoElement = createElementWithText(document, "info", bar.getInfo());
            barElement.appendChild(infoElement);

            barsElements.appendChild(barElement);
        }

        return transformToInputStream(document);
    }

    /**
     * Calculate how much nameOfDrink contain list of drinks
     * @param drinkList list of drinks
     * @param nameOfDrink name of counting drink
     * @return count of drink
     */
    private String countOfDrink(List<Drink> drinkList, String nameOfDrink) {
        int counter = 0;
        for (Drink drink : drinkList) {
            if (drink.getName().equals(nameOfDrink)) counter++;
        }
        return "" + counter;
    }

    /**
     * Calculate how much alcohol containing the orders
     * @param drinkList
     * @return
     */
    private String alcoholQuantityOfOrder(List<Drink> drinkList) {
        double alcohol = 0;
        for (Drink drink : drinkList) {
            alcohol += drink.getAlcoholQuantity();
        }
        return "" + alcohol;
    }

    /**
     * Write the XML document to the console
     * @param document
     */
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
            logger.log(Level.SEVERE, "Transform exception. ", ex);
        } catch (TransformerException ex) {
            logger.log(Level.SEVERE, "Transform exception. ", ex);
        }
    }

    public InputStream transformToInputStream(Document document) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(source, result);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (TransformerConfigurationException ex) {
            logger.log(Level.SEVERE, "Transform exception. ", ex);
        } catch (TransformerException ex) {
            logger.log(Level.SEVERE, "Transform exception. ", ex);
        }
        return null;
    }

    public Document transformToXML(String data) {
        Document document = newDocument();
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMResult domResult = new DOMResult(document);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getBytes(Charset.forName("utf-8")));
            StreamSource streamSource = new StreamSource(byteArrayInputStream);
            transformer.transform(streamSource, domResult);
        } catch (TransformerConfigurationException ex) {
            logger.log(Level.SEVERE, "Transform exception. ", ex);
        } catch (TransformerException ex) {
            logger.log(Level.SEVERE, "Transform exception. ", ex);
        }
        logger.log(Level.INFO, "Transform to XML is OK.");
        return document;
    }
}
