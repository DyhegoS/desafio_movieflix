package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public ReviewDTO insert(ReviewDTO dto){
        Review entity = new Review();
        User user = authService.authenticated();
        entity.setId(dto.getId());
        entity.setText(dto.getText());
        entity.setMovie(new Movie(dto.getMovieId()));
        entity.setUser(user);
        entity = reviewRepository.save(entity);
        return new ReviewDTO(entity);
    }

}
