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
package eu.vocabularytrainer.vocabulary.interfaces;

import eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;
import generated.Columnordertype;
import generated.Vocelemtype;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public interface Iteration {
 
    /**
     * 
     * @return 
     */
    public int getIndex();
    
    /**
     * 
     * @param index 
     */
    public void setIndex(int index);
    
    /**
     * 
     * @return 
     */
    public Representation getOptionType();
    
    /**
     * 
     * @param type
     */
    public void setOptionType(Vocelemtype type);
    
    /**
     * 
     * @return 
     */
    public Representation getQueryType();
    
    /**
     * 
     * @param type
     */
    public void setQueryType(Vocelemtype type);
    
    /**
     * 
     * @return 
     */
    public Direction getColumnOrder();
    
    /**
     * 
     * @param order
     */
    public void setColumnOrder(Columnordertype order);
}
