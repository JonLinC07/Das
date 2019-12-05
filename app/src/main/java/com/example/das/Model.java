package com.example.das;

import android.provider.BaseColumns;

public final class Model {
    private Model() {}

    public static class Alumnos implements BaseColumns {
        public static String TABLE_NAME = "Alumnos";
        public static String COLUMN_NAME_EXPEDIENTE = "expediente";
        public static String COLUMN_NAME_NOMBRE = "nombre";
        public static String COLUMN_NAME_LIC = "licencitura";
        public static String COLUMN_NAME_EMAIL = "correo";
        public static String COLUMN_NAME_PHONE = "telefono";
        public static String COLUMN_NAME_PASSWORD = "contraseña";
        public static String COLUMN_NAME_CREDITOS = "creditos";
        public static String COLUMN_NAME_PROMEDIO = "promedio";
        public static String COLUMN_NAME_CAMPUS = "campus";
    }

    public static final String SQL_CREATE_ALUMNOS = "CREATE TABLE " + Alumnos.TABLE_NAME + " (" +
            Alumnos.COLUMN_NAME_EXPEDIENTE + " INTEGER PRIMARY KEY, " +
            Alumnos.COLUMN_NAME_NOMBRE + " TEXT NOT NULL, " +
            Alumnos.COLUMN_NAME_LIC + " TEXT NOT NULL, " +
            Alumnos.COLUMN_NAME_EMAIL + " TEXT NOT NULL, " +
            Alumnos.COLUMN_NAME_PHONE + " TEXT NOT NULL, " +
            Alumnos.COLUMN_NAME_PASSWORD + " TEXT NOT NULL, " +
            Alumnos.COLUMN_NAME_PROMEDIO + " REAL, " +
            Alumnos.COLUMN_NAME_CREDITOS + " INTEGER, " +
            Alumnos.COLUMN_NAME_CAMPUS + " TEXT)";

    public static final String SQL_DELETE_ALUMNOS = "DROP TABLE IF EXISTS " + Alumnos.TABLE_NAME;


    public static final String SQL_GET_SESSION_INFO = "SELECT " + Alumnos.COLUMN_NAME_EXPEDIENTE + ", " +
            Alumnos.COLUMN_NAME_PASSWORD + " FROM " + Alumnos.TABLE_NAME;


    public static class Destinos implements BaseColumns {

        public static String TABLE_NAME = "Destinos";
        public static String COLUMN_NAME_ID = "id";
        public static String COLUMN_NAME_CIUDAD = "ciudad";
        public static String COLUMN_NAME_PAIS = "pais";
        public static String COLUMN_NAME_COORDENADAS = "coordenadas";
        public static String COLUMN_NAME_UNIVERSIDAD = "universidad";
        public static String COLUMN_NAME_LINK = "link";
    }

    public static final String SQL_CREATE_DESTINOS = "CREATE TABLE " + Destinos.TABLE_NAME + " (" +
            Destinos.COLUMN_NAME_ID + " INTEGER, " +
            Destinos.COLUMN_NAME_CIUDAD + " TEXT NOT NULL, " +
            Destinos.COLUMN_NAME_PAIS + " TEXT NOT NULL, " +
            Destinos.COLUMN_NAME_COORDENADAS + " TEXT NOT NULL, " +
            Destinos.COLUMN_NAME_UNIVERSIDAD + " TEXT NOT NULL, " +
            Destinos.COLUMN_NAME_LINK + " TEXT NOT NULL);";

    public static final String SQL_DELETE_DESTINOS = "DROP TABLE IF EXISTS " + Destinos.TABLE_NAME;

    public static final String SQL_INSERT_DESTINOS = "INSERT INTO " + Destinos.TABLE_NAME + "( " +
            Destinos.COLUMN_NAME_ID + ", " + ", " + Destinos.COLUMN_NAME_CIUDAD + ", " +
            Destinos.COLUMN_NAME_PAIS + ", " + Destinos.COLUMN_NAME_COORDENADAS +
            Destinos.COLUMN_NAME_UNIVERSIDAD + ", " + Destinos.COLUMN_NAME_LINK + ")VALUES ( " +
            "'Ciudad de Mexico', 'México', 'geo:19.332465,-99.1869765,17', 'Universidad Nacional Autonoma de México (UNAM)', " +
            "'https://es.wikipedia.org/wiki/Universidad_Nacional_Aut%C3%B3noma_de_M%C3%A9xico'), " +
            "('Guadalajara', 'Mexico', 'geo:20.6947053,-103.4203198', 'Universidad Autonoma de Guadlajara (UAG)', " +
            "'https://es.wikipedia.org/wiki/Universidad_Aut%C3%B3noma_de_Guadalajara'), " +
            "('Vancuver', 'Canada', 'geo:49.2602794,-123.2298715', 'University of British Columbia (UBC)', " +
            "'https://es.wikipedia.org/wiki/Universidad_de_Columbia_Brit%C3%A1nica'), " +
            "'Toronto', 'Canada', 'geo:43.6628917,-79.3978504', 'University of Canada', " +
            "'https://es.wikipedia.org/wiki/Universidad_de_Toronto');";
}
