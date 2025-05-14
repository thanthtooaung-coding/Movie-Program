package com.moving_system;

/**
 * Represents a Movie entity with all required fields
 */
public class Movie {
    private int id;
    private String title;
    private String releaseDate;
    private long revenue;
    private double runtime;
    private String companie;
    private String genre;
    private String prodCountry;
    private double voteAverage;

    // Constructor
    public Movie(int id, String title, String releaseDate, long revenue, double runtime, 
                String companie, String genre, String prodCountry, double voteAverage) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.companie = companie;
        this.genre = genre;
        this.prodCountry = prodCountry;
        this.voteAverage = voteAverage;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public double getRuntime() {
        return runtime;
    }

    public void setRuntime(double runtime) {
        this.runtime = runtime;
    }

    public String getCompanie() {
        return companie;
    }

    public void setCompanie(String companie) {
        this.companie = companie;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getProdCountry() {
        return prodCountry;
    }

    public void setProdCountry(String prodCountry) {
        this.prodCountry = prodCountry;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", companie='" + companie + '\'' +
                ", genre='" + genre + '\'' +
                ", prodCountry='" + prodCountry + '\'' +
                ", voteAverage=" + voteAverage +
                '}';
    }

    public String toCSV() {
        return id + "," + title + "," + releaseDate + "," + revenue + "," + 
               runtime + "," + companie + "," + genre + "," + prodCountry + "," + voteAverage;
    }
}