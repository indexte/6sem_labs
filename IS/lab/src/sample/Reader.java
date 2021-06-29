package sample;

import java.util.ArrayList;

public class Reader {
    public int code;
    public String name;
    private ArrayList<Book> books = new ArrayList<>();
    public Reader(int code, String name){
        this.code = code;
        this.name = name;
    }
    public ArrayList<Book> getBooks(){
        return books;
    }
    public void addBook(Book book){
        books.add(book);
    }
    public Book findBookByCode(int code){
        for(int i = 0; i  < books.size(); i++){
            if(books.get(i).code == code){
                return books.get(i);
            }
        }
        return null;
    }

}
