/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.enums;

/**
 * Enum of Place Types
 *
 * @author Wensttay
 */
public enum PlaceType{
    MICRORREGIAO("Microrregião", "microrregiao"),
    ESTADO("Estado", "estado"),
    MUNICIPIO("Município", "municipio"),
    MESORREGIAO("Mesorregião", "mesorregiao"),
    REGIAO("Região", "regiao");

    private String nameToUser;
    private String nameToBd;

    /**
     * Default Constructor for PlaceType
     *
     * @param nameToUser Name used to show for the users
     * @param nameToBd Name used to persist on Data Base
     */
    PlaceType( String nameToUser, String nameToBd ){
        this.nameToUser = nameToUser;
        this.nameToBd = nameToBd;
    }

    /**
     * @return the nameToUser
     */
    public String getNameToUser(){
        return nameToUser;
    }

    /**
     * @param nameToUser the nameToUser to set
     */
    public void setNameToUser( String nameToUser ){
        this.nameToUser = nameToUser;
    }

    /**
     * @return the nameToBd
     */
    public String getNameToBd(){
        return nameToBd;
    }

    /**
     * @param nameToBd the nameToBd to set
     */
    public void setNameToBd( String nameToBd ){
        this.nameToBd = nameToBd;
    }

}
