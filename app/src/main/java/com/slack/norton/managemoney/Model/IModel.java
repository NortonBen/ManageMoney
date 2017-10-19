package com.slack.norton.managemoney.Model;

import java.util.List;

/**
 * Created by norton on 01/10/2017.
 */

public interface IModel<T> {
    boolean instert(T obj);
    boolean update(T obj);
    boolean delete(T obj);
    List<T> getAll();
    T get(int id);
}
