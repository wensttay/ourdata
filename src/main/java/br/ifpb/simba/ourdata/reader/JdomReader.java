/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Node;

/**
 *
 * @author wensttay
 */
public class JdomReader implements Reader<Element, String> {

    @Override
    public Element build(String url) {
        SAXBuilder builder;
        Document document;
        try {
            builder = new SAXBuilder();
            document = (Document) builder.build(url);

            Element rootNode = document.getRootElement();

            if (rootNode != null) {
                return rootNode;
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(JdomReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void print(String url) {
        Element aux;
        aux = build(url);
        if (aux != null) {
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            String xmlString = outputter.outputString(aux);
            System.out.println(xmlString);
        }
    }

    @Override
    public void printChildrenNames(String url) {
        Element aux;
        aux = build(url);
        if (aux != null) {
            printChildrenNames(aux, "");
        }
    }

    private void printChildrenNames(Element aux, String espace) {
        List<Element> rootNodeList = aux.getChildren();

        if (rootNodeList != null && !rootNodeList.isEmpty()) {
            for (int i = 0; i < rootNodeList.size(); i++) {
                System.out.println(espace + rootNodeList.get(i).getName());
//                printChildrenNames(rootNodeList.get(i), espace + "    ");
            }
        }
    }
}
