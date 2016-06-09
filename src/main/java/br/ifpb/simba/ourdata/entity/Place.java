
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
    public String toString()
    {
        return "Place{" + "id=" + getId() + ", nome=" + getNome() + ", sigla=" + getSigla() + ", tipo=" + getTipo() + ", way=" + getWay() + '}';
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
    
}
