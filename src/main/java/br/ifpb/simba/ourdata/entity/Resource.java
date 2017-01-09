package br.ifpb.simba.ourdata.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public class Resource implements Comparable<Resource> {

    private String id;
    private String name;
    private String descricao;
    private String formato;
    private String url;
    private List<KeyPlace> keyplaces;
    private String idDataset;
    private Place place;
    private Double precisionScore;

    public Resource() {
        this.keyplaces = new ArrayList<>();
    }

    public Resource(String id, String name, String descricao, String formato, String url, String idDataset) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
        this.formato = formato;
        this.url = url;
        this.idDataset = idDataset;
        this.keyplaces = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addKeyPlace(KeyPlace kp) {
        getKeyplaces().add(kp);
    }

    public List<KeyPlace> getKeyPlaces() {
        return Collections.unmodifiableList(getKeyplaces());
    }

    public double getRepeatPercent(float constante) {
        int sum_repeat = 0;
        int rows;

        if (!keyplaces.isEmpty()) {
            rows = getKeyplaces().get(0).getRowsNumber();
        } else {
            return 0;
        }

        for (KeyPlace kp : getKeyplaces()) {
            sum_repeat += kp.getRepeatNumber();
        }

        return (sum_repeat / rows) * constante;
    }

//    @Override
//    public int compareTo(Resource o) {
//        float constante = new Float(0.5);
//        double thisScore = this.getRepeatPercent(constante) + PlaceUtils.getOverlap(this.getPlace(), o.getPlace(), constante);
//        double otherScore = o.getRepeatPercent(constante) + PlaceUtils.getOverlap(o.getPlace(), this.getPlace(), constante);
//        return 
//    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the keyplaces
     */
    public List<KeyPlace> getKeyplaces() {
        return keyplaces;
    }

    /**
     * @param keyplaces the keyplaces to set
     */
    public void setKeyplaces(List<KeyPlace> keyplaces) {
        this.keyplaces = keyplaces;
    }

    /**
     * @return the idDataset
     */
    public String getIdDataset() {
        return idDataset;
    }

    /**
     * @param idDataset the idDataset to set
     */
    public void setIdDataset(String idDataset) {
        this.idDataset = idDataset;
    }

    /**
     * @return the place
     */
    public Place getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * @return the precisionScore
     */
    public double getPrecisionScore() {
        return precisionScore;
    }

    /**
     * @param precisionScore the precisionScore to set
     */
    public void setPrecisionScore(double precisionScore) {
        this.precisionScore = precisionScore;
    }

    @Override
    public int compareTo(Resource o) {
        return this.precisionScore.compareTo(o.getPrecisionScore());
    }

}
