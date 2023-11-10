package com.holub.database.jdbc;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.sql.*;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JoinedSelectAllThroughJDBCTest {
    private static final String[] TEST_DDL = {
            """
            create table address (
              primary key (id),
              addrId
            )
            """
            ,
            """         
            create table name (
              primary key (id),
              theName,
              addrId
            )
            """
    };

    private static final String[] INSERTS = {
            "insert into address (addrId) values (1)",
            "insert into address (addrId) values (2)",
            "insert into address (addrId) values (3)",
            "insert into name (theName, addrId) values (\"aello\", 1)",
            "insert into name (theName, addrId) values (\"bello\", 1)",
            "insert into name (theName, addrId) values (\"cello\", 2)",
    };

    private static final String SELECT = """
            select * from address, name where address.addrId = name.addrId and addrId = 1
            """;


    private static final String DB_PATH = Paths.get(".").toUri().getPath();
    private static final String DRIVER_NAME = "com.holub.database.jdbc.JDBCDriver";

    private Connection connection = null;

    @BeforeAll
    void beforeAll() throws SQLException {
        try {
            Class.forName(DRIVER_NAME).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        connection = DriverManager.getConnection("file://" + DB_PATH);
    }

    @AfterAll
    void afterAll() throws SQLException {
        connection.close();
    }

    @Test
    void testSelectAllForJoinedColumn() throws SQLException {
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        for (String tableSql : TEST_DDL) {
            statement.executeUpdate(tableSql);
        }

        for (String insertSql : INSERTS) {
            statement.executeUpdate(insertSql);
        }

        ResultSet rs = statement.executeQuery(SELECT);
        //aello
        rs.next();
        String aelloName = rs.getString("theName");
        assertThat(aelloName).as("check aello").isEqualTo("aello");rs.next();

        //bello
        rs.next();
        String belloName = rs.getString("theName");
        assertThat(belloName).as("check bello").isEqualTo("bello");

        assertThat(rs.next()).as("is select result finished?").isFalse();

        connection.rollback();
    }
}