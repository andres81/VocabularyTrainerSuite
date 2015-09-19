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
package eu.vocabularytrainer.vocabulary;

import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import java.util.List;
import java.util.ArrayList;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import generated.Lesson;
import generated.Pairelemtype;
import generated.Pairtype;
import generated.Vocabularytype;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import javax.imageio.ImageIO;
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
     * @param file
     * @return 
     */
    public static Vocabulary createFromXML(File file) {
        Vocabulary voc = new DefaultVocabulary();
        Lesson lesson;
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
            Schema schema = sf.newSchema(new File("src/main/resources/lesson.xsd")); 
            JAXBContext jaxbContext = JAXBContext.newInstance(Lesson.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setSchema(schema);
            lesson = (Lesson) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException | SAXException e) {
            e.printStackTrace(System.err);
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
            return null;
        }
        List<VocabularyElementPair> pairs = new ArrayList<>();
        VocabularyElementPair pair;
        for (Pairtype pt : voctype.getPairs().getPair()) {
            Pairelemtype pet1 = pt.getFirst();
            Pairelemtype pet2 = pt.getSecond();
            Representative first = new DefaultRepresentative(pet1.getText(), getImageFromUrlString(pet1.getImage()), pet1.getAudio());
            Representative second = new DefaultRepresentative(pet2.getText(), getImageFromUrlString(pet2.getImage()), pet2.getAudio());
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

    /**
     * 
     * @param imageUrlString
     * @return 
     */
    public static Image getImageFromUrlString(String imageUrlString) {
        URL url = null;
        try {
            url = new URL(imageUrlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getImageFromUrl(url);
    }
    
    /**
     * 
     * @param imageUrl
     * @return 
     */
    public static Image getImageFromUrl(URL imageUrl) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
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
}
