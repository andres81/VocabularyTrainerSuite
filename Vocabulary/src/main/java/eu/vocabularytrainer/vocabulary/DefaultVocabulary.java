/**
 * VocabularyTrainer  Copyright (C) 2015  André Schepers
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.vocabularytrainer.vocabulary;

import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import java.util.List;
import java.util.ArrayList;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import generated.Lesson;
import generated.Pairtype;
import generated.Vocabularytype;
import java.io.File;
import java.io.Serializable;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;


/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public class DefaultVocabulary implements Vocabulary {
    
    /**
     * 
     */
    private List<VocabularyElementPair> pairs;
    
    /**
     * 
     */
    private DefaultVocabulary() {
    }
    
    /**
     * 
     * @param fileName
     * @return 
     */
    public static Vocabulary createFromXML(String fileName) {
        System.out.println("start createFromXml");
        Vocabulary voc = new DefaultVocabulary();
        Lesson lesson;
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
            Schema schema = sf.newSchema(new File("src/main/resources/lesson.xsd")); 
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Lesson.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setSchema(schema);
            lesson = (Lesson) jaxbUnmarshaller.unmarshal(file);
            System.out.println(lesson);
        } catch (JAXBException | SAXException e) {
            System.out.println("exception: ");
            e.printStackTrace();
            return null;
        }
        
        Vocabularytype voctype = null;
        for (Serializable ser : lesson.getContent()) {
            if (ser instanceof JAXBElement) {
                voctype = ((JAXBElement<Vocabularytype>) ser).getValue();
                break;
            }
        }
        if (voctype == null) {
            System.out.println("voctype = null");
            return null;
        }
        List<VocabularyElementPair> pairs = new ArrayList<>();
        VocabularyElementPair pair;
        for (Pairtype pt : voctype.getPairs().getPair()) {
            Representative first = new DefaultRepresentative(pt.getFirst().getText(), null);
            Representative second = new DefaultRepresentative(pt.getSecond().getText(), null);
            pair = new DefaultVocabularyElementPair(first, second);
            pairs.add(pair);
        }
        voc.setPairs(pairs);
        return voc;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public List<VocabularyElementPair> getPairs() {
        return pairs;
    }
    
    /**
     * 
     * @param pairs 
     */
    @Override
    public void setPairs(List<VocabularyElementPair> pairs) {
        if (pairs == null) {
            throw new NullPointerException();
        }
        this.pairs = pairs;
    }
    
    /**
     * 
     * @param pair 
     */
    public void addPair(VocabularyElementPair pair) {
        if (pair == null) {
            throw new NullPointerException();
        }
        if (pairs == null) {
            pairs = new ArrayList<>();
        }
        pairs.add(pair);
    }
}
