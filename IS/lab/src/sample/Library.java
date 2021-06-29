package sample;

import java.util.ArrayList;

public class Library {
    private ArrayList<Reader> readers = new ArrayList<>();
    public void addReader(int code, String name){
        readers.add(new Reader(code, name));
    }
    public void addReader(Reader reader){ readers.add(reader);}
    public Reader getReader(int code){
        for(int i = 0; i  < readers.size(); i++){
            if(readers.get(i).code == code){
                return readers.get(i);
            }
        }
        return null;
    }
    public ArrayList<Reader> getReaders(){return readers;}
    public int countReaders(){
        return readers.size();
    }
    public void deleteReader(int code) throws Exception{
        Reader readerToDelete = getReader(code);
        if(readerToDelete == null){
            throw new Exception("Reader doesnt exist");
        }
        readers.remove(readerToDelete);
    }
    public void addBook(int code, String from, String to, int readerCode) throws Exception{
        Reader reader = getReader(code);
        if(reader == null){
            throw new Exception("Reader doesnt exist");
        }
        ArrayList<Book> books = reader.getBooks();
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).code == code){
                throw new Exception("This book has already exist");
            }
        }
        Book book = new Book(code, from, to);
        reader.addBook(book);
    }
}
