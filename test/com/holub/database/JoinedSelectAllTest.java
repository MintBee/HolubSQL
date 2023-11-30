package com.holub.database;

import com.holub.text.ParseFailure;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JoinedSelectAllTest {
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

    private Database db = null;

    @BeforeAll
    void beforeAll() {
        db = new Database();
    }

    @Test
    void testSelectAllForJoinedColumn() throws IOException, ParseFailure {
        db.begin();

        //given
        for (String tableSql : TEST_DDL) {
            db.execute(tableSql);
        }

        for (String insertSql : INSERTS) {
            db.execute(insertSql);
        }

        //when
        Cursor resultRows = db.execute(SELECT).rows();

        //then

        ///aello
        resultRows.advance();
        resultRows.column("theName");
        assertThat(resultRows.column("theName")).as("check aello").isEqualTo("aello");

        ///bello
        resultRows.advance();
        resultRows.column("theName");
        assertThat(resultRows.column("theName")).as("check bello").isEqualTo("bello");

        ///is finished
        assertThat(resultRows.advance()).as("check finished").isFalse();

        db.rollback();
    }
}