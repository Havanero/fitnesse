package fitnesse.wikitext.parser;

import fitnesse.html.HtmlElement;
import org.junit.Test;

public class TableTokenTest {
    @Test public void scansTables() {
        ParserTest.assertScans("|a|\n", "Table=|,Text=a,EndCell=|\n");
        ParserTest.assertScans("!|a|\n", "Table=!|,Text=a,EndCell=|\n");
    }

    @Test public void translatesTables() {
        ParserTest.assertTranslates("|a|\n", tableWithCell("a"));
        ParserTest.assertTranslates("||\n", tableWithCell(""));
        ParserTest.assertTranslates("| a |\n", tableWithCell("a"));
        ParserTest.assertTranslates("|''a''|\n", tableWithCell("<i>a</i>"));
        ParserTest.assertTranslates("!|''a''|\n", tableWithCell("''a''"));

        ParserTest.assertTranslates("|a|b|c|\n|d|e|f|\n",
                "<table border=\"1\" cellspacing=\"0\">" + HtmlElement.endl +
                "\t<tr>" + HtmlElement.endl +
                "\t\t<td>a</td>" + HtmlElement.endl +
                "\t\t<td>b</td>" + HtmlElement.endl +
                "\t\t<td>c</td>" + HtmlElement.endl +
                "\t</tr>" + HtmlElement.endl +
                "\t<tr>" + HtmlElement.endl +
                "\t\t<td>d</td>" + HtmlElement.endl +
                "\t\t<td>e</td>" + HtmlElement.endl +
                "\t\t<td>f</td>" + HtmlElement.endl +
                "\t</tr>" + HtmlElement.endl +
                "</table>"+ HtmlElement.endl);
    }

    private String tableWithCell(String cellContent) {
        return "<table border=\"1\" cellspacing=\"0\">"+ HtmlElement.endl +
        "\t<tr>" + HtmlElement.endl +
        "\t\t<td>" + cellContent + "</td>" + HtmlElement.endl +
        "\t</tr>" + HtmlElement.endl +
        "</table>"+ HtmlElement.endl;
    }
}
