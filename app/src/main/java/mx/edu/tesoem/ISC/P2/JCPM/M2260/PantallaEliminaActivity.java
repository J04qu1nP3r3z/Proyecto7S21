package mx.edu.tesoem.ISC.P2.JCPM.M2260;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PantallaEliminaActivity extends AppCompatActivity {

    List<String> Datos;
    int lin;
    TextView txtPEMat, txtPENom, txtPEEdad, txtPESem, txtPEProm, txtPEEdo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_elimina);

        txtPEMat = findViewById(R.id.txtPEMat);
        txtPENom = findViewById(R.id.txtPENom);
        txtPEEdad = findViewById(R.id.txtPEEdad);
        txtPESem = findViewById(R.id.txtPESem);
        txtPEProm = findViewById(R.id.txtPEProm);
        txtPEEdo = findViewById(R.id.txtPEEdo);

        Bundle Dtos = getIntent().getExtras();
        String m =Dtos.getString("Matricula").toString();
        lin = Dtos.getInt("Linea");

        txtPEMat.setText(Dtos.getString("Matricula"));
        txtPENom.setText(Dtos.getString("Nombre"));
        txtPEEdad.setText(Dtos.getString("Edad"));
        txtPESem.setText(Dtos.getString("Semestre"));
        txtPEProm.setText(Dtos.getString("Promedio"));
        txtPEEdo.setText(Dtos.getString("Edo"));

    }

    public void Aceptar(View jcpm){
        Archivo archivos = new Archivo();
        boolean resp =  archivos.LeerFile(this);
        if(resp) {
            Datos = archivos.RegresaInfo();
            Datos.remove(lin-1);
            archivos.Grabar(this,Datos);
        }

        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra("resp", "Datos Eliminados Correctamente");
        intent.putStringArrayListExtra("Dto", (ArrayList<String>) Datos);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    public void Cancelar(View jcpm){

        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra("resp", "Proceso Cancelado, no se Elimino el registro");
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}