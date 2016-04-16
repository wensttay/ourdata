/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import br.ifpb.simba.ourdata.test.TesteXMLPrint;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author wensttay
 */
public class XMLReader implements Reader<String, String> {

    @Override
    public String build(String url) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void print(String urlString) {
        try {
            System.out.println("Carregando URL: " + urlString + " ...");
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(120000);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());
            
//            Transformer transformer = TransformerFactory.newInstance().newTransformer();
//            Source source = new DOMSource(doc);
//            Result output = new StreamResult(System.out);
//            transformer.transform(source, output);

            printDocument(doc, System.out);
            System.out.println("Carregou Com Sucesso! ( " + ++TesteXMLPrint  .funcionou + " )");

        } catch (IllegalArgumentException | MalformedURLException | ParserConfigurationException |
                TransformerException | SAXException ex) {
            System.out.println("Error: Url: " + urlString + " (" + ex.getClass() + ")\n\n");
        } catch (IOException ex) {
            System.out.println("Error: Url: " + urlString + " (" + ex.getClass() + ")\n\n");
        }
    }

    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

//        transformer.transform(new DOMSource(doc),
//                new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }
}
