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
package eu.vocabularyexercise.domain.interfaces;

import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import java.util.List;
import java.util.Observer;
import java.util.UUID;

/**
 *
 * @author andre
 */
public interface VocabularyModel {
    
    /**
     * 
     * @param direction 
     */
    public void setDirection(Direction direction);
    
    /**
     * 
     * @param pairs 
     */
    public void setVocabularyElementPairs(List<VocabularyElementPair> pairs);
    
    /**
     * 
     * @return 
     */
    public List<VocabularyElementPair> getVocabularyElementPairs();
    
    /**
     * 
     * @return 
     */
    public Representative getActiveQuery();
    
    /**
     * 
     * @return 
     */
    public Representative getActiveQueryOption();
    
    /**
     * 
     */
    public void setRandomActiveQueryPair();
    
    /**
     * 
     * @param uuid 
     */
    public void setActiveQueryPair(UUID uuid);
    
    /**
     * 
     * @return 
     */
    public VocabularyElementPair getActiveQueryPair();
    
    /**
     * 
     * @param o 
     */
    public void addObserver(Observer o);

    /**
     * 
     * @return 
     */
    public List<Representative> getOptions();
}
