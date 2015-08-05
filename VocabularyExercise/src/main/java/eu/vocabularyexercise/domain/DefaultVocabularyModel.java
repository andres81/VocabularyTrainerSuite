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
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.vocabularyexercise.domain;


import eu.vocabularyexercise.domain.interfaces.VocabularyModel;
import eu.vocabularytrainer.vocabulary.DefaultRepresentative;
import eu.vocabularytrainer.vocabulary.DefaultVocabularyElementPair;
import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;
import static eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction.COLUMNONETOONE;
import static eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction.COLUMNONETOTWO;
import static eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction.COLUMNTWOTOONE;
import static eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction.COLUMNTWOTOTWO;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.UpdateType;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import java.util.ArrayList;
import java.util.HashMap;
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
public class DefaultVocabularyModel extends Observable implements VocabularyModel {
    
    // Logging
    private static final Logger logger = LogManager.getLogger(DefaultVocabularyModel.class);
    
    /**
     * 
     */
    private Map<UUID,VocabularyElementPair> pairs = null;
    
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
    public DefaultVocabularyModel() {
        direction = Direction.COLUMNONETOTWO;
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
     * @param pairs
     */
    @Override
    public void setVocabularyElementPairs(List<VocabularyElementPair> pairs) {
        if (pairs == null) {
            throw new NullPointerException();
        }
        if (this.pairs == null) {
            this.pairs = new HashMap<>();
        }
        for (VocabularyElementPair pair : pairs) {
            this.pairs.put(pair.getUuid(), new DefaultVocabularyElementPair(pair));
        }
        updateOptions();
        setRandomActiveQueryPair();
        setChanged();
        notifyObservers(UpdateType.PAIRS);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public List<VocabularyElementPair> getVocabularyElementPairs() {
        return new ArrayList<>(pairs.values());
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
     * @param uuid 
     */
    @Override
    public void setActiveQueryPair(UUID uuid) {
        setActiveQueryPairNoUpdate(uuid);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public VocabularyElementPair getActiveQueryPair() {
        if (activeQueryPair == null) {
            activeQueryPair = new DefaultVocabularyElementPair(UUID.randomUUID(), new DefaultRepresentative(), new DefaultRepresentative());
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
        VocabularyElementPair pair = pairs.get(uuid);
        if (pair == null) return; // No pair found with given uuid!
        activeQueryPair = pair;
        updateQueryAndQueryOption();
    }
    
    /**
     * 
     */
    private void updateQueryAndQueryOption() {
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
        if (pairs == null ||
            pairs.isEmpty()) {
            activeQueryPair = null;
            activeQuery = null;
            activeQueryOption = null;
            return;
        }
        List<VocabularyElementPair> temp = new ArrayList<>(pairs.values());
        if (activeQueryPair != null &&
            temp.size() > 1) {
            temp.remove(activeQueryPair);
        }
        Random r = new Random();
        int newIndex = r.nextInt(temp.size());
        setActiveQueryPairNoUpdate(temp.get(newIndex).getUuid());
    }

    private void updateOptions() {
        options = new ArrayList<>();
        for(VocabularyElementPair pair : pairs.values()) {
            if (direction == Direction.COLUMNONETOONE ||
                direction == Direction.COLUMNTWOTOONE) {
                options.add(pair.getFirst());
            } else {
                options.add(pair.getSecond());
            }
        }
    }
    
    @Override
    public List<Representative> getOptions() {
        return options;
    }
}
