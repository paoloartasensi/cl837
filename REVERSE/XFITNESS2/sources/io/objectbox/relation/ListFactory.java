package io.objectbox.relation;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface ListFactory extends Serializable {

    public static class CopyOnWriteArrayListFactory implements ListFactory {
        private static final long serialVersionUID = 1888039726372206411L;

        public <T> List<T> createList() {
            return new CopyOnWriteArrayList();
        }
    }

    <T> List<T> createList();
}
