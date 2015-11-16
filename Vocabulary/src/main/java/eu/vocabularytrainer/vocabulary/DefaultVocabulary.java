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

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import eu.vocabularytrainer.vocabulary.interfaces.Iteration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;



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
    private List<Iteration> iterations;
    
    /**
     * 
     */
    public DefaultVocabulary() {
    }
    
    /**
     * 
     * @param file
     * @return 
     */
    public static Vocabulary createFromJSON(File file) {
      ObjectMapper mapper = new ObjectMapper();
      Vocabulary vocabulary = null;
      try {
        vocabulary = mapper.readValue(file, DefaultVocabulary.class);
      } catch (JsonGenerationException e) {
        e.printStackTrace();
      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return vocabulary;
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
     * @param iterations 
     */
    @Override
    public void setIterations(List<Iteration> iterations) {
        this.iterations = iterations;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public List<Iteration> getIterations() {
        return iterations;
    }
    
    public static void main(String[] args) throws FileNotFoundException, JsonProcessingException, IOException {
      ObjectMapper mapper = new ObjectMapper();
//      Vocabulary vocabularyFromXml = VocabularyFromXMLFactory.createFromXML(new FileInputStream("/home/andres81/swdev/VocabularyTrainerSuite/MainWindow/src/main/resources/rus-lesson1-alfabet.xml"));
      File file = new File("/home/andres81/rus-lesson1.json");
      File file2 = new File("/home/andres81/rus-lesson2.json");
//      mapper.writerWithDefaultPrettyPrinter().writeValue(file, vocabularyFromXml);
      DefaultVocabulary voc = mapper.readValue(file, DefaultVocabulary.class);
      mapper.writerWithDefaultPrettyPrinter().writeValue(file2, voc);
    }
}
