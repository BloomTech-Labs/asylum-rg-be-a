package com.bloomtech.asylumrgbea.models;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Stream;

/**
 * Defines a POJO for a page object that represents the server side page.
 */
@Getter
@Setter
public class Page<T> implements Iterable<T> {
    private Collection<T> page;

    private int pageNumber, TotalPages;

    public Page() {
        this.page = new ArrayList<>();
    }

    public Page(int pageNumber) {
        this.setPageNumber(0);
        this.setTotalPages(1);
        this.page = new ArrayList<>();
    }

    //TESTING
    public Page(Collection<T> collection) {
        this.setPageNumber(0);
        this.setTotalPages(1);
        this.page = new ArrayList<>();
        this.page.addAll(collection);
    }

    public Page(int pageNumber, int totalPages, Collection<T> collection) {
        this.setPageNumber(pageNumber);
        this.setTotalPages(totalPages);
        this.page = new ArrayList<>();
        this.page.addAll(collection);
    }

    @Override
    public Iterator<T> iterator() {
        return page.iterator();
    }

    public void add(T object) {
        page.add(object);
    }

    public T get(int number) {
        Optional<T> result = page.stream().skip(number).findFirst();

        if (result.isPresent()) return result.get();

        throw new IndexOutOfBoundsException("The index is out of range (index < 0 || index >= size()) collectionSize: " + number);
    }

    public void addAll(Collection<T> collection) {
        page.addAll(collection);
    }

    public int size() {
        return page.size();
    }

    public boolean isEmpty() {
        return page.isEmpty();
    }

    public Stream<T> stream() {
        return page.stream();
    }

    public void addIterator(Iterable<T> iterable) {
        for (T t : iterable) {
            page.add(t);
        }
    }

}
