package com.moisesflima.goldenraspberry.repository;

import com.moisesflima.goldenraspberry.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Returns all winning movies ordered by producers and year,
     * so we can compute consecutive win intervals.
     */
    @Query("SELECT m FROM Movie m WHERE m.winner = true ORDER BY m.producers ASC, m.year ASC")
    List<Movie> findAllWinnersOrderedByProducerAndYear();
}
