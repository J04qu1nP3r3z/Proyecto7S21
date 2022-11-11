package mx.edu.tesoem.ISC.P2.JCPM.M2260;

import android.os.Parcel;
import android.os.Parcelable;

public class Datos implements Parcelable {
    private String Matricula;
    private String Nombre;
    private int Edad;
    private String Semestre;
    private double Promedio;
    private String Estado;

    public Datos() {
    }

    public Datos(String matricula, String nombre, int edad, String semestre, double promedio, String estado) {
        this.Matricula = matricula;
        this.Nombre = nombre;
        this.Edad = edad;
        this.Semestre = semestre;
        this.Promedio = promedio;
        this.Estado = estado;
    }

    protected Datos(Parcel in) {
        Matricula = in.readString();
        Nombre = in.readString();
        Edad = in.readInt();
        Semestre = in.readString();
        Promedio = in.readDouble();
        Estado = in.readString();
    }

    public static final Creator<Datos> CREATOR = new Creator<Datos>() {
        @Override
        public Datos createFromParcel(Parcel in) {
            return new Datos(in);
        }

        @Override
        public Datos[] newArray(int size) {
            return new Datos[size];
        }
    };

    public String getMatricula() {return Matricula;}

    public void setMatricula(String matricula) {Matricula = matricula;}

    public String getNombre() {return Nombre;}

    public void setNombre(String nombre) {Nombre = nombre;}

    public int getEdad() {return Edad;}

    public void setEdad(int edad) {Edad = edad;}

    public String getSemestre() {return Semestre;}

    public void setSemestre(String semestre) {Semestre = semestre;}

    public double getPromedio() {return Promedio;}

    public void setPromedio(double promedio) {Promedio = promedio;}

    public String getEstado() {return Estado;}

    public void setEstado(String estado) {Estado = estado;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Matricula);
        parcel.writeString(Nombre);
        parcel.writeInt(Edad);
        parcel.writeString(Semestre);
        parcel.writeDouble(Promedio);
        parcel.writeString(Estado);
    }
}
