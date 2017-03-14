package br.ifpb.simba.ourdata.reader;

/**
 * Interface with methods to Readers files
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public interface Reader<T, I> {

    /**
     * Method to build (using a address url) something usable to print, read ...
     *
     * @param urlString The address URL
     *
     * @return A type usable to print, read ...
     */
    T build(I urlString);

    /**
     * Method to print some file using a address url
     *
     * @param urlString The address URL
     */
    void print(I urlString);

//    List<KeyPlace> filterKeyPlace(String resourceId, I urlString, List<Place> PlaceList);
}
