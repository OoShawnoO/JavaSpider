package cn.edu.swu.ws.book;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// TODO: 实现 BookDataSource 中定义的所有接口函数
public class FileBookDataSource implements BookDataSource {

    // 用来存储图书的目录
    private String directory;

    // 格式化日期的类。把格式化的日期作为文件夹名称
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private List<Book> BookList;

    public List<Book> getBookList() {
        return BookList;
    }

    public void setBookList(List<Book> bookList) {
        BookList = bookList;
    }

    public FileBookDataSource(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Book> getNewBooks() throws IOException {
        List<Book> BookList = new ArrayList<Book>();
        String date = this.simpleDateFormat.format(new Date());
        File[] files = new File(this.directory + "/" + date+"/").listFiles();
        String dic = this.directory + "/" + date+"/";
        for(File file : files) {
                List<String> txt = new ArrayList<String>();
                //System.out.println(file.getName());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(dic+file.getName()),"utf-8"));
                String line = null;
                while((line = bufferedReader.readLine())!=null){
                    txt.add(line);
                }
                bufferedReader.close();
                Book book = new Book();
                book.setCode(txt.get(0));
                book.setName(txt.get(1));
                book.setPrice(Float.parseFloat(txt.get(2)));
                BookList.add(book);
            }
        this.BookList = BookList;
        return BookList;
    }

    @Override
    public void saveNewBook(Book book) throws IOException {
        String date = this.simpleDateFormat.format(new Date());
        String filePath = this.directory + "/" + date + "/" + book.getCode() + ".txt";
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        String imageFilePath = this.downloadImage(book);
        book.setImageFile(imageFilePath);

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(book.getCode() + "\n");
        fileWriter.write(book.getName() + "\n");
        fileWriter.write(String.valueOf(book.getPrice()) + "\n");
        fileWriter.write(book.getImageUrl() + "\n");
        fileWriter.write(book.getImageFile());
        fileWriter.close();
    }

    @Override
    /**
     * 根据提供的编号获取一本数据的详细信息
     */
    public Book getBookByCode(String code) throws IOException {
        Book bookFind = null;
        for(Book book:BookList) {
            if (Integer.parseInt(book.getCode()) == Integer.parseInt(code)) {
                bookFind = book;
            }
        }
        return bookFind;

    }

    @Override
    /**
     * 根据提供的书名，查找返回符合条件的图书。
     * 例如：输入 “哲学”，可能返回所有书名中包含哲学的书
     */
    public List<Book> findBookByName(String code) {
        List<Book> books = new ArrayList<Book>();
        for(Book book:BookList){
            if(book.getName().contains(code)){
                books.add(book);
            }
        }

        if(books.isEmpty()){
            return null;
        }
        return books;
    }

    @Override
    /**
     * 根据提供的编号删除一本数据的详细信息
     */
    public void deleteBookByCode(String code) {
        String date = this.simpleDateFormat.format(new Date());
        File[] files = new File(this.directory + "/" + date+"/").listFiles();
        for(File file:files){
            if(file.getName().equals(code+".txt")){
                System.out.println(file.exists()+" "+file.delete());
            }
        }
    }

    @Override
    /**
     * 查找给定价格区间的图书
     */
    public List<Book> findBookByPrice(float minPrice, float maxPrice) {
        List<Book> books = new ArrayList<Book>();
        for(Book book:BookList){
            if(book.getPrice()>=minPrice&&book.getPrice()<=maxPrice){
                books.add(book);
            }
        }
        return books;
    }


    private String downloadImage(Book book) throws IOException {
        byte[] data = HttpTools.getImage(book.getImageUrl());
        String imageFilePath = this.generateImageFilePath(book);
        this.saveImage(data, imageFilePath);
        return imageFilePath;
    }

    private String generateImageFilePath(Book book) {
        // TODO: 随机或者根据 book 类生成一个存储图片的文件路径
        String BookName = "D://JavaDemos/HomeWork/files/"+UUID.randomUUID()+".jpg";
        return BookName;
    }

    private void saveImage(byte[] data, String filePath) throws IOException {
        // TODO: 保存图片到指定的文件路径
        File file = new File(filePath);
        FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(file);
        fileImageOutputStream.write(data,0,data.length);
        fileImageOutputStream.close();
    }

}




