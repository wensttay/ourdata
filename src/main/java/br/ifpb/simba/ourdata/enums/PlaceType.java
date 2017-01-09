package br.ifpb.simba.ourdata.enums;

/**
 * Enum of Place Types
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public enum PlaceType {
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
    PlaceType(String nameToUser, String nameToBd) {
        this.nameToUser = nameToUser;
        this.nameToBd = nameToBd;
    }

    /**
     * @return the nameToUser
     */
    public String getNameToUser() {
        return nameToUser;
    }

    /**
     * @param nameToUser the nameToUser to set
     */
    public void setNameToUser(String nameToUser) {
        this.nameToUser = nameToUser;
    }

    /**
     * @return the nameToBd
     */
    public String getNameToBd() {
        return nameToBd;
    }

    /**
     * @param nameToBd the nameToBd to set
     */
    public void setNameToBd(String nameToBd) {
        this.nameToBd = nameToBd;
    }

}
