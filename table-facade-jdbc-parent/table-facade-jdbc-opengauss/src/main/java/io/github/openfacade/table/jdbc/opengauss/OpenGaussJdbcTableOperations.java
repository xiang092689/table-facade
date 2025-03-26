/*
 * Copyright 2024 OpenFacade Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.openfacade.table.jdbc.opengauss;

import io.github.openfacade.table.api.Condition;
import io.github.openfacade.table.api.TableException;
import io.github.openfacade.table.api.TableOperations;
import io.github.openfacade.table.sql.mysql.MysqlSqlUtil;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;

@RequiredArgsConstructor
public class OpenGaussJdbcTableOperations implements TableOperations {
    private final DataSource dataSource;

    @Override
    public <T> T insert(T object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Long update(Condition condition, Object[] pairs, Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T find(Condition condition, Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<T> findAll(Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Long delete(Condition condition, Class<T> type) throws TableException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Long deleteAll(Class<T> type) throws TableException {
        String tableName = getTableName(type);
        String sql = MysqlSqlUtil.deleteAll(tableName);

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            return (long) rowsAffected;
        } catch (SQLException e) {
            throw new TableException("Failed to delete all records from table " + tableName, e);
        }
    }

    @Override
    public <T> Long count(Class<T> type) throws TableException {
        String tableName = getTableName(type);
        String sql = MysqlSqlUtil.count(tableName);
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            ResultSet resultSet = stmt.getResultSet();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new TableException("Failed to count records in table " + tableName, e);
        }
    }

    private <T> String getTableName(Class<T> type) throws TableException {
        io.github.openfacade.table.api.anno.Table tableAnnotation = type.getAnnotation(io.github.openfacade.table.api.anno.Table.class);
        if (tableAnnotation == null || tableAnnotation.name().isEmpty()) {
            throw new TableException("Class " + type.getName() + " does not have a Table annotation with a valid name.");
        }

        return tableAnnotation.name();
    }
}
