package mx.edu.tesoem.ISC.P2.JCPM.M2260;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Archivo {
    public final String NomArch = "JCPMP27S21.txt";
    private List<String> datos = new ArrayList<>();

    public boolean Grabar(Context context, List<String> Dato) {

        try {
            OutputStreamWriter archivo =
                    new OutputStreamWriter(context.openFileOutput(NomArch, Activity.MODE_PRIVATE));
            for (String line : Dato) {
                archivo.write(line + "\n");
            }

            archivo.flush();
            archivo.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean LeerFile(Context context) {
        try {
            InputStreamReader archivo = new InputStreamReader(context.openFileInput(NomArch));
            BufferedReader br = new BufferedReader(archivo);

            String line = "";
            while ((line = br.readLine()) != null) {
                datos.add(line);
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }

    public List<String> RegresaInfo() {
        return datos;
    }
}