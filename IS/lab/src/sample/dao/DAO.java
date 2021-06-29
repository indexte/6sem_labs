package sample.dao;

import sample.Library;

public interface DAO {
    Library getAll();
    void updateReader(int code, int newCode, String newName);
    void updateBook(int readerCode, int code, int newCode, String newFrom, String newTo);
    void addReader(int code, String name);
    void addBook(int readerCode, int code, String from, String to);
    void deleteReader(int code);
    void deleteBook(int readerCode, int code);
}
