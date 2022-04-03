package org.example.store;

import org.example.MovieManager;
import org.example.model.Movie;
import org.example.util.DataSourceProvider;
import org.example.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.List;

import static org.example.store.MovieStore.title;
import static org.example.store.MovieStore.yearOfRelease;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieStoreTest
{
    private final MovieStore movieStore;
    private List<Movie> moviesToTest;

    MovieStoreTest()
    {
	MovieManager movieManager = MovieManager.getManager(DataSourceProvider.dataSource());
	// Stores used for testing
	this.movieStore = movieManager.getMovieStore();
    }

    @BeforeAll
    void init() throws SQLException
    {
	this.movieStore.deleteAll();
	// Data used for testing
	this.moviesToTest = this.movieStore.insert()
		.values(JsonUtil.getTestObjects(Movie.class)).returning();
    }

    @Test
    void testWhereClause() throws SQLException
    {
	Assertions.assertEquals(moviesToTest.size()
		, this.movieStore.select().size()
		, "Select All");
    }

    @Test
    void testWhereClauseSingleCriteria() throws SQLException
    {
	Assertions.assertEquals(1
		, this.movieStore.select(title().eq("Memento")).size()
		, "Select All Single Criteria");
    }

    @Test
    void testWhereClauseMultipleANDCriteria() throws SQLException
    {
	Assertions.assertEquals(1
		, this.movieStore.select(yearOfRelease().eq((short) 2017).and().directedBy().eq("Christopher Nolan")).size()
		, "Select All Single Criteria");
    }

    @Test
    void testWhereClauseMultipleORCriteria() throws SQLException
    {
	Assertions.assertEquals(12
		, this.movieStore.select(yearOfRelease().eq((short) 2017).or().directedBy().eq("Christopher Nolan")).size()
		, "Select All Single Criteria");
    }
}