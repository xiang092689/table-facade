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

package io.github.openfacade.table.api;

import java.util.List;

public interface TableOperations {
    <T> T insert(T object);

    <T> Long update(Condition condition, Object[] pairs, Class<T> type);

    <T> T find(Condition condition, Class<T> type);

    <T> List<T> findAll(Class<T> type);

    <T> Long delete(Condition condition, Class<T> type) throws TableException;

    <T> Long deleteAll(Class<T> type) throws TableException;

    <T> Long count(Class<T> type) throws TableException;
}
