package mx.edu.tesoem.ISC.P2.JCPM.M2260;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PantallaAgregarActivity extends AppCompatActivity {

    List<String> Datos;
    TextView txtPAMat, txtPANom, txtPAEdad, txtPASem, txtPAProm, txtPAEdo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar);

        txtPAMat = findViewById(R.id.txtPAMat);
        txtPANom = findViewById(R.id.txtPANom);
        txtPAEdad = findViewById(R.id.txtPAEdad);
        txtPASem = findViewById(R.id.txtPASem);
        txtPAProm = findViewById(R.id.txtPAProm);
        txtPAEdo = findViewById(R.id.txtPAEdo);

        Bundle Dtos = getIntent().getExtras();

        txtPAMat.setText(Dtos.getString("Matricula"));
        txtPANom.setText(Dtos.getString("Nombre"));
        txtPAEdad.setText(Dtos.getString("Edad"));
        txtPASem.setText(Dtos.getString("Semestre"));
        txtPAProm.setText(Dtos.getString("Promedio"));
        txtPAEdo.setText(Dtos.getString("Edo"));
    }

    public void Enviar(View jcpm){
        Archivo archivos = new Archivo();
        boolean resp =  archivos.LeerFile(this);
        if(resp) {
            Datos = archivos.RegresaInfo();
        }

        Intent intent = new Intent(this, PrincipalActivity.class);

        intent.putExtra("resp", "Datos Guardados Correctamente");
        intent.putStringArrayListExtra("Dto", (ArrayList<String>) Datos);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}