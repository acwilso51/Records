/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Attributes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Class DuplicateChecker.
 *
 * @author Alexander Wilson
 */
public class DuplicateChecker {

  /**
   * Gets the hm.
   *
   * @return the hm
   */
  private static HashMap getHm() {
    return hm;
  }

  /**
   * Gets the values.
   *
   * @param Title          the title
   * @param Artist          the artist
   * @param byPassDuplicate the by pass duplicate
   * @return the values
   */
   static ArrayList<String> getValues(String location, String Title, String Artist) {
    System.out.println("DuplicateEntry.getValues(" + Title + ", " + Artist + ")");
    ArrayList<String> valSet;
    if (isDuplicate(Title, Artist)) {
      valSet = null;
    } else {
      valSet = EchonestSongAttributes.retrieveEchoInfo(Artist, Title);
    }
    if (valSet != null) {
      String newTitle = valSet.get(0);
      String newArtist = valSet.get(1);
      getHm().put(newTitle + "," + newArtist, valSet);
    }
    return valSet;
  }

  /**
   * Checks if is duplicate.
   *
   * @param Title          the title
   * @param Artist          the artist
   * @param byPass the by pass
   * @return true, if is duplicate
   */
  static boolean isDuplicate(String Title, String Artist) {
    System.out.println("DuplicateEntry.isDuplicate(" + Title + ", " + Artist + ")");
      if (getHm().containsKey(Title + "," + Artist)) {
        System.out.println("----Duplicate found----");
        return true;
      } else {
        System.out.println("----New Entry----");
        return false;
      }
  }

  /**
   * Sets the hm.
   *
   * @param aHm
   *          the hm to set
   */
  public static void setHm(HashMap aHm) {
    hm = aHm;
  }

  /** The hm. */
  private static HashMap hm;

}
