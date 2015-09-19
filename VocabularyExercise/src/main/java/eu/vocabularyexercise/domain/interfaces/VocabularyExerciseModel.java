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
public interface VocabularyExerciseModel {
    
    /**
     * 
     * @param direction 
     */
    public void setDirection(Direction direction);
    
    /**
     * 
     * @param representation 
     */
    public void setQueryRepresentation(Representative.Representation representation);
    
    /**
     * 
     * @param representation 
     */
    public void setOptionsRepresentation(Representative.Representation representation);
    
    /**
     * 
     * @return 
     */
    public Representative.Representation getQueryRepresentation();
    
    /**
     * 
     * @return 
     */
    public Representative.Representation getOptionsRepresentation();
    
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
    
    /**
     * 
     */
    public void shiftToPreviousPairs();

    /**
     * 
     */
    public void shiftToNextPairs();
}
