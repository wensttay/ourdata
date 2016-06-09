
package br.ifpb.simba.ourdata.reader;

/**
 * Interface with methods to Readers files
 * 
 * @author Wensttay
 */
public interface Reader <T,I> {
    /**
     * Method to build (using a address url) something usable to print, read ... 
     * 
     * @param urlString The address URL 
     * @return A type usable to print, read ...
     */
    T build (I urlString);
    
    /**
     * Method to print some file using a address url
     * 
     * @param urlString The address URL 
     */
    void print (I urlString);
    
//    List<KeyWord> filterKeyWord(String resourceId, I urlString, List<Place> PlaceList);
}
