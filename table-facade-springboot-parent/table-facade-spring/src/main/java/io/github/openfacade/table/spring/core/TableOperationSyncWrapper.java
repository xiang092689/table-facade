package io.github.openfacade.table.spring.core;

import io.github.openfacade.table.api.Condition;
import io.github.openfacade.table.api.TableException;
import io.github.openfacade.table.api.TableOperations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableOperationSyncWrapper implements TableOperations {

    final ReactiveBaseTableOperations delegate;

    public TableOperationSyncWrapper(ReactiveBaseTableOperations delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> T insert(T object) {
        return delegate.insert(object).block();
    }

    @Override
    public <T> Long update(Condition condition, Object[] pairs, Class<T> type) {
        return delegate.update(condition, pairs, type).block();
    }

    @Override
    public <T> T find(Condition condition, Class<T> type) {
        return delegate.find(condition, type).block();
    }

    @Override
    public <T> List<T> findAll(Class<T> type) {
        return delegate.findAll(type).collectList().block();
    }

    @Override
    public <T> Long deleteAll(Class<T> type) throws TableException {
        return delegate.deleteAll(type).block();
    }

    @Override
    public <T> Long delete(Condition condition, Class<T> type) throws TableException {
        return delegate.delete(condition, type).block();
    }

    @Override
    public <T> Long count(Class<T> type) throws TableException {
        return delegate.findAll(type).count().block();
    }

}
