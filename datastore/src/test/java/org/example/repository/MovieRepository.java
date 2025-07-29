package org.example.repository;

import org.example.model.Movie;
import org.example.store.MovieStore;
import org.springframework.data.repository.CrudRepository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

public class MovieRepository implements CrudRepository<Movie, Short> {

    private final DataSource dataSource;
    private final MovieStore movieStore;

    public MovieRepository(final DataSource dataSource
            , final MovieStore movieStore) {
        this.dataSource = dataSource;
        this.movieStore = movieStore;
    }


    @Override
    public <S extends Movie> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Movie> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Movie> findById(Short aShort) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Short aShort) {
        return false;
    }

    @Override
    public Iterable<Movie> findAll() {
        return null;
    }

    @Override
    public Iterable<Movie> findAllById(Iterable<Short> shorts) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Short aShort) {

    }

    @Override
    public void delete(Movie entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Short> shorts) {

    }

    @Override
    public void deleteAll(Iterable<? extends Movie> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
