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
package eu.vocabularytrainer.vocabulary.interfaces;

import java.util.List;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public interface Vocabulary {

    /**
     * 
     */
    public enum Direction {
       COLUMNONETOONE,
       COLUMNONETOTWO,
       COLUMNTWOTOONE,
       COLUMNTWOTOTWO;
    };
    
    /**
     * 
     */
    public enum UpdateType {
       PAIRS,
       ACTIVEPAIRS,
       DIRECTION,
       ACTIVEPAIR,
       QUERYINTERACTIONTYPE,
       OPTIONSINTERACTIONTYPE;
    };
    
    /**
     * 
     * @param pairs 
     */
    public void setPairs(List<VocabularyElementPair> pairs);
    
    /**
     * 
     * @return 
     */
    public List<VocabularyElementPair> getPairs();

    /**
     * 
     * @param iterations 
     */
    public void setIterations(List<Iteration> iterations);
    
    /**
     * 
     * @return 
     */
    public List<Iteration> getIterations();
    
}
