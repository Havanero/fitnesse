package fitnesse.wikitext.parser;

import fitnesse.html.HtmlElement;
import fitnesse.wiki.InMemoryPage;
import fitnesse.wiki.PageCrawler;
import fitnesse.wiki.PathParser;
import fitnesse.wiki.WikiPage;
import org.junit.Test;

public class TextTokenTest {
    @Test public void scansTextAsWords() {
        ParserTest.assertScans("hi mom", "Text=hi,Whitespace= ,Text=mom");
    }

    @Test public void translatesText() {
        ParserTest.assertTranslates("hi mom", "hi mom");
        ParserTest.assertTranslates("Hi MOM", "Hi MOM");
        ParserTest.assertTranslates("Hi+Mom", "Hi+Mom");
        ParserTest.assertTranslates(".", ".");
        ParserTest.assertTranslates("<hi>", "&lt;hi&gt;");
        ParserTest.assertTranslates("\rmore\rstuff\r", "morestuff");
    }

    @Test public void translatesWikiWords() throws Exception {
        WikiPage root = InMemoryPage.makeRoot("RooT");
        PageCrawler crawler = root.getPageCrawler();
        WikiPage pageOne = crawler.addPage(root, PathParser.parse("PageOne"));
        WikiPage pageOneTwo = crawler.addPage(pageOne, PathParser.parse("PageTwo"));
        WikiPage pageOneThree = crawler.addPage(pageOne, PathParser.parse("PageThree"));

        ParserTest.assertTranslates(pageOne, "PageOne", "<a href=\"PageOne\">PageOne</a>" + HtmlElement.endl);
        ParserTest.assertTranslates(pageOneTwo, "PageTwo", "<a href=\"PageOne.PageTwo\">PageTwo</a>" + HtmlElement.endl);
        ParserTest.assertTranslates(pageOneThree, ".PageOne", "<a href=\"PageOne\">.PageOne</a>" + HtmlElement.endl);

        //ParserTest.assertTranslates("Wiki.Word", "<a href=\"Wiki.Word\">Wiki.Word</a>" + HtmlElement.endl);
    }
}
