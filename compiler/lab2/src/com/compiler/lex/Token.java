package com.compiler.lex;

public class Token {
    public String tokenClass;
    public String tokenValue;
    public int tokenId;
    public int row;
    public int beginclo;
    public int endclo;

    public Token(String tokenClass){
        this.tokenClass = tokenClass;
        setTokenId();
    }

    public Token(String tokenClass, String tokenValue){
        this.tokenClass = tokenClass;
        this.tokenValue = tokenValue;
        setTokenId();
    }

    public Token(String tokenClass, String tokenValue, int row, int beginclo, int endclo){
        this.tokenClass = tokenClass;
        this.tokenValue = tokenValue;
        this.row = row;
        this.beginclo = beginclo;
        this.endclo = endclo;
        setTokenId();
    }

    public void setTokenId(){
        tokenId = 0;
        if(!Table.lookUp(tokenValue)){
            tokenId = Table.table.get(tokenClass);
        }
    }

    public String getTokenClass() {
        return tokenClass;
    }

    public void setTokenClass(String tokenClass) {
        this.tokenClass = tokenClass;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
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


    public int getTokenId() {
        return tokenId;
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
