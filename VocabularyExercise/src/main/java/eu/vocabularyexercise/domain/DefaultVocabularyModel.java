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
package eu.vocabularyexercise.domain;


import eu.vocabularyexercise.domain.interfaces.IVocabularyModel;
import eu.vocabularytrainer.vocabulary.interfaces.Iteration;
import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.UpdateType;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 *
 * @author andres81
 */
public class DefaultVocabularyModel extends Observable implements IVocabularyModel {
    
    // Logging
    private static final Logger logger = LogManager.getLogger(DefaultVocabularyExerciseModel.class);
    private Map<UUID,VocabularyElementPair> activePairs = null;
    private Map<UUID,VocabularyElementPair> vocabularyPairs = null;
    private VocabularyElementPair activeQueryPair = null;
    private List<Representative> options = null;
    private Direction direction = null;
    private int pairStartIndex = 1;
    private int pairEndIndex = 1;
    private List<Iteration> iterations = null;
    private int iterationIndex;
    
    /**
     * 
     * @param vocabulary 
     */
    @Override
    public void setVocabulary(Vocabulary vocabulary) {
        setVocabularyElementPairs(vocabulary.getPairs());
        setIterations(vocabulary.getIterations());
        iterationIndex = 0;
        initIteration();
        updateActivePairs();
        updateOptions();
        setRandomActiveQueryPairNoUpdate();
        setChanged();
        notifyObservers(UpdateType.PAIRS);
    }
    
    /**
     * 
     */
    private void initIteration() {
        Iteration iteration = iterations.get(iterationIndex);
        direction = iteration.getColumnOrder();
    }
    
    /**
     * 
     * @param pairs
     */
    private void setVocabularyElementPairs(List<VocabularyElementPair> pairs) {
        if (pairs == null) {
            throw new NullPointerException();
        }
        if (this.vocabularyPairs == null) {
            this.vocabularyPairs = new LinkedHashMap<>();
        }
        this.vocabularyPairs.clear();
        for (VocabularyElementPair pair : pairs) {
            this.vocabularyPairs.put(pair.getUuid(), pair);
        }
        if (vocabularyPairs.size() < 1) return;
        pairStartIndex = 0;
    }
    
    /**
     * 
     */
    private void updateActivePairs() {
        if (vocabularyPairs == null) return;
        if (activePairs == null) {
            activePairs = new LinkedHashMap<>();
        }
        pairEndIndex = pairStartIndex + 4;
        int size = vocabularyPairs.size();
        pairEndIndex = (pairEndIndex < size) ? pairEndIndex : (size - 1);
        activePairs.clear();
        LinkedList<UUID> keys = new LinkedList<>(vocabularyPairs.keySet());
        for (int i = pairStartIndex;i<=pairEndIndex;i++) {
            activePairs.put(keys.get(i), vocabularyPairs.get(keys.get(i)));
        }
    }
    
    /**
     * Set a new random active query pair.
     */
    @Override
    public void setRandomActiveQueryPair() {
        setRandomActiveQueryPairNoUpdate();
        setChanged();
        notifyObservers(UpdateType.ACTIVEPAIR);
    }
    
    /**
     * 
     * @param o 
     */
    @Override
    public void addObserver(Observer o) {
       super.addObserver(o);
    }

    /**
     * Set a new random active query pair.
     */
    private void setRandomActiveQueryPairNoUpdate() {
        if (activePairs == null ||
            activePairs.isEmpty()) {
            return;
        }
        List<VocabularyElementPair> temp = new ArrayList<>(activePairs.values());
        if (activeQueryPair != null &&
            temp.size() > 1) {
            temp.remove(activeQueryPair);
        }
        Random r = new Random();
        int newIndex = r.nextInt(temp.size());
    }

    /**
     * 
     */
    private void updateOptions() {
        if (activePairs == null) return;
        options = new ArrayList<>();
        for(VocabularyElementPair pair : activePairs.values()) {
            if (direction == Direction.COLUMNONETOONE ||
                direction == Direction.COLUMNTWOTOONE) {
                options.add(pair.getFirst());
            } else {
                options.add(pair.getSecond());
            }
        }
    }
    
    /**
     * 
     * @param iterations 
     */
    private void setIterations(List<Iteration> iterations) {
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

    @Override
    public Representation getQueryRepresentation(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Representation getOptionsRepresentation(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VocabularyElementPair getActivePair() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
