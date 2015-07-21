import javafx.collections.ObservableList;

/**
 * A class to load initial entries into Diary application automatically for demo purposes
 *
 * @author Graham McAllister
 * @version 1.0
 */
public class EntryLoader {

    public static void loadEntries(ObservableList<Entry> list, PatriciaTrie titles) {
        Entry a = new Entry();
        a.setBody("The open championship ends today.");
        a.setTitle("Golfers are playing");
        Entry b = new Entry();
        b.setBody("Jordan Spieth is going to win the Open championship.");
        b.setTitle("Golf");
        Entry c = new Entry();
        c.setBody("Video games are popular.");
        c.setTitle("Games");
        Entry d = new Entry();
        d.setBody("Tiger Woods is struggling.");
        d.setTitle("Golfing community");
        Entry e = new Entry();
        e.setBody("This is my todo list");
        e.setTitle("TODO");
        Entry f = new Entry();
        f.setBody("Tiger missed the cut this weekend");
        f.setTitle("Tiger Woods");
        Entry g = new Entry();
        g.setBody("I did my project on tries.");
        g.setTitle("Tries");
        Entry h = new Entry();
        h.setBody("Zach Johnson won this year.");
        h.setTitle("Golf British Open");
        Entry i = new Entry();
        i.setBody("Treaps are another data structure we learned about.");
        i.setTitle("Treaps");
        Entry j = new Entry();
        j.setBody("We learned about many tree structures and balancing methods in cs1332.");
        j.setTitle("Trees");
        Entry k = new Entry();
        k.setBody("Have a great day!");
        k.setTitle("Happy birthday");
        Entry l = new Entry();
        l.setBody("There is a new house for sale in your neighborhood.");
        l.setTitle("Home for sale");
        Entry m = new Entry();
        m.setBody("This is a reminder to reapply to financial aid.");
        m.setTitle("Hope scholarship");
        //Add all entries to entryList
        list.addAll(a, b, c, d, e, f, g, h, i, j, k, l, m);
        //Add all titles to trie
        titles.add(a.getTitle());
        titles.add(b.getTitle());
        titles.add(c.getTitle());
        titles.add(d.getTitle());
        titles.add(e.getTitle());
        titles.add(f.getTitle());
        titles.add(g.getTitle());
        titles.add(h.getTitle());
        titles.add(i.getTitle());
        titles.add(j.getTitle());
        titles.add(k.getTitle());
        titles.add(l.getTitle());
        titles.add(m.getTitle());
    }
}
