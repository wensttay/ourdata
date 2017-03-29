package br.ifpb.simba.ourdata.core.temporal.heideltime;

import de.unihd.dbs.heideltime.standalone.DocumentType;
import de.unihd.dbs.heideltime.standalone.HeidelTimeStandalone;
import de.unihd.dbs.heideltime.standalone.OutputType;
import de.unihd.dbs.heideltime.standalone.POSTagger;
import de.unihd.dbs.heideltime.standalone.components.impl.TimeMLResultFormatter;
import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import de.unihd.dbs.uima.annotator.heideltime.resources.Language;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Class responsable to manage the HeidelTime processes.
 *
 * @see HeidelTimeToken
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 28/03/2017, 18:42:56
 */
public class HeidelTimeEngine {

    private HeidelTimeStandalone heidelTimeStandalone;

    public HeidelTimeEngine(String configPropsPath) {
        this.heidelTimeStandalone = new HeidelTimeStandalone(Language.ENGLISH, DocumentType.NEWS,
                OutputType.TIMEML, configPropsPath, POSTagger.NO, false);
    }

    public HeidelTimeEngine(String configPropsPath, Language language) {
        this.heidelTimeStandalone = new HeidelTimeStandalone(language, DocumentType.NEWS,
                OutputType.TIMEML, configPropsPath, POSTagger.NO, false);
    }

    public HeidelTimeEngine(HeidelTimeStandalone heidelTimeStandalone) {
        this.heidelTimeStandalone = heidelTimeStandalone;
    }

    /**
     * Fill a HeidelTimeToken with values in an {@link org.jdom.Element} passed
     *
     * @param timevalue element with values.
     *
     * @return A HeidelTimeToken with filled values.
     */
    private HeidelTimeToken fillHeidelTimeToken(Element timevalue) {
        HeidelTimeToken stv = new HeidelTimeToken();
        stv.setData(timevalue.getText());
        stv.setId(timevalue.getAttributeValue("tid"));
        stv.setType(timevalue.getAttributeValue("type"));
        stv.setValue(timevalue.getAttributeValue("value"));
        return stv;
    }

    /**
     * Process a text to obteins a list of
     * {@link br.ifpb.simba.ourdata.core.temporal.heideltime.HeidelTimeToken}
     * with all temporal terms detected by HeidelTime algorithm basing in a
     * date.
     *
     * @param text text to be processed.
     * @param currentData date to be based.
     *
     * @return A list of
     * {@link br.ifpb.simba.ourdata.core.temporal.heideltime.HeidelTimeToken}
     * with all temporal terms detected by HeidelTime.
     *
     * @throws JDOMException when error occur in XML parsing.
     * @throws IOException when an I/O error prevents a document from being
     * fully parsed.
     * @throws DocumentCreationTimeMissingException Exception thrown if document
     * creation time is missing while processing a document of type
     * {@link de.unihd.dbs.heideltime.standalone.DocumentType#NEWS}.
     */
    public List<HeidelTimeToken> process(String text, Date currentData) throws JDOMException, IOException, DocumentCreationTimeMissingException {
        List<HeidelTimeToken> heidelTimeTokens = new ArrayList<>();
        List listtimevalues = processText(text, currentData);
        Iterator i = listtimevalues.iterator();

        while (i.hasNext()) {
            Element timevalue = (Element) i.next();
            HeidelTimeToken stv = fillHeidelTimeToken(timevalue);
            heidelTimeTokens.add(stv);
        }

        return heidelTimeTokens;
    }

    /**
     * Process a text to obteins a list with all temporal terms detected by
     * HeidelTime algorithm basing in a date. If the curretDate passed is null
     * this method change the DocumentType for
     * {@link de.unihd.dbs.heideltime.standalone.DocumentType#NARRATIVES}.
     *
     * @param text text to be processed.
     * @param currentData date to be based.
     *
     * @return A list of with all temporal Elements detected by HeidelTime.
     *
     * @throws JDOMException when error occur in XML parsing.
     * @throws IOException when an I/O error prevents a document from being
     * fully parsed.
     * @throws DocumentCreationTimeMissingException Exception thrown if document
     * creation time is missing while processing a document of type
     * {@link de.unihd.dbs.heideltime.standalone.DocumentType#NEWS}.
     */
    private List processText(String text, Date currentData) throws JDOMException, DocumentCreationTimeMissingException, IOException {

        if (text == null || text.isEmpty() || text.equals("")) {
            return Collections.EMPTY_LIST;
        }

        String xml = "";

        if (currentData != null) {
            getHeidelTimeStandalone().setDocumentType(DocumentType.NEWS);
            xml = getHeidelTimeStandalone().process(text, currentData,
                    new TimeMLResultFormatter());
        } else {
            
            // If currentData == null, chante DocumentType to DocumentType.NARRATIVES, 
            // to process without based date.
            getHeidelTimeStandalone().setDocumentType(DocumentType.NARRATIVES);
            xml = getHeidelTimeStandalone().process(text,
                    new TimeMLResultFormatter());
        }
        
        // Process the XML result to List
        Reader reader = new StringReader(xml);
        Document document = new SAXBuilder().build(reader);
        Element element = (Element) document.getRootElement();
        List list = element.getChildren();

        reader.close();
        return list;
    }

    /**
     * @return the heidelTimeStandalone
     */
    public HeidelTimeStandalone getHeidelTimeStandalone() {
        return heidelTimeStandalone;
    }

    /**
     * @param heidelTimeStandalone the heidelTimeStandalone to set
     */
    public void setHeidelTimeStandalone(HeidelTimeStandalone heidelTimeStandalone) {
        this.heidelTimeStandalone = heidelTimeStandalone;
    }

}
