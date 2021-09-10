package domain;
// Eine Menge von Buchstaben

import java.util.LinkedList;

public class Range {
    private LinkedList<BasicRange> element = new LinkedList<>();

    public Range() { }
    public Range(char a) { this(a,a); }
    public Range(char a, char b) { element.add(new BasicRange(a,b)); }
    public Range add(char a) { return add(a,a); }
    public Range add(char a, char b) { // füge den Bereich [a - b] hinzu
        loop:for (int i=0; i<element.size(); i++) {
            BasicRange currentElement = element.get(i);
            switch (currentElement.compare(a, b)) {
                case -2: element.add(i, new BasicRange(a,b)); break loop;
                case -1: currentElement.start = a; break loop;
                case 1: currentElement.end = b; break loop;
                case 2: if (i==element.size()-1) { element.add(new BasicRange(a,b)); break loop; }
                    break;
                default:
                    if (a<currentElement.start) currentElement.start=a;
                    if (b>currentElement.end) currentElement.end = b;
                    break loop;
            }
        }
        return this;
    }

    public boolean isEmpty() { return element.size()==0; }

    public String toString() {
        String erg = "{ ";
        boolean first = true;
        for (BasicRange cr:element) {
            if (!first) erg += ", ";
            else first=false;
            erg += cr.toString();
        }
        erg += " }";
        return erg;
    }

    public static Range getUniversalRange() { return new Range((char) 0, (char) 0xffff); }
    public static Range getEmptyRange() { return new Range(); }

    public LinkedList<BasicRange> getElement() {
        return element;
    }
}
