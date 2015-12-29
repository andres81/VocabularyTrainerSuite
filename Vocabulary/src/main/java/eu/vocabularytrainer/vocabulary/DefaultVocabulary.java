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

import eu.vocabularytrainer.vocabulary.interfaces.Iteration;
import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public class DefaultVocabulary implements Vocabulary {
    
    /**
     * 
     */
    private List<VocabularyElementPair> vocabularyPairs;
    
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
     * @return 
     */
    @Override
    public List<VocabularyElementPair> getPairs() {
        return vocabularyPairs;
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
        this.vocabularyPairs = pairs;
    }
    
    /**
     * 
     * @param pair 
     */
    public void addPair(VocabularyElementPair pair) {
        if (pair == null) {
            throw new NullPointerException();
        }
        if (vocabularyPairs == null) {
            vocabularyPairs = new ArrayList<>();
        }
        vocabularyPairs.add(pair);
    }

    /**
     * 
     * @param iterations 
     */
    @Override
    public void setIterations(List<Iteration> iterations) {
        this.iterations = iterations;
        Collections.sort(this.iterations, new Comparator<Iteration>() {
            @Override
            public int compare(Iteration t, Iteration t1) {
                int ti1 = t.getIndex();
                int ti2 = t.getIndex();
                if (ti1 < ti2) return -1;
                if (ti1 == ti2) return 0;
                return 1;
            }
        });
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public List<Iteration> getIterations() {
        return iterations;
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public Representative.Representation getQueryRepresentation(int index) {
      return getIterations().get(index/5).getQueryType();
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public Representative.Representation getOptionsRepresentation(int index) {
      return getIterations().get(index/5).getOptionType();
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public Direction getPairsGroupDirection(int index) {
      return getIterations().get(index/5).getColumnOrder();
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public List<VocabularyElementPair> getPairs(int index) {
      List<VocabularyElementPair> pairs = new ArrayList<>();
      int pc = vocabularyPairs.size();
      int nofGroupsPerIteration = pc / 5 + (pc%5 == 0 ? 0 : 1);
      int reducedIndex = index % nofGroupsPerIteration;
      for (int i=reducedIndex;i<reducedIndex+5;++i) {
        if (i<pc) {
          pairs.add(vocabularyPairs.get(i));
        } else {
          pairs.add(vocabularyPairs.get(i%pc));
        }
      }
      return pairs;
    }
    
}
