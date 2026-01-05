package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id){
        Optional<Movie> obj = movieRepository.findById(id);
        Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!"));
        Genre genre = new Genre();
        genre = obj.get().getGenre();
        return new MovieDetailsDTO(entity, genre);

    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findByGenre(String genreId, Pageable pageable){
        if("0".equals(genreId) || genreId.isEmpty()){
            Page<Movie> allMovies = movieRepository.searchAll(pageable);
            return allMovies.map(MovieCardDTO::new);
        }
        Long id = Long.parseLong(genreId);
        return movieRepository.searchByGenre(id, pageable);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findAllReviewsByIdMovie(Long id){
    	
    	Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado!"));
    	
    	try {
    		List<Review> list = movieRepository.searchAllReviewsByIdMovie(id);  		
    		return list.stream().map(ReviewDTO::new).collect(Collectors.toList());
    	}
    	catch(RuntimeException e) {
    		throw new ResourceNotFoundException("Recurso não encontrado!");
    	}
        
    }


}
