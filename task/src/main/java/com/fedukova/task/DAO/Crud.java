package com.fedukova.task.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public interface Crud{
    int create(ArrayList<?> list) throws SQLException;
    ArrayList<?> read() throws SQLException;
    int update(Objects ob);
    int delete(ArrayList<?> list) throws SQLException;

}
