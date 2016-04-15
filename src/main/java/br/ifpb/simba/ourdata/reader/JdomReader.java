/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import br.ifpb.simba.ourdata.test.TesteJsomPrintAll;
import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.JDOMParseException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Wensttay
 */
public class JdomReader implements Reader<Element, String> {

    @Override
    public Element build(String url) {
        try {
            Document document = new SAXBuilder().build(url);
            Element rootNode = document.getRootElement();

            if (rootNode != null) {
                return rootNode;
            }        
        } catch (JDOMParseException ex) {
            System.out.println("Error: Url: " + url + " (Arquivo Migrado para outro Site)\n\n");
        } catch (UnknownHostException ex) {
            System.out.println("Error: Url: " + url + " (Conecção interrompida)\n\n");
        } catch (OutOfMemoryError ex) {
            System.out.println("Error: Url: " + url + " (Arquivo excedeu o limite de memoria)\n\n");
        } catch (FileNotFoundException ex) {
            System.out.println("Error: Url: " + url + " (Acesso Negado à página XML)\n\n");
        } catch (JDOMException | IOException ex) {
            System.out.println("Error: Url: " + url + " ("+ ex.getMessage() +")\n\n");
        }
        return null;
    }

    @Override
    public void print(String url) {
        Element auxElement = build(url);
        XMLOutputter outputter;

        if (auxElement != null) {
            System.out.println(String.format("Carregando URL: %s ...", url));
            outputter = new XMLOutputter(Format.getPrettyFormat());
            TesteJsomPrintAll.funcionou++;
            System.out.println("Carregou Com Sucesso! ( " + TesteJsomPrintAll.funcionou + " )");
//            String xmlString = outputter.outputString(auxElement);
//            System.out.println(xmlString);
        }
//        outputter = null;
//        auxElement = null;
    }

//    @Override
//    public void printChildren(String url) {
//        Element auxElement = build(url);
//        if (auxElement != null) {
//            printChildren(auxElement, "");
//        }
//    }
//
//    private void printChildren(Element auxElement, String espace) {
//
//        List<Element> rootNodeList = auxElement.getChildren();
//
//        if (rootNodeList != null && !rootNodeList.isEmpty()) {
//            for (int i = 0; i < rootNodeList.size(); i++) {
//                System.out.print(espace + rootNodeList.get(i).getName() + " = "
//                        + rootNodeList.get(i).getText());
//                printChildren(rootNodeList.get(i), espace + "    ");
//            }
//            rootNodeList.clear();
//        }
//
//    }
}
