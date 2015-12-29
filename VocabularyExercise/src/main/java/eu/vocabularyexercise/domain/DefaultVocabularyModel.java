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
import eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.UUID;



/**
 *
 * @author andres81
 */
public class DefaultVocabularyModel extends Observable implements IVocabularyModel {
    
    private Vocabulary vocabulary = null;
    private VocabularyElementPair activePair = null;
    private List<VocabularyElementPair> activePairGroup = null;
    
    /**
     * 
     * @param vocabulary 
     */
    @Override
    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
        List<VocabularyElementPair> pairs = getActivePairGroup();
        pairs.clear();
        List<VocabularyElementPair> vocabularyPairs = vocabulary.getPairs();
        for (int i=0;i<5&&i<vocabularyPairs.size();++i) {
            pairs.add(vocabularyPairs.get(i));
        }
        if (pairs.size() > 0) {
            setActivePair(pairs.get(0).getUuid());
        }
    }
    
    /**
     * Set a new random active query pair.
     */
    @Override
    public void setRandomActiveQueryPair() {
        List<VocabularyElementPair> pairs = getActivePairGroup();
        if (pairs.size() > 0) {
            Random r = new Random();
            int newIndex = r.nextInt(pairs.size());
            activePair = pairs.get(newIndex);
        }
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
     * @param index
     * @return 
     */
    @Override
    public Representation getQueryRepresentation(int index) {
        //        vocabulary.getIterations().
        return Representation.STRING;
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public Representation getOptionsRepresentation(int index) {
//        vocabulary.getIterations().
        return Representation.STRING;
    }

    @Override
    public Direction getPairsGroupDirection(int index) {
        return Direction.COLUMNONETOTWO;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public VocabularyElementPair getActivePair() {
        return activePair;
    }
    
    /**
     * 
     * @param uuid 
     */
    @Override
    public void setActivePair(UUID uuid) {
        for (VocabularyElementPair pair : activePairGroup) {
            if (pair.getUuid() == uuid) {
                this.activePair = pair;
                return;
            }
        }
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public List<VocabularyElementPair> getActivePairGroup() {
        if (activePairGroup == null) {
            activePairGroup = new ArrayList<>();
        }
        return activePairGroup;
    }
    
}
