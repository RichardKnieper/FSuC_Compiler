package domain;

public class BasicRange { // Abschnitte von Buchstaben ohne Lücken
    char start, end;
    public BasicRange(char a, char b) {
        start = a;
        end = b;
    }
    public int compare(char a, char b) { // -2 means b<start-1, -1 means b==start-1, 0 means [a,b] and [start,end] have common elements,
        // 1 means a==end+1, 2 means a>end+1
        if (a==end+1) return 1;
        else if (a>end+1) return 2;
        else if (b==start-1) return -1;
        else if (b<start-1) return -2;
        else return 0;
    }
    public boolean isSingle() { return start==end; }
    public String toString() {
        if (isSingle()) return "'"+start+"'";
        else return "['"+start+"' - '"+end+"']";
    }

    public char getStart() {
        return start;
    }

    public char getEnd() {
        return end;
    }
}
