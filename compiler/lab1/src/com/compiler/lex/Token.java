package com.compiler.lex;

public class Token<T> {
    public String tokenClass;
    public T tokenValue;
    public int row;
    public int beginclo;
    public int endclo;

    public Token(String tokenClass){
        this.tokenClass = tokenClass;
    }

    public Token(String tokenClass, T tokenValue){
        this.tokenClass = tokenClass;
        this.tokenValue = tokenValue;
    }

    public Token(String tokenClass, T tokenValue, int row, int beginclo, int endclo){
        this.tokenClass = tokenClass;
        this.tokenValue = tokenValue;
        this.row = row;
        this.beginclo = beginclo;
        this.endclo = endclo;
    }

    public String getTokenClass() {
        return tokenClass;
    }

    public void setTokenClass(String tokenClass) {
        this.tokenClass = tokenClass;
    }

    public T getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(T tokenValue) {
        this.tokenValue = tokenValue;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getBeginclo() {
        return beginclo;
    }

    public void setBeginclo(int beginclo) {
        this.beginclo = beginclo;
    }

    public int getEndclo() {
        return endclo;
    }

    public void setEndclo(int endclo) {
        this.endclo = endclo;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenClass='" + tokenClass + '\'' +
                ", tokenValue='" + tokenValue + '\'' +
                ", row=" + row +
                ", beginclo=" + beginclo +
                ", endclo=" + endclo +
                '}';
    }
}
