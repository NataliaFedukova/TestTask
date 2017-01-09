package com.fedukova.task.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/** NOT USED
 *
 */
public interface Crud{
    int create(List<?> list) throws SQLException;
    List<?> read() throws SQLException;
    int update(Objects ob);
    int delete(List<?> list) throws SQLException;

}
