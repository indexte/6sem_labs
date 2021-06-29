package sample.dao;

import sample.Library;
import sample.Book;

import java.sql.*;

public class ConcreteDAO implements DAO {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/library?serverTimezone=UTC";

    static final String USER = "root";
    static final String PASS = "qwerty123";

    Connection conn = null;
    Statement stmt = null;
    public ConcreteDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
    }

    @Override
    public Library getAll() {
        Library library = new Library();
        try {

            stmt = conn.createStatement();
            String sql;
            sql = "SELECT *  FROM reader";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int code = rs.getInt("code");
                String name = rs.getString("name");
                library.addReader(code, name);
            }


            sql = "SELECT *  FROM book";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                int readerCode = rs.getInt("readerCode");
                int code = rs.getInt("code");
                String from = rs.getString("timeFrom");
                String to = rs.getString("timeTo");
                library.getReader(readerCode).addBook(new Book(code, from, to));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return library;
    }

    @Override
    public void updateReader(int code, int newCode, String newName) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = " update reader set code=" + newCode + ", name=\"" + newName + "\" where code=" + code + ";";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBook(int readerCode, int code, int newCode, String newFrom, String newTo){
        try {
            stmt = conn.createStatement();
            String sql;
            sql = " update book set code=" + newCode + ", timeFrom=\"" + newFrom +
                    "\", timeTo=\"" + newTo+ "\" where code=" + code + " and readerCode=" + readerCode +";";
            System.out.println(sql);
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addReader(int code, String name) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "insert into reader values (" + code + ", \"" + name + "\");";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBook(int readerCode, int code, String from, String to) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "insert into book values (" + readerCode + ", " + code + ",\"" + from + "\",\"" + to + "\");";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReader(int code) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "delete from reader where code = " + code + ";";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBook(int readerCode, int code) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "delete from book where code = " + code + " and readerCode=" + readerCode + ";";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
