package br.ifpb.simba.ourdata.entity;

/**
 *
 * @author Pedro Arthur
 */
public class ResourceItemSearch implements Comparable<ResourceItemSearch> {

    private Resource resource;
    private double ranking;

    public ResourceItemSearch(Resource resource, double ranking) {
        this.resource = resource;
        this.ranking = ranking;
    }

    public Resource getResource() {
        return resource;
    }

    public double getRanking() {
        return ranking;
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
