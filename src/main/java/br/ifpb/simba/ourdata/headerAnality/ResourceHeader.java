
package br.ifpb.simba.ourdata.headerAnality;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for keep the meta-datas abaut all resource's columns
 *
 * @author Pedro Arthur
 */
public class ResourceHeader{
    private List<ColumnHeader> columns;
    private int qtdRows;

    /**
     * Constructor default to ResourceAnality.
     * <p>
     */
    public ResourceHeader(){
        columns = new ArrayList<>();
        qtdRows = 0;
    }

    /**
     * Constructor to Resource parameter passing a List with ColumnAnalitys and
     * the number of Document rows
     *
     * @param columns A list with all ColumnsAnality of some resource
     * @param qtdRows A number of rows about some resource
     */
    public ResourceHeader( List<ColumnHeader> columns, int qtdRows ){
        this.columns = columns;
        this.qtdRows = qtdRows;
    }

    /**
     * String representation of class
     *
     * @return A String representation of class
     */
    @Override
    public String toString(){
        return "ResourceHeader{" + "columns=" + getColumns() + ", qtdRows=" + getQtdRows() + '}';
    }

    /**
     * @return the columns
     */
    public List<ColumnHeader> getColumns(){
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns( List<ColumnHeader> columns ){
        this.columns = columns;
    }

    /**
     * @return the qtdRows
     */
    public int getQtdRows(){
        return qtdRows;
    }

    /**
     * @param qtdRows the qtdRows to set
     */
    public void setQtdRows( int qtdRows ){
        this.qtdRows = qtdRows;
    }

}
