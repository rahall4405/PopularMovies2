package nanodegree.rahall.popularmovies2.models;

import java.util.ArrayList;

/**
 * Created by rahall on 11/28/15.
 */
public class Reviews {

    private ArrayList<Review> reviews;
    public Reviews() {
        reviews = new ArrayList<>();
    }
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
    public void addReview(Review review) {
        reviews.add(review);
    }

    public void deleteReview(Review review) {
        reviews.remove(review);
    }

    public Review getReview(int i) {
        return reviews.get(i);
    }

    public int getSize() {
        return reviews.size();
    }

}
