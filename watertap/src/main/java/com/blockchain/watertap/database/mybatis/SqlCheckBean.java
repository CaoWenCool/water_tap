package com.blockchain.watertap.database.mybatis;


import com.blockchain.watertap.database.SqliPrevention;

/**
 * Bean version of {@link SqliPrevention}, which can be instantiated and used in mybatis SQLs..
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/14 下午8:44
 */
public class SqlCheckBean {
    public static final SqlCheckBean INSTANCE = new SqlCheckBean();

    public String table(String table) {
        SqliPrevention.checkTable(table);
        return table;
    }

    public String column(String column) {
        SqliPrevention.checkColumn(column);
        return column;
    }

    public String columnWithPre(String column) {
        SqliPrevention.checkColumnWithPrefix(column);
        return column;
    }

    public String order(String order) {
        SqliPrevention.checkOrder(order);
        return order;
    }
}
