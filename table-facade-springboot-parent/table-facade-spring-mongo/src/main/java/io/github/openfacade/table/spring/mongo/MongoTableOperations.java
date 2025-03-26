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

package io.github.openfacade.table.spring.mongo;

import io.github.openfacade.table.api.Condition;
import io.github.openfacade.table.api.TableException;
import io.github.openfacade.table.api.TableOperations;

import java.util.List;

public class MongoTableOperations implements TableOperations {

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
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Long count(Class<T> type) throws TableException {
        throw new UnsupportedOperationException();
    }

}
