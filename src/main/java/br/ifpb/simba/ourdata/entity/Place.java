
package br.ifpb.simba.ourdata.entity;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Class to represent a Place, this class save some meta-dates of a one Place
 *
 * @author Wensttay
 */
public class Place
{
    private int         id;
    private String      nome;
    private String      sigla;
    private String      tipo;
    private Geometry    way;
    private double      minX;
    private double      minY;
    private double      maxX;
    private double      maxY;
    
    /**
     * Constructor passing Geometry Value
     *
     * @param way Geometry Value
     */
    public Place(Geometry way)
    {
        this.way = way;
    }

    /**
     * Default Constructor
     */
    public Place()
    {
    }

    /**
     * Representation of this Place on String formate
     *
     * @return String with all values into this Place
     */
    @Override
    public String toString() {
        return "Place{" + "id=" + id + ", nome=" + nome + ", sigla=" + sigla + ", tipo=" + tipo + ", minX=" + minX + ", minY=" + minY + ", maxX=" + maxX + ", maxY=" + maxY + '}';
    }
    
    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * @return the sigla
     */
    public String getSigla()
    {
        return sigla;
    }

    /**
     * @param sigla the sigla to set
     */
    public void setSigla(String sigla)
    {
        this.sigla = sigla;
    }

    /**
     * @return the tipo
     */
    public String getTipo()
    {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    /**
     * @return the way
     */
    public Geometry getWay()
    {
        return way;
    }

    /**
     * @param way the way to set
     */
    public void setWay(Geometry way)
    {
        this.way = way;
    }

    /**
     * @return the minX
     */
    public double getMinX() {
        return minX;
    }

    /**
     * @param minX the minX to set
     */
    public void setMinX(double minX) {
        this.minX = minX;
    }

    /**
     * @return the minY
     */
    public double getMinY() {
        return minY;
    }

    /**
     * @param minY the minY to set
     */
    public void setMinY(double minY) {
        this.minY = minY;
    }

    /**
     * @return the maxX
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * @param maxX the maxX to set
     */
    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    /**
     * @return the maxY
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * @param maxY the maxY to set
     */
    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

}
