package br.ifpb.simba.ourdata.headerAnality;

/**
 * Class for keep the meta-datas about a one resource's column
 *
 * @author Pedro Arthur
 */
public class ColumnHeader {

    private int distinctValues;
    private String name;

    /**
     * Constructor to make a Column started with atributes 'distinctvalues',
     * 'name'
     *
     * @param distinctValues Number of stanza thar not reapeat
     * @param name Colum name
     */
    public ColumnHeader(int distinctValues, String name) {
        this.distinctValues = distinctValues;
        this.name = name;
    }

    /**
     * String representation of class
     *
     * @return A String representation of class
     */
    @Override
    public String toString() {
        return "Column{" + "distinctValues=" + getDistinctValues() + ", name=" + getName() + '}';
    }

    /**
     * @return the distinctValues
     */
    public int getDistinctValues() {
        return distinctValues;
    }

    /**
     * @param distinctValues the distinctValues to set
     */
    public void setDistinctValues(int distinctValues) {
        this.distinctValues = distinctValues;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
