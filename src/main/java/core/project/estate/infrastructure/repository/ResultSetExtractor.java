package core.project.estate.infrastructure.repository;

import core.project.estate.infrastructure.exceptions.RepositoryDataException;
import jakarta.annotation.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetExtractor<T> {
    @Nullable
    T extractData(ResultSet rs) throws SQLException, RepositoryDataException;
}