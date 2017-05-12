package explorador;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 *
 * @author Daniel
 */
public class XMLManager {
    public void crear(){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root element
            Document doc = docBuilder.newDocument();
            doc.setXmlVersion("1.0");
            doc.setXmlStandalone(false);
            Element rootElement = doc.createElement("directorio");
            doc.appendChild(rootElement);
            Attr attr = doc.createAttribute("id");
            attr.setValue(String.valueOf(Explorador.getCurrentId()));
            rootElement.setAttributeNode(attr);
            
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("arbol.xml"));                  
            transformer.transform(source, result);
            System.out.println("File saved!");
            Explorador.setCurrentId(Explorador.getCurrentId() + 1);
        } catch (ParserConfigurationException | TransformerException pce) {
            System.err.print(pce.getMessage());
        }
    }
    
    public void guardar(){
        try{
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(Explorador.arbol);
            StreamResult result = new StreamResult(new File("arbol.xml"));                  
            transformer.transform(source, result);
            System.out.println("File saved!");
        }catch(Exception e){
            
        }
    }
}		