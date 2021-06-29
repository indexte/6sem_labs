package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.dao.ConcreteDAO;
import sample.dao.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private TreeView treeView;
    @FXML
    private Button addReader;
    @FXML
    private Button addBook;
    @FXML
    private Button updateReader;
    @FXML
    private Button updateBook;
    @FXML
    private Button deleteReader;
    @FXML
    private Button deleteBook;
    @FXML
    private Button save;
    @FXML
    private TextField codeUser;
    @FXML
    private TextField nameUser;
    @FXML
    private TextField codeBook;
    @FXML
    private TextField fromBook;
    @FXML
    private TextField toBook;
    @FXML
    private Label information;

    DAO dao;
    @FXML
    public void initialize() {
        try {
            dao = new ConcreteDAO();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printToTreeView();


    }
    public void printToTreeView(){
        treeView.setRoot(null);
        ArrayList<Reader> reders = dao.getAll().getReaders();
        //ArrayList<TreeItem<String>> redersTree = new ArrayList<>();
        TreeItem<String> root = new TreeItem<>("Library");

        for(int i = 0; i < reders.size(); i++){
            String info;
            info = "Code: " + reders.get(i).code + " Name: " + reders.get(i).name;
            ArrayList<Book> books = reders.get(i).getBooks();
            TreeItem<String> reader = new TreeItem<>(info);
            for(int j = 0; j < books.size(); j++){
                String bookInfo = books.get(j).code + " " + books.get(j).from + " - " + books.get(j).to;
                TreeItem<String> book = new TreeItem<>(bookInfo);
                reader.getChildren().add(book);
            }
            // redersTree.add(reader);
            root.getChildren().add(reader);
        }
        treeView.setRoot(root);
    }
    @FXML
    private void addReader(ActionEvent event) {
        String code = codeUser.getText();
        String name = nameUser.getText();
        //Reader reader = new Reader(Integer.valueOf(code), name);
        dao.addReader(Integer.valueOf(code), name);
        printToTreeView();
    }
    @FXML
    private void addBook(ActionEvent event) {
        String code = codeBook.getText();
        String from = fromBook.getText();
        String to = toBook.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected reader");
            return;
        }
        String codeReader = selectedItem.getValue().split(" ")[1];
        //Reader reader = xmlWorker.findReaderByCode(Integer.valueOf(codeReader));
        //xmlWorker.addBook(Integer.valueOf(codeReader), new Book(Integer.valueOf(code), from, to));

        dao.addBook(Integer.valueOf(codeReader), Integer.valueOf(code), from, to);
        printToTreeView();

    }
    @FXML
    private void updateReader(ActionEvent event) {
        String newCode = codeUser.getText();
        String newName = nameUser.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected reader");
            return;
        }
        String codeReader = selectedItem.getValue().split(" ")[1];
        String nameReader = selectedItem.getValue().split(" ")[3];
        //Reader reader = xmlWorker.findReaderByCode(Integer.valueOf(codeReader));
        if(newCode.equals("")){
            newCode = codeReader;
        }
        if(newName.equals("")){
            newName = nameReader;
        }
        dao.updateReader(Integer.valueOf(codeReader), Integer.valueOf(newCode), newName);
        printToTreeView();
    }
    @FXML
    public void updateBook(ActionEvent event){
        String newCode = codeBook.getText();
        String newFrom = fromBook.getText();
        String newTo = toBook.getText();
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected reader");
            return;
        }
        String codeReader = selectedItem.getParent().getValue().split(" ")[1];
        String codeBook = selectedItem.getValue().split(" ")[0];
        String fromBook = selectedItem.getValue().split(" ")[1];
        String toBook = selectedItem.getValue().split(" ")[3];
        if(newCode.equals("")){
            newCode = codeBook;
        }
        if(newFrom.equals("")){
            newFrom = fromBook;
        }
        if(newTo.equals("")){
            newTo = toBook;
        }
        dao.updateBook(Integer.valueOf(codeReader), Integer.valueOf(codeBook),
                Integer.valueOf(newCode), newFrom, newTo);
        printToTreeView();
    }
    @FXML
    public void deleteReader(ActionEvent event){
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected reader");
            return;
        }
        String codeUser = selectedItem.getValue().split(" ")[1];
        dao.deleteReader(Integer.valueOf(codeUser));
        printToTreeView();
    }
    @FXML
    public void deleteBook(ActionEvent event){
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected reader");
            return;
        }
        String codeReader = selectedItem.getParent().getValue().split(" ")[1];
        String codeBook = selectedItem.getValue().split(" ")[0];
        dao.deleteBook(Integer.valueOf(codeReader), Integer.valueOf(codeBook));
        printToTreeView();
    }

}
