/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import br.ifpb.simba.ourdata.geo.KeyWord;
import br.ifpb.simba.ourdata.geo.Place;
import br.ifpb.simba.ourdata.test.TesteXMLPrint;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author wensttay
 */
public class XMLReader implements Reader<Document, String> {

    @Override
    public Document build(String urlString) {
        try {
            System.out.println("Carregando URL: " + urlString + " ...");
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(120000);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(conn.getInputStream());

        } catch (IllegalArgumentException | MalformedURLException | ParserConfigurationException | SAXException ex) {
            System.out.println("Error: Url: " + urlString + " (" + ex.getClass() + ")\n\n");
        } catch (IOException ex) {
            System.out.println("Error: Url: " + urlString + " (" + ex.getClass() + ")\n\n");
        }
        return null;
        
    }

    @Override
    public void print(String urlString) {
        Document doc = build(urlString);
        if (doc != null) {
            try {
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.ISO_8859_1.name());
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                
                transformer.transform(new DOMSource(doc),
                        new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
                
                System.out.println("Carregou Com Sucesso! ( " + ++TesteXMLPrint.funcionou + " )");
            
            } catch (TransformerException | UnsupportedEncodingException ex) {
                System.out.println("Error: Url: " + urlString + " (" + ex.getClass() + ")\n\n");
            }
        }
    }

}
