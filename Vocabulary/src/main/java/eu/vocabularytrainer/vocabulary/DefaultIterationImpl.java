/**
 * VocabularyTrainer Copyright (C) 2015 André Schepers andreschepers81@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.vocabularytrainer.vocabulary;

import eu.vocabularytrainer.vocabulary.interfaces.Iteration;
import eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public class DefaultIterationImpl implements Iteration {

    /**
     * 
     */
    private int index = 0;
    
    /**
     * 
     */
    private Representation queryRepresentation = null;
    
    /**
     * 
     */
    private Representation optionRepresentation = null;
    
    /**
     * 
     */
    private Direction direction = null;
    
    
    /**
     * 
     */
    public DefaultIterationImpl() {
    }

    /**
     * 
     * @return 
     */
    @Override
    public int getIndex() {
        return index;
    }

    /**
     * 
     * @param index 
     */
    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Representation getOptionType() {
        return optionRepresentation;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Representation getQueryType() {
        return queryRepresentation;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Direction getColumnOrder() {
        return direction;
    }

    /**
     * 
     * @param order 
     */
    @Override
    public void setColumnOrder(Direction direction) {
        if (direction == null) throw new NullPointerException();
        this.direction = direction;
    }

    /**
     * 
     * @param type 
     */
    @Override
    public void setOptionType(Representation representation) {
        if (representation == null) throw new NullPointerException();
        this.optionRepresentation = representation;
    }

    /**
     * 
     * @param type 
     */
    @Override
    public void setQueryType(Representation representation) {
        if (representation == null) throw new NullPointerException();
        this.queryRepresentation = representation;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Index: " + index +
               "queryType: " + queryRepresentation +
               "optionType: " + optionRepresentation + 
               "direction: " + direction;
    }
    
}
