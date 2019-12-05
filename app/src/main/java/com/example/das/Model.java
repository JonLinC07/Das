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

    public static class Destinos implements BaseColumns {
        public static String TABLE_NAME = "Destinos";
        public static String COLUMN_NAME_ID = "id";
        public static String COLUMN_NAME_NOMBRE = "nombre";
        public static String COLUMN_NAME_LUGAR = "coordenadas";
        public static String COLUMN_NAME_UNIVERSIDAD = "universidad";
        public static String COLUMN_NAME_LINK = "link";
    }

    public static final String SQL_CREATE_DESTINOS = "CREATE TABLE " + Destinos.TABLE_NAME + " (" +
            Destinos.COLUMN_NAME_ID + " INTEGER, " +
            Destinos.COLUMN_NAME_NOMBRE + " TEXT NOT NULL, " +
            Destinos.COLUMN_NAME_LUGAR + " TEXT NOT NULL, " +
            Destinos.COLUMN_NAME_UNIVERSIDAD + " TEXT NOT NULL, " +
            Destinos.COLUMN_NAME_LINK + " TEXT NOT NULL)";

    public static final String SQL_DELETE_DESTINOS = "DROP TABLE IF EXISTS " + Destinos.TABLE_NAME;
}
