import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 *
 * @author Nabeu Habteaslassa
 *
 */
public final class RSSAggregator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSAggregator() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * <html> <head> <title>the channel tag title as the page title</title>
     * </head> <body>
     * <h1>the page title inside a link to the <channel> link</h1>
     * <p>
     * the channel description
     * </p>
     * <table border="1">
     * <tr>
     * <th>Date</th>
     * <th>Source</th>
     * <th>News</th>
     * </tr>
     *
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the root of channel is a <channel> tag] and out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        //head
        out.println("<html>");
        out.println("<head>");
        out.print("<title>");
        String title = "Empty Title";
        if (channel.child(getChildElement(channel, "title"))
                .numberOfChildren() > 0) {
            title = channel.child(getChildElement(channel, "title")).child(0)
                    .label();
        }
        out.print(title);
        out.println("</title>");
        out.println("</head>");

        //body
        out.println("<body>");

        //outputs header
        String link = channel.child(getChildElement(channel, "link")).child(0)
                .label();
        out.println("<h1><a href=\"" + link + "\">" + title + "</a></h1>");

        //outputs description
        out.println("<p>");
        String description = "No description";
        if (channel.child(getChildElement(channel, "description"))
                .numberOfChildren() > 0) {
            description = channel.child(getChildElement(channel, "description"))
                    .child(0).label();
        }
        out.print(description);
        out.println("</p>");

        //creates table
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>Date</th>");
        out.println("<th>Source</th>");
        out.println("<th>News</th>");
        out.println("</tr>");
    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * </table>
     * </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        int indx = 0;
        while (indx < xml.numberOfChildren()
                && !xml.child(indx).label().equals(tag)) {
            indx++;
        }
        if (indx >= xml.numberOfChildren()) {
            indx = -1;
        }
        return indx;
    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the label of the root of item is an <item> tag] and
     *           out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]
     * </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        //Pub date table cell
        out.println("<tr>");
        out.print("<td>");
        String pubDate = "No date available";
        int pubDateIndx = getChildElement(item, "pubDate");
        if (pubDateIndx >= 0) {
            pubDate = item.child(pubDateIndx).child(0).label();
        }
        out.print(pubDate);
        out.println("</td>");

        //source table cell
        out.print("<td>");
        int sourceIndx = getChildElement(item, "source");
        String source = "No source available";
        if (sourceIndx >= 0) {
            out.print("<a href=\"");
            out.print(item.child(sourceIndx).attributeValue("url"));
            out.print("\">");

            if (item.child(sourceIndx).numberOfChildren() > 0) {
                source = item.child(sourceIndx).child(0).label();
            }
            out.print(source);
            out.println("</a></td>");
        } else {
            out.print(source);
            out.println("</td>");
        }

        //title/description table cell (link)
        out.print("<td>");

        int linkIndx = getChildElement(item, "link");
        boolean hasLink = false;
        if (linkIndx >= 0) {
            hasLink = true;
            out.print("<a href=\"");
            out.print(item.child(linkIndx).child(0).label());
            out.print("\">");
        }

        //title/description table cell (title/description)
        int titleIndx = getChildElement(item, "title");
        int descriptionIndx = getChildElement(item, "description");
        String title = "No title available";
        if (titleIndx >= 0 && item.child(titleIndx).numberOfChildren() > 0) {
            title = item.child(titleIndx).child(0).label();

        } else if ((descriptionIndx >= 0
                && item.child(descriptionIndx).numberOfChildren() > 0)) {
            title = item.child(descriptionIndx).child(0).label();

        }
        out.print(title);
        if (hasLink) {
            out.println("</a></td>");
        } else {
            out.println("</td>");
        }
    }

    /**
     * Processes one XML RSS (version 2.0) feed from a given URL converting it
     * into the corresponding HTML output file.
     *
     * @param url
     *            the URL of the RSS feed
     * @param file
     *            the name of the HTML output file
     * @param out
     *            the output stream to report progress or errors
     * @updates out.content
     * @requires out.is_open
     * @ensures <pre>
     * [reads RSS feed from url, saves HTML document with table of news items
     *   to file, appends to out.content any needed messages]
     * </pre>
     */
    private static void processFeed(String url, String file, SimpleWriter out) {
        //Input the source URL.
        XMLTree xml = new XMLTree1(url);

        //checks if valid RSS 2.0
        if (xml.label().equals("rss") && xml.hasAttribute("version")
                && xml.attributeValue("version").equals("2.0")) {

            //creates .html file
            SimpleWriter fileOut = new SimpleWriter1L("doc/" + file);

            //outputs header
            /**
             * didn't do child at 0 because I found RSS 2.0 feeds where channel
             * was child 1
             */
            XMLTree channel = xml.child(getChildElement(xml, "channel"));
            outputHeader(channel, fileOut);

            //fills table in .html file
            for (int indx = 0; indx < channel.numberOfChildren(); indx++) {
                if (channel.child(indx).label().equals("item")) {
                    processItem(channel.child(indx), fileOut);
                }
            }
            //finishes .html file
            outputFooter(fileOut);
            fileOut.close();
        } else {
            out.println("URL isn't to an to an RSS 2.0 file.");
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //Get url for RSS feeds
        out.print(
                "Enter an input XML file containing a list of RSS 2.0 Feeds: ");
        XMLTree feed = new XMLTree1(in.nextLine());

        //Creates Home page
        out.print("Enter the name of an output .html file: ");
        SimpleWriter fileOut = new SimpleWriter1L("doc/" + in.nextLine());

        //Prints title and header to file
        fileOut.println("<html>");
        fileOut.println("  <head>");
        fileOut.println(
                "    <title>" + feed.attributeValue("title") + "</title>");
        fileOut.println("  </head>");

        fileOut.println("  <body>");
        fileOut.println("    <h2>" + feed.attributeValue("title") + "</h2>");
        fileOut.println("    <ul>");

        //Loops through all RSS feeds from url and creates their pages
        //Also prints them in an unordered list w links to html files to the homepage
        for (int indx = 0; indx < feed.numberOfChildren(); indx++) {
            processFeed(feed.child(indx).attributeValue("url"),
                    feed.child(indx).attributeValue("file"), out);
            fileOut.println("      <li><a href=\""
                    + feed.child(indx).attributeValue("file") + "\">"
                    + feed.child(indx).attributeValue("name") + "</a></li>");

        }
        fileOut.println("    </ul>");
        fileOut.println("  </body>");
        fileOut.println("</html>");

        fileOut.close();
        in.close();
        out.close();
    }

}
