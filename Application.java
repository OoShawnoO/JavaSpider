package cn.edu.swu.ws.book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException {
        // 初始化一个应用程序对象
        Application application = new Application();

        // 通过 run 方法启动运行的逻辑
        application.run();
    }

    public void run() throws IOException {
        // 定义数据源
        BookDataSource webSource = new WebBookDataSource( "http://book.dangdang.com/");
        BookDataSource fileSource = new FileBookDataSource("D://JavaDemos/HomeWork/files");
        List<Book> BookList = new ArrayList<Book>();
        //下载并保存数据
        List<Book> newBooks = webSource.getNewBooks();
        for(int i=0; i<newBooks.size(); i++) {
            Book book = newBooks.get(i);
            fileSource.saveNewBook(book);
            System.out.println(String.format("保存第 %s 本书", i));
        }

        fileSource.getNewBooks();

        //对保存的数据做增、删，改，查的操作
        String code = "29263957";
        System.out.println("\n书籍的Code与所给Code匹配的书：");
        if(fileSource.getBookByCode(code)!=null){
            this.log(fileSource.getBookByCode(code));
        }
        System.out.println("\n书名中包含哲学的书籍有:");
        this.log(fileSource.findBookByName("哲学"));

        System.out.println("\n价格区间在10-40的书籍有：");
        this.log(fileSource.findBookByPrice(10, 40));

        System.out.println("书籍列表:");
        this.log(fileSource.getNewBooks());
        fileSource.deleteBookByCode(code);
    }


    /**
     * 打印图书列表的日志输出类
     * @param books
     */
    public void log(List<Book> books) {
        for(Book book : books) {
            System.out.println(book.toString());
        }
    }

    /**
     * 打印图书的日志输出类
     * @param book
     */
    public void log(Book book) {
        System.out.println(book.toString());
    }



}
