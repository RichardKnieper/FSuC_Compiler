package domain;

public class State {
    String label;
    int accept;

    public State(String l) {
        label = l;
    }
    public State(String l, int a) {
        this(l);
        accept = a;
    }
    public String toString() {
        return "(" + label + ", "+accept+")";
    }

    public String getLabel() {
        return label;
    }

    public int getAccept() {
        return accept;
    }
}
