
package br.ifpb.simba.ourdata.headerAnality;

/**
 * Interface of methods to define a ResourceHeaderAnality type
 *
 * @author Pedro Arthur
 */
public interface ResourceHeaderAnality<T>{
    /**
     * Method to get a complete ResourceHeader from some string URl
     *
     * @param url
     *
     * @return A ResourceHeader with your ColumnsHeaders and Number of Rows
     */
    public T getHeader( String url );
}
