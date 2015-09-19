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
import generated.Columnordertype;
import generated.Vocelemtype;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
class DefaultIterationImpl implements Iteration {

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
    public void setColumnOrder(Columnordertype order) {
        if (order == null) throw new NullPointerException();
        
        switch (order) {
            case FIRST_FIRST:
                this.direction = Direction.COLUMNONETOONE;
                break;
            case FIRST_SECOND:
                this.direction = Direction.COLUMNONETOTWO;
                break;
            case SECOND_FIRST:
                this.direction = Direction.COLUMNTWOTOONE;
                break;
            case SECOND_SECOND:
                this.direction = Direction.COLUMNTWOTOTWO;
                break;
        } 
    }

    /**
     * 
     * @param type 
     */
    @Override
    public void setOptionType(Vocelemtype type) {
        if (type == null) throw new NullPointerException();
        
        switch (type) {
            case AUDIO:
                this.optionRepresentation = Representation.SOUND;
                break;
            case TEXT:
                this.optionRepresentation = Representation.STRING;
                break;
            case IMAGE:
                this.optionRepresentation = Representation.IMAGE;
                break;
        }
    }

    /**
     * 
     * @param type 
     */
    @Override
    public void setQueryType(Vocelemtype type) {
        if (type == null) throw new NullPointerException();
        
        switch (type) {
            case AUDIO:
                this.queryRepresentation = Representation.SOUND;
                break;
            case TEXT:
                this.queryRepresentation = Representation.STRING;
                break;
            case IMAGE:
                this.queryRepresentation = Representation.IMAGE;
                break;
        }
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
