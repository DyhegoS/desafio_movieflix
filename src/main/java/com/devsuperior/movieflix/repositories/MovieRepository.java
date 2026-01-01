package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.fasterxml.jackson.core.json.async.NonBlockingJsonParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("""
            SELECT new com.devsuperior.movieflix.dto.MovieCardDTO(obj)  FROM Movie obj
            WHERE obj.genre.id = :genreId
            """)
    Page<MovieCardDTO> searchByGenre(Long genreId, Pageable pageable);

    @Query("""
            SELECT obj FROM Movie obj
            ORDER BY obj.title ASC
            """)
    Page<Movie> searchAll(Pageable pageable);
}
