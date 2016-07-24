
package com.themoviedbapp.dataModels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviesNowPlaying {

    @Expose
    private Dates dates;
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<Movie> movieList = new ArrayList<Movie>();
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    /**
     *
     * @return
     *     The dates
     */
    public Dates getDates() {
        return dates;
    }

    /**
     *
     * @param dates
     *     The dates
     */
    public void setDates(Dates dates) {
        this.dates = dates;
    }

    /**
     *
     * @return
     *     The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     *
     * @param page
     *     The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     *
     * @return
     *     The movieList
     */
    public List<Movie> getMovieList() {
        return movieList;
    }

    /**
     *
     * @param movieList
     *     The movieList
     */
    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    /**
     *
     * @return
     *     The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     *     The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     *
     * @return
     *     The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     *     The total_results
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

}
