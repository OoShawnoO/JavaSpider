package cn.edu.swu.ws.book;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// TODO: 将 BookDataSource 中定义的新增接口通过抛出异常的方式实现
public class WebBookDataSource implements BookDataSource {

    private String url = null;

    public WebBookDataSource(String url) {
        this.setUrl(url);
    }

    @Override
    public List<Book> getNewBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        String html = HttpTools.getHtml(this.getUrl(), "GB2312");
        String[] lines = html.split("\n");
        boolean start = false;
        for(String line : lines) {
            if (line.contains("rollitem")) {
                start = true;
            }

            if (start) {
                if (line.contains("title")) {
                    Book book = this.parseBookFromLine(line);
                    books.add(book);
                }
            }

            if (line.contains("</ul>")) {
                start = false;
            }
        }

        return books;
    }

    @Override
    public void saveNewBook(Book book) {
        throw new java.lang.UnsupportedOperationException("不能保存书");
    }

    @Override
    public Book getBookByCode(String code) {
        throw new java.lang.UnsupportedOperationException("WebBookDataSource类型不能通过Code获得Book.");
    }

    @Override
    public List<Book> findBookByName(String code) {
        throw new java.lang.UnsupportedOperationException("WebBookDataSource类型不能通过Name获得Book.");
    }

    @Override
    public void deleteBookByCode(String code) {
        throw new java.lang.UnsupportedOperationException("WebBookDataSource类型不能通过Code删除Book.");
    }

    @Override
    public List<Book> findBookByPrice(float minPrice, float maxPrice) {
        throw new java.lang.UnsupportedOperationException("WebBookDataSource类型不能通过Price区间获得Book.");
    }

    private Book parseBookFromLine(String line) {

        String html = line.substring(line.indexOf("<a  title=\"") + "<a  title=\"".length());
        //System.out.println(html);
        //System.out.println(html.substring(html.indexOf("img src='")+"img src='".length(),html.indexOf("' alt=")));
        String name = html.substring(0, html.indexOf("\"  class="));



        // TODO : 这里获取的是书的详细页地址，不是图片地址，需要修改
        html = html.substring(html.indexOf("href=\"")+ "href=\"".length());

        // TODO: 获取书的编码信息
        String code = html.substring(html.indexOf("dangdang.com/")+"dangdang.com/".length(),html.indexOf(".html"));

        String imgUrl = "http:"+html.substring(html.indexOf("img src='")+"img src='".length(),html.indexOf("' alt="));

        html = html.substring(html.indexOf("<span class=\"num\">") + "<span class=\"num\">".length());
        String price = html.substring(0, html.indexOf("</span>"));

        Book book = new Book();
        book.setCode(code);
        book.setName(name);
        book.setPrice(Float.parseFloat(price));
        book.setImageUrl(imgUrl);
        return book;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
