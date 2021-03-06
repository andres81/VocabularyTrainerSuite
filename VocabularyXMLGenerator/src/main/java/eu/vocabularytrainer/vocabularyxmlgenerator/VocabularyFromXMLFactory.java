package eu.vocabularytrainer.vocabularyxmlgenerator;

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
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.vocabularytrainer.vocabulary.DefaultIterationImpl;
import eu.vocabularytrainer.vocabulary.DefaultRepresentative;
import eu.vocabularytrainer.vocabulary.DefaultVocabulary;
import eu.vocabularytrainer.vocabulary.DefaultVocabularyElementPair;
import eu.vocabularytrainer.vocabulary.interfaces.Iteration;
import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import generated.Columnordertype;
import java.util.List;
import java.util.ArrayList;
import generated.Iterationtype;
import generated.Lesson;
import generated.Pairelemtype;
import generated.Pairtype;
import generated.Vocabularytype;
import generated.Vocelemtype;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;


/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public class VocabularyFromXMLFactory {
    
    /**
     * 
     * @param in
     * @return 
     */
    public static Vocabulary createFromXML(InputStream in) {
        Vocabulary voc = new DefaultVocabulary();
        Vocabularytype voctype = getVocabularyType(getLesson(in));
        if (voctype == null) {
            return null;
        }
        voc.setPairs(getPairs(voctype));
        voc.setIterations(getIterations(voctype));
        return voc;
    }
    
    /**
     * 
     * @param in
     * @return 
     */
    private static Lesson getLesson(InputStream in) {
        Lesson lesson;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Lesson.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
            Schema schema = sf.newSchema(new StreamSource(VocabularyFromXMLFactory.class.getResourceAsStream("/lesson.xsd"))); 
            jaxbUnmarshaller.setSchema(schema);
            lesson = (Lesson) jaxbUnmarshaller.unmarshal(in);
        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
            System.err.println("Could not load lesson from xml!");
            return null; // TODO throw exception to be handled up the line.
        }
        return lesson;
    }
    
    /**
     * 
     * @param lesson
     * @return 
     */
    private static Vocabularytype getVocabularyType(Lesson lesson) {
        Vocabularytype voctype = null;
        for (Serializable ser : lesson.getContent()) {
            if (ser instanceof JAXBElement) {
                voctype = ((JAXBElement<Vocabularytype>) ser).getValue();
                break;
            }
        }
        return voctype;
    }
    
    /**
     * 
     * @param voctype
     * @return 
     */
    private static List<Iteration> getIterations(Vocabularytype voctype) {
        List<Iteration> iterations = new ArrayList<>();
        for (Iterationtype type : voctype.getInstructions().getIterations().getIteration()) {
            iterations.add(getIteration(type));
        }
        return iterations;
    }
    
    /**
     * 
     * @param type
     * @return 
     */
    private static Iteration getIteration(Iterationtype type) {
        Iteration iteration = new DefaultIterationImpl();
        iteration.setIndex(type.getIndex());
        iteration.setColumnOrder(columnOrderTypeToDirection(type.getColumnorder()));
        iteration.setOptionType(vocelemtypeToVocabularyElementType(type.getOptions().getType()));
        iteration.setQueryType(vocelemtypeToVocabularyElementType(type.getQuery().getType()));
        return iteration;
    }
    
    /**
     * 
     * @param type
     * @return 
     */
    private static Representation vocelemtypeToVocabularyElementType(Vocelemtype type) {
      switch (type) {
        case AUDIO:
          return Representation.SOUND;
        case IMAGE:
          return Representation.IMAGE;
        case TEXT:
          return Representation.STRING;
        default:
          return Representation.STRING;
      }
    }
    
    /**
     * 
     * @param type
     * @return 
     */
    private static Direction columnOrderTypeToDirection(Columnordertype type) {
      switch (type) {
        case FIRST_FIRST:
          return Direction.COLUMNONETOONE;
        case FIRST_SECOND:
          return Direction.COLUMNONETOTWO;
        case SECOND_FIRST:
          return Direction.COLUMNTWOTOONE;
        case SECOND_SECOND:
          return Direction.COLUMNTWOTOTWO;
        default:
          return Direction.COLUMNONETOONE;
      }
    }
    
    /**
     * 
     * @param voctype
     * @return 
     */
    private static List<VocabularyElementPair> getPairs(Vocabularytype voctype) {
        List<VocabularyElementPair> pairs = new ArrayList<>();
        for (Pairtype pt : voctype.getPairs().getPair()) {
            pairs.add(getPair(pt));
        }
        return pairs;
    }
    
    /**
     * 
     * @param pt
     * @return 
     */
    private static VocabularyElementPair getPair(Pairtype pt) {
        
        Pairelemtype pet1 = pt.getFirst();
        Pairelemtype pet2 = pt.getSecond();
        
        Representative first = 
                new DefaultRepresentative(
                        pet1.getText(),
                        pet1.getImage(),
                        pet1.getAudio());
        
        Representative second =
                new DefaultRepresentative(
                        pet2.getText(),
                        pet2.getImage(),
                        pet2.getAudio());
        
        return new DefaultVocabularyElementPair(first, second);
    }
    
    /**
     * 
     * @param imageUrlString
     * @return 
     */
    public static Image getImageFromUrlString(String imageUrlString) {
        URL url;
        try {
            url = new URL(imageUrlString);
        } catch (IOException e) {
            System.err.println("Could not form url from given image url.");
            return null;
        }
        return getImageFromUrl(url);
    }
    
    /**
     * 
     * @param imageUrl
     * @return 
     */
    public static Image getImageFromUrl(URL imageUrl) {
        BufferedImage image;
        try {
            image = ImageIO.read(imageUrl);
        } catch (IllegalArgumentException | IOException ex) {
            System.err.println("Could not load image from url: " + imageUrl);
            return null;
        }
        int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();

        return resizeImage(image, type);
    }
    
    /**
     * 
     * @param originalImage
     * @param type
     * @return 
     */
   private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        
        double orgHeight = originalImage.getHeight();
        double orgWidth = originalImage.getWidth();
        double ar = orgWidth / orgHeight;
        int width = (int)(100.0 * ar);
        
        BufferedImage resizedImage = new BufferedImage(width, 100, type);
        Graphics2D g = resizedImage.createGraphics();
              g.drawImage(originalImage,
                          // Area to draw on
                          0, 0, width, 100,
                          // part of the original image we take, full of course.
                          0, 0, originalImage.getWidth(), originalImage.getHeight(),
                          // Optional observer that is called when image is fully drawn.
                          null);
        g.dispose();
        return resizedImage;
    }
   
   public static void main(String[] args) throws FileNotFoundException, JsonProcessingException, IOException {
      Vocabulary vocabularyFromXml = VocabularyFromXMLFactory.createFromXML(new FileInputStream("/home/andres81/swdev/VocabularyTrainerSuite/MainWindow/src/main/resources/rus-lesson1-alfabet.xml"));
      File file = new File("/home/andres81/rus_lesson2.json");
      ObjectMapper mapper = new ObjectMapper();
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, vocabularyFromXml);
    }
}
