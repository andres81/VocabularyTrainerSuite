package eu.vocabularytrainer.vocabulary;

import eu.vocabularytrainer.vocabulary.interfaces.IVocabularyQueryModel;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Andre Schepers <andre@team51.nl>
 */
public class DefaultVocabularyQueryModel extends Observable implements IVocabularyQueryModel {

  private Vocabulary vocabulary = null;
  private List<VocabularyElementPair> pairs = null;
  private VocabularyElementPair activePair = null;
  
  /**
   * 
   * @param index 
   */
  @Override
  public void updatePairGroup(int index) {
    if (vocabulary == null) throw new NullPointerException("No Vocabulary set, can't update!");
    getVocabularyPairs().clear();
    getVocabularyPairs().addAll(vocabulary.getPairs(index));
    setRandomActiveQuery();
  }

  /**
   * 
   */
  @Override
  public void setRandomActiveQuery() {
    if (pairs == null) throw new NullPointerException("No Vocabulary set, can't set random active pair!");
    Random r = new Random();
    if (activePair == null) activePair = pairs.get(r.nextInt(pairs.size()));
    List<VocabularyElementPair> temp = new ArrayList<>();
    temp.addAll(pairs);
    temp.remove(activePair);
    activePair = temp.get(r.nextInt(temp.size()));
  }

  /**
   * 
   * @param uuid 
   */
  @Override
  public void setActiveQuery(UUID uuid) {
    for (VocabularyElementPair pair : pairs) {
      if (pair.getUuid() == uuid) {
        activePair = pair;
      }
    }
  }

  /**
   * 
   * @return 
   */
  @Override
  public VocabularyElementPair getActiveQueryPair() {
    return activePair;
  }

  /**
   * 
   * @param vocabulary 
   */
  @Override
  public void setVocabulary(Vocabulary vocabulary) {
    this.vocabulary = vocabulary;
  }

  /**
   * 
   * @return 
   */
  private List<VocabularyElementPair> getVocabularyPairs() {
    if (pairs == null) {
      pairs = new ArrayList<>();
    }
    return pairs;
  }
  
}
