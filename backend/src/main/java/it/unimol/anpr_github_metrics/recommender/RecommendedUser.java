package it.unimol.anpr_github_metrics.recommender;

import it.unimol.anpr_github_metrics.beans.User;

/**
 * This class creates an object to wrap infos about a recommended user
 * @author Code Warrior Team
 */
public class RecommendedUser implements Comparable {
    private User user;
    private double coverage, weight;

    public RecommendedUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getCoverage() {
        return coverage;
    }

    public double getWeight() {
        return weight;
    }

    public void updateCoverage(double amount){
        this.coverage += Math.abs(amount);
    }

    public void updateWeight(double amount){
        this.weight += Math.abs(amount);
    }

    public double getWeightedCoverage(){
        return this.coverage / this.weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecommendedUser that = (RecommendedUser) o;

        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        if(this.getWeightedCoverage() < ((RecommendedUser) o).getWeightedCoverage()){
            return -1;
        }else if(this.getWeightedCoverage() == ((RecommendedUser) o).getWeightedCoverage()){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public String toString() {
        return "RecommendedUser{" +
                "user=" + user.getLogin() +
                ", coverage=" + coverage +
                ", weight=" + weight +
                '}';
    }
}
