package com.github.jhanne82.documenttree.document;

public class Term<T> {

    private final int index;
    private final T   content;

    public Term( int index, T content ) {
        this.index = index;
        this.content = content;
    }


    public int getIndex() {
        return index;
    }


    public T getContent() {
        return content;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Term<?> term = (Term<?>) o;

        return index == term.index && (content != null ? content.equals(term.content)
                                                       : term.content == null);
    }



    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
