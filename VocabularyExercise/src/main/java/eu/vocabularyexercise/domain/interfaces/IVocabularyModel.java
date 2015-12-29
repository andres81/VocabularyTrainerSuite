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
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import java.util.List;
import java.util.Observer;
import java.util.UUID;

/**
 *
 * @author andre
 */
public interface IVocabularyModel {
    
    /**
     * 
     * @param index
     * @return 
     */
    public Representative.Representation getQueryRepresentation(int index);
    
    /**
     * 
     * @param index
     * @return 
     */
    public Representative.Representation getOptionsRepresentation(int index);
    
    /**
     * 
     * @param vocabulary 
     */
    public void setVocabulary(Vocabulary vocabulary);
    
    /**
     * 
     */
    public void setRandomActiveQueryPair();
    
    /**
     * 
     * @return 
     */
    public VocabularyElementPair getActivePair();
    
    /**
     * 
     * @param uuid 
     */
    public void setActivePair(UUID uuid);
    
    /**
     * 
     * @return 
     */
    public List<VocabularyElementPair> getActivePairGroup();
    
    /**
     * 
     * @param o 
     */
    public void addObserver(Observer o);

    /**
     * 
     * @param index
     * @return 
     */
    public Direction getPairsGroupDirection(int index);
    
}
