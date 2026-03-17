package com.moisesflima.goldenraspberry.config;

import com.moisesflima.goldenraspberry.entity.Movie;
import com.moisesflima.goldenraspberry.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Component responsible for loading movie data from a CSV file upon application startup.
 */
@Component
public class CsvDataLoader implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(CsvDataLoader.class);

    private final MovieRepository movieRepository;

    public CsvDataLoader(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Loading movie data from CSV...");
        List<Movie> movies = loadMoviesFromCsv();
        movieRepository.saveAll(movies);
        logger.info("Successfully loaded {} movies into the database.", movies.size());
    }

    private List<Movie> loadMoviesFromCsv() throws Exception {
        List<Movie> movies = new ArrayList<>();

        // Try to load from external path first (useful for Docker/External config)
        String externalPath = System.getProperty("csv.path", System.getenv("CSV_PATH"));
        java.io.InputStream inputStream;

        if (externalPath != null && !externalPath.isBlank() && new java.io.File(externalPath).exists()) {
            logger.info("Loading movie data from EXTERNAL file: {}", externalPath);
            inputStream = new java.io.FileInputStream(externalPath);
        } else {
            logger.info("Loading movie data from CLASSPATH (internal movielist.csv)");
            var resource = new org.springframework.core.io.ClassPathResource("movielist.csv");
            inputStream = resource.getInputStream();
        }

        try (
            var is = inputStream;
            var reader = new java.io.InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8);
            var bufferedReader = new BufferedReader(reader)
        ) {
            // Skip header
            String lineStr = bufferedReader.readLine();
            if (lineStr == null) {
                logger.warn("CSV file is empty.");
                return movies;
            }

            while ((lineStr = bufferedReader.readLine()) != null) {
                if (lineStr.isBlank()) continue;

                // Simple split by semicolon
                String[] columns = lineStr.split(";", -1);
                
                if (columns.length < 4) {
                    logger.warn("Skipping malformed line: {}", lineStr);
                    continue;
                }

                try {
                    Integer year = Integer.parseInt(columns[0].trim());
                    String title = columns[1].trim();
                    String studios = columns[2].trim();
                    String producers = columns[3].trim();
                    Boolean winner = columns.length > 4 && "yes".equalsIgnoreCase(columns[4].trim());

                    movies.add(new Movie(year, title, studios, producers, winner));
                } catch (NumberFormatException e) {
                    logger.warn("Skipping line with invalid year '{}': {}", columns[0], e.getMessage());
                }
            }
        }

        return movies;
    }
}
