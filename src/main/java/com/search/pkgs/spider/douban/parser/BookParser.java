package com.search.pkgs.spider.douban.parser;

import com.alibaba.fastjson.JSON;
import com.search.pkgs.model.entity.BookEntity;
import com.search.pkgs.util.ListUtil;
import com.search.pkgs.util.RandomUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-15 17:56
 */
@Slf4j
public class BookParser {

    public static List<BookEntity> parse(String html) {
        Document document = Jsoup.parse(html);
        Elements tables = document.select("table");

        List<BookEntity> list = new ArrayList<>();
        for (Element table : tables) {
            BookEntity book = parseToBook(table);
            list.add(book);
        }

        if (log.isDebugEnabled()) {
            log.info("Function[parse]values:{}", JSON.toJSONString(list, true));
        }
        return list;
    }

    private static BookEntity parseToBook(Element table) {
        String bookId = RandomUtil.timestamp();

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookId);
        bookEntity.setIsbn(bookId);
        Element bookNameElement = firstOfIt(table, ".pl2 a");
        Element authorElement = firstOfIt(table, ".pl");
        Element rateElement = firstOfIt(table, ".rating_nums");
        Element descElement = firstOfIt(table, ".inq");

        //<p class="pl">[英] 阿加莎·克里斯蒂 / 陈尧光 / 人民文学出版社 / 2006-5 / 18.00元</p>
        List<String> tags = new ArrayList<>();
        if (Objects.nonNull(bookNameElement)) {
            String text = bookNameElement.text();

            tags.add(text);
            bookEntity.setBookName(text);
        }

        if (Objects.nonNull(authorElement)) {
            String authorsText = authorElement.text();
            int left = authorsText.indexOf("]");
            int right = authorsText.indexOf(" /");
            if (left == right) {
                left = 0;
            } else {
                left = left + 1;
            }
            String authorName = authorsText.substring(left, right);
            authorName = authorName.trim();
            bookEntity.setAuthor(authorName.trim());
            tags.add(authorName);
        }

        if (Objects.nonNull(rateElement)) {
            String rateText = rateElement.text();
            double rateNum = Double.parseDouble(rateText);
            bookEntity.setScore(rateNum);
        }

        if (Objects.nonNull(descElement)) {
            String descText = descElement.text();
            bookEntity.setDescription(descText);
        }

        bookEntity.setTags(tags);
        bookEntity.setPublishTime(new Date());
        return bookEntity;
    }

    private static Element firstOfIt(Element element, String selector) {
        Elements elements = element.select(selector);
        return ListUtil.getFirstElement(elements);
    }
}
