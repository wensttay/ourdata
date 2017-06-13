package br.ifpb.simba.ourdata.shared.entities;

/**
 *
 * @author Pedro Arthur
 */
public class ResourceItemSearch implements Comparable<ResourceItemSearch> {

    private Resource resource;
    private String datasetName;
    private String datasetAuthor;
    private double ranking;

    public ResourceItemSearch(Resource resource, String datasetName, String datasetAuthor, double ranking) {
        this.resource = resource;
        this.datasetName = datasetName;
        this.datasetAuthor = datasetAuthor;
        this.ranking = ranking;
    }
    
    public ResourceItemSearch(Resource resource, double ranking) {
        this.resource = resource;
        this.ranking = ranking;
    }
    
    public ResourceItemSearch() {
    }

    public String getDatasetAuthor() {
        return datasetAuthor;
    }

    public void setDatasetAuthor(String datasetAuthor) {
        this.datasetAuthor = datasetAuthor;
    }
    
    
    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }
    
    public Resource getResource() {
        return resource;
    }

    public double getRanking() {
        return ranking;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    @Override
    public int compareTo(ResourceItemSearch o) {
        if (this.ranking < o.getRanking()) {
            return 1;
        } else if (this.ranking > o.getRanking()) {
            return -1;
        }
        return 0;
    }
}
