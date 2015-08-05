/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.vocabularytrainer.vocabulary.interfaces;

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
       INTERACTIONTYPE;
    };
    
    
}
