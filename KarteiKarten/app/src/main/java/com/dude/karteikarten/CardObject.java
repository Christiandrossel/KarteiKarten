package com.dude.karteikarten;

public class CardObject {

    String id;
    String Frage;
    String Antwort;
    static String lessonTitle;

    public CardObject() {
    }

    public CardObject(String id, String frage, String antwort) {
        this.id = id;
        Frage = frage;
        Antwort = antwort;
    }

    public void setValue(String id, String frage, String antwort){
        this.id = id;
        Frage = frage;
        Antwort = antwort;
    }

    public void setValue(String frage, String antwort){
        Frage = frage;
        Antwort = antwort;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrage() {
        return Frage;
    }

    public void setFrage(String frage) {
        Frage = frage;
    }

    public String getAntwort() {
        return Antwort;
    }

    public void setAntwort(String antwort) {
        Antwort = antwort;
    }

    @Override
    public String toString() {
        return "CardObject{" +
                "Frage='" + Frage + '\'' +
                '}';
    }
}
