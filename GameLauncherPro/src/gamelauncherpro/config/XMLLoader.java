/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelauncherpro.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author CAVI
 */
public class XMLLoader {
    private final Document xmlDocument;
    private final XPath xPath;
    
    public XMLLoader(InputStream stream) throws ParserConfigurationException, IOException, SAXException {
        xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
        xPath = XPathFactory.newInstance().newXPath();
    }

    public XMLLoader(File file) throws ParserConfigurationException, IOException, SAXException {
        xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        xPath = XPathFactory.newInstance().newXPath();
    }
    
    public String getString(String xmlPath, String fallback) {
        try {
            return getString(xmlPath);
        } catch(XPathExpressionException t) {
            return fallback;
        }
    }

    public String getString(String xmlPath) throws XPathExpressionException {
        XPathExpression expr = xPath.compile(xmlPath);
        return (String)expr.evaluate(xmlDocument, XPathConstants.STRING);
    }

    public double getDouble(String xmlPath) throws XPathExpressionException {
        return Double.parseDouble(this.getString(xmlPath));
    }

    public double getDouble(String xmlPath, double fallback) {
        try {
            return getDouble(xmlPath);
        } catch(NumberFormatException t) {
            return fallback;
        } catch(XPathExpressionException t) {
            return fallback;
        }
    }

    public long getLong(String xmlPath) throws XPathExpressionException {
            return Long.parseLong(this.getString(xmlPath));
    }

    public long getLong(String xmlPath, long fallback) {
        try {
            return getLong(xmlPath);
        } catch(NumberFormatException t) {
            return fallback;
        } catch(XPathExpressionException t) {
            return fallback;
        }
    }

    public int getInt(String xmlPath) throws XPathExpressionException {
        return Integer.parseInt(this.getString(xmlPath));
    }

    public int getInt(String xmlPath, int fallback) {
        try {
            return getInt(xmlPath);
        } catch(NumberFormatException t) {
            return fallback;
        } catch(XPathExpressionException t) {
            return fallback;
        }
    }

    public float getFloat(String xmlPath) throws XPathExpressionException {
        return Float.parseFloat(this.getString(xmlPath));
    }

    public float getFloat(String xmlPath, float fallback) {
        try {
            return getFloat(xmlPath);
        } catch(NumberFormatException t) {
            return fallback;
        } catch(XPathExpressionException t) {
            return fallback;
        }
    }

    public boolean getBoolean(String xmlPath) throws XPathExpressionException {
        return Boolean.parseBoolean(this.getString(xmlPath));
    }

    public boolean getBoolean(String xmlPath, boolean fallback) {
        try {
            return getBoolean(xmlPath);
        } catch(Throwable t) {
            return fallback;
        }
    }
    
    public Node getNode(String xmlPath) throws XPathExpressionException {
        XPathExpression expr = xPath.compile(xmlPath);
        return ((Node)expr.evaluate(xmlDocument, XPathConstants.NODE));
    }

    public List<Node> getElements(String xmlPath) throws XPathExpressionException {
        XPathExpression expr = xPath.compile(xmlPath);
        NodeList nodes = (NodeList)expr.evaluate(xmlDocument, XPathConstants.NODESET);
        
        List<Node> result = new ArrayList<Node>();
        for(int i = 0; i<nodes.getLength(); i++) {
            result.add(nodes.item(i));
        }
        
        return result;
    }
}
