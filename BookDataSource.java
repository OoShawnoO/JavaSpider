package cn.edu.swu.ws.book;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface BookDataSource {
    /**
     * 获取当前最新的图书列表
     */
    public List<Book> getNewBooks() throws IOException;

    /**
     * 保存一本书的数据到数据源
     */
    public void saveNewBook(Book book) throws IOException;

    /**
     * 根据提供的编号获取一本数据的详细信息
     */
    public Book getBookByCode(String code) throws IOException;

    /**
     * 根据提供的书名，查找返回符合条件的图书。
     * 例如：输入 “哲学”，可能返回所有书名中包含哲学的书
     */
    public List<Book> findBookByName(String code);

    /**
     * 根据提供的编号删除一本数据的详细信息
     */
    public void deleteBookByCode(String code);

    /**
     * 查找给定价格区间的图书
     */
    public List<Book> findBookByPrice(float minPrice, float maxPrice);

}
