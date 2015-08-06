/**
 * VocabularyTrainer Copyright (C) 2015 André Schepers andreschepers81@gmail.com
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.filfa.lessoncreator;

import java.io.File;
import java.util.Arrays;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import generated.Lesson;
import generated.ObjectFactory;
import generated.Vocabularytype;
import generated.Iterationstype;
import generated.Instructionstype;
import generated.Iterationtype;
import generated.Presentationtype;
import generated.Vocelemtype;
import generated.Columnordertype;
import generated.Pairstype;
import generated.Pairtype;
import generated.Pairelemtype;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public class VocabularyXMLApi {
    
    /**
     * 
     */
    private static Lesson lesson = new Lesson();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        lesson.setId("1");
        lesson.setName("Alphabet");
        
        
        ObjectFactory obFac = new ObjectFactory();
        lesson.getContent().add(obFac.createLessonVocabulary(getVocabulary()));
        
        // Code template start.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(lesson.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "lesson.xsd");
            SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            marshaller.setSchema(sf.newSchema(new File("src/main/resources/lesson.xsd")));
            marshaller.marshal(lesson, System.out);
        } catch (javax.xml.bind.JAXBException ex) {
            System.err.println("Went something wrong with marshalling the custom xml piece of shit of code: " + ex.getMessage());
            System.err.println(ex.toString());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        } catch (SAXException ex) {
            Logger.getLogger(VocabularyXMLApi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @return 
     */
    private static Vocabularytype getVocabulary() {
        Vocabularytype voc = new Vocabularytype();
        voc.setInstructions(getInstructions());
        voc.setPairs(getPairs());
        return voc;
    }
    
    /**
     * 
     * @return 
     */
    private static Instructionstype getInstructions() {
        Instructionstype instructions = new Instructionstype();
        instructions.setIterations(getIterations());
        return instructions;
    }
    
    /**
     * 
     * @return 
     */
    private static Iterationstype getIterations() {
        // List of iterations
        Iterationstype iterations = new Iterationstype();
        iterations.getIteration().add(getIteration1());
        iterations.getIteration().add(getIteration2());
        iterations.getIteration().add(getIteration3());
        iterations.getIteration().add(getIteration4());
        return iterations;
    }
    
    /**
     * 
     * @return 
     */
    private static Iterationtype getIteration1() {
        // Iteration 1
        Iterationtype iteration1 = new Iterationtype();
        iteration1.setIndex(0);
        Presentationtype presType1 = new Presentationtype();
        iteration1.setQuery(presType1);
        presType1.setType(Vocelemtype.IMAGE);
        Presentationtype presType2 = new Presentationtype();
        iteration1.setOptions(presType2);
        presType2.setType(Vocelemtype.TEXT);
        iteration1.setColumnorder(Columnordertype.FIRST_FIRST);
        return iteration1;
    }
    
    /**
     * 
     * @return 
     */
    private static Iterationtype getIteration2() {
        // Iteration 2
        Iterationtype iteration2 = new Iterationtype();
        iteration2.setIndex(1);
        Presentationtype presType1 = new Presentationtype();
        iteration2.setQuery(presType1);
        presType1.setType(Vocelemtype.TEXT);
        Presentationtype presType2 = new Presentationtype();
        iteration2.setOptions(presType2);
        presType2.setType(Vocelemtype.TEXT);
        iteration2.setColumnorder(Columnordertype.SECOND_FIRST);
        return iteration2;
    }
    
    /**
     * 
     * @return 
     */
    private static Iterationtype getIteration3() {
        // Iteration 3
        Iterationtype iteration3 = new Iterationtype();
        iteration3.setIndex(2);
        Presentationtype presType1 = new Presentationtype();
        iteration3.setQuery(presType1);
        presType1.setType(Vocelemtype.TEXT);
        Presentationtype presType2 = new Presentationtype();
        iteration3.setOptions(presType2);
        presType2.setType(Vocelemtype.TEXT);
        iteration3.setColumnorder(Columnordertype.FIRST_SECOND);
        return iteration3;
    }
    
    /**
     * 
     * @return 
     */
    private static Iterationtype getIteration4() {
        // Iteration 4
        Iterationtype iteration4 = new Iterationtype();
        iteration4.setIndex(3);
        Presentationtype presType1 = new Presentationtype();
        iteration4.setQuery(presType1);
        presType1.setType(Vocelemtype.TEXT);
        Presentationtype presType2 = new Presentationtype();
        iteration4.setOptions(presType2);
        presType2.setType(Vocelemtype.TEXT);
        iteration4.setColumnorder(Columnordertype.SECOND_SECOND);
        return iteration4;
    }
    
    /**
     * 
     * @return 
     */
    private static Pairstype getPairs() {
        Pairstype pairs = new Pairstype();
        pairs.getPair().add(getPair1());
        pairs.getPair().add(getPair2());
        pairs.getPair().add(getPair3());
        pairs.getPair().add(getPair4());
        return pairs;
    }
    
    /**
     * 
     * @return 
     */
    private static Pairtype getPair1() {
        Pairtype pair = new Pairtype();
        Pairelemtype first = new Pairelemtype();
        first.setText("test");
        pair.setFirst(first);
        Pairelemtype second = new Pairelemtype();
        second.setText("test");
        pair.setSecond(second);
        return pair;
    }
    
    /**
     * 
     * @return 
     */
    private static Pairtype getPair2() {
        Pairtype pair = new Pairtype();
        Pairelemtype first = new Pairelemtype();
        first.setText("test");
        pair.setFirst(first);
        Pairelemtype second = new Pairelemtype();
        second.setText("test");
        pair.setSecond(second);
        return pair;
    }
    
    /**
     * 
     * @return 
     */
    private static Pairtype getPair3() {
        Pairtype pair = new Pairtype();
        Pairelemtype first = new Pairelemtype();
        first.setText("test");
        pair.setFirst(first);
        Pairelemtype second = new Pairelemtype();
        second.setText("test");
        pair.setSecond(second);
        return pair;
    }
    
    /**
     * 
     * @return 
     */
    private static Pairtype getPair4() {
        Pairtype pair = new Pairtype();
        Pairelemtype first = new Pairelemtype();
        first.setText("test");
        pair.setFirst(first);
        Pairelemtype second = new Pairelemtype();
        second.setText("test");
        pair.setSecond(second);
        return pair;
    }
}