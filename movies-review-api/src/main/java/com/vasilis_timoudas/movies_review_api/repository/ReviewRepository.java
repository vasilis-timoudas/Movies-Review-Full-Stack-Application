package com.vasilis_timoudas.movies_review_api.repository;

import com.vasilis_timoudas.movies_review_api.model.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {
}
