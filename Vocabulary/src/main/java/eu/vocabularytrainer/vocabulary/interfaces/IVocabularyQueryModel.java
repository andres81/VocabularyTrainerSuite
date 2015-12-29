package eu.vocabularytrainer.vocabulary.interfaces;

import java.util.Observer;
import java.util.UUID;

/**
 *
 * @author Andre Schepers <andre@team51.nl>
 */
public interface IVocabularyQueryModel {

  /**
   * 
   * @param index 
   */
  public void updatePairGroup(int index);
  
  /**
   * 
   */
  public void setRandomActiveQuery();
  
  /**
   * 
   * @param uuid 
   */
  public void setActiveQuery(UUID uuid);
  
  /**
   * 
   * @return 
   */
  public VocabularyElementPair getActiveQueryPair();
  
  /**
   * 
   * @param vocabulary 
   */
  public void setVocabulary(Vocabulary vocabulary);

  /**
   * 
   * @param o 
   */
  public void addObserver(Observer o);
  
}
