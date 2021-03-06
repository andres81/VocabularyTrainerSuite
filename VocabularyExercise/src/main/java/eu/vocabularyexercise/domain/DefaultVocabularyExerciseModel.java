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


import eu.vocabularyexercise.domain.interfaces.VocabularyExerciseModel;
import eu.vocabularytrainer.vocabulary.DefaultRepresentative;
import eu.vocabularytrainer.vocabulary.DefaultVocabularyElementPair;
import eu.vocabularytrainer.vocabulary.interfaces.Iteration;
import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;
import static eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction.COLUMNONETOONE;
import static eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction.COLUMNONETOTWO;
import static eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction.COLUMNTWOTOONE;
import static eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction.COLUMNTWOTOTWO;
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
public class DefaultVocabularyExerciseModel extends Observable implements VocabularyExerciseModel {
    
    // Logging
    private static final Logger logger = LogManager.getLogger(DefaultVocabularyExerciseModel.class);
    
    /**
     * 
     */
    private Map<UUID,VocabularyElementPair> activePairs = null;
    
    /**
     * 
     */
    private Map<UUID,VocabularyElementPair> vocabularyPairs = null;
    
    /**
     * 
     */
    private Representative activeQuery = null;

    /**
     * 
     */
    private VocabularyElementPair activeQueryPair = null;
            
    /**
     * 
     */
    private Representative activeQueryOption = null;
    
    /**
     * 
     */
    private List<Representative> options = null;
            
    /**
     * 
     */
    private Direction direction = null;
    
    /**
     * 
     */
    private Representation queryRepresentation = null;
    
    /**
     * 
     */
    private Representation optionsRepresentation = null;
    
    /**
     * 
     */
    private int pairStartIndex = 1;
    
    /**
     * 
     */
    private int pairEndIndex = 1;
    
    /**
     * 
     */
    private List<Iteration> iterations = null;
    private int iterationIndex;
    
    /**
     * 
     */
    public DefaultVocabularyExerciseModel() {
        direction = Direction.COLUMNONETOTWO;
        optionsRepresentation = Representation.STRING;
        queryRepresentation = Representation.STRING;
    }
    
    /**
     * s
     * @param direction 
     */
    @Override
    public void setDirection(Direction direction) {
        if (direction == null) {
            throw new NullPointerException();
        }
        this.direction = direction;
        updateQueryAndQueryOption();
        updateOptions();
        setChanged();
        notifyObservers(UpdateType.DIRECTION);
    }
    
    /**
     * 
     * @param representation 
     */
    @Override
    public void setQueryRepresentation(Representation representation) {
        this.queryRepresentation = representation;
        setChanged();
        notifyObservers(UpdateType.QUERYINTERACTIONTYPE);
    }
    
    /**
     * 
     * @param representation 
     */
    @Override
    public void setOptionsRepresentation(Representation representation) {
        this.optionsRepresentation = representation;
        setChanged();
        notifyObservers(UpdateType.OPTIONSINTERACTIONTYPE);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Representation getQueryRepresentation() {
        return queryRepresentation;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Representation getOptionsRepresentation() {
        return optionsRepresentation;
    }
    
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
        queryRepresentation = iteration.getQueryType();
        optionsRepresentation = iteration.getOptionType();
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
     * 
     */
    @Override
    public void shiftToPreviousPairs() {
        if (pairStartIndex == 0) {
            if (iterationIndex == 0) return;
            iterationIndex = iterationIndex - 1;
            initIteration();
            pairStartIndex = vocabularyPairs.size() - (vocabularyPairs.size() % 5);
        } else {
            pairStartIndex = pairStartIndex - 5;
            if (pairStartIndex < 0) pairStartIndex = 0;
            pairEndIndex = pairStartIndex + 4;
        }
        updateActivePairs();
        updateOptions();
        setRandomActiveQueryPairNoUpdate();
        setChanged();
        notifyObservers(UpdateType.PAIRS);
    }
    
    /**
     * 
     */
    @Override
    public void shiftToNextPairs() {
        if (pairEndIndex == vocabularyPairs.size() - 1) {
            // Next iteration
            if (iterationIndex == iterations.size() - 1) return;
            iterationIndex = iterationIndex + 1;
            initIteration();
            pairStartIndex = 0;
        } else {
            pairStartIndex = pairEndIndex + 1;
        }
        updateActivePairs();
        updateOptions();
        setRandomActiveQueryPairNoUpdate();
        setChanged();
        notifyObservers(UpdateType.PAIRS);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public List<VocabularyElementPair> getVocabularyElementPairs() {
        return new ArrayList<>(activePairs.values());
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Representative getActiveQuery() {
        if (activeQuery == null) {
            activeQuery = new DefaultRepresentative();
        }
        return activeQuery;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Representative getActiveQueryOption() {
        if (activeQueryOption == null) {
            activeQueryOption = new DefaultRepresentative();
        }
        return activeQueryOption;
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
     * @return 
     */
    @Override
    public VocabularyElementPair getActiveQueryPair() {
        if (activeQueryPair == null) {
            activeQueryPair = new DefaultVocabularyElementPair(new DefaultRepresentative(), new DefaultRepresentative());
        }
        return activeQueryPair;
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
     * 
     * @param uuid 
     */
    private void setActiveQueryPairNoUpdate(UUID uuid) {
        if (uuid == null) {
            throw new NullPointerException();
        }
        VocabularyElementPair pair = activePairs.get(uuid);
        if (pair == null) return; // No pair found with given uuid!
        activeQueryPair = pair;
        updateQueryAndQueryOption();
    }
    
    /**
     * 
     */
    private void updateQueryAndQueryOption() {
        if (activeQueryPair == null) return;
        Representative first = activeQueryPair.getFirst();
        Representative second = activeQueryPair.getSecond();
        switch(direction) {
            case COLUMNONETOONE:
                activeQuery = first;
                activeQueryOption = first;
                break;
            case COLUMNONETOTWO:
                activeQuery = first;
                activeQueryOption = second;
                break;
            case COLUMNTWOTOONE:
                activeQuery = second;
                activeQueryOption = first;
                break;
            case COLUMNTWOTOTWO:
                activeQuery = second;
                activeQueryOption = second;
                break;
        }
    }
    
    /**
     * Set a new random active query pair.
     */
    private void setRandomActiveQueryPairNoUpdate() {
        if (activePairs == null ||
            activePairs.isEmpty()) {
            activeQueryPair = null;
            activeQuery = null;
            activeQueryOption = null;
            return;
        }
        List<VocabularyElementPair> temp = new ArrayList<>(activePairs.values());
        if (activeQueryPair != null &&
            temp.size() > 1) {
            temp.remove(activeQueryPair);
        }
        Random r = new Random();
        int newIndex = r.nextInt(temp.size());
        setActiveQueryPairNoUpdate(temp.get(newIndex).getUuid());
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
     * @return 
     */
    @Override
    public List<Representative> getOptions() {
        return options;
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
}
