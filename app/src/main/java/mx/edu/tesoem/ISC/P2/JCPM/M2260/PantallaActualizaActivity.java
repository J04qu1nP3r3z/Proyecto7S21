package mx.edu.tesoem.ISC.P2.JCPM.M2260;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PantallaActualizaActivity extends AppCompatActivity {

    List<String> Datos;
    int lin;
    TextView txtAMat, txtANom, txtAEdad, txtASem, txtAProm, txtAEdo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_actualiza);


        txtAMat = findViewById(R.id.txtAMat);
        txtANom = findViewById(R.id.txtANom);
        txtAEdad = findViewById(R.id.txtAEdad);
        txtASem = findViewById(R.id.txtASem);
        txtAProm = findViewById(R.id.txtAProm);
        txtAEdo = findViewById(R.id.txtAEdo);

        Bundle Dtos = getIntent().getExtras();
        lin = Dtos.getInt("Linea");
        txtAMat.setText(Dtos.getString("Matricula"));
        txtANom.setText(Dtos.getString("Nombre"));
        txtAEdad.setText(Dtos.getString("Edad"));
        txtASem.setText(Dtos.getString("Semestre"));
        txtAProm.setText(Dtos.getString("Promedio"));
        double promedio = Double.parseDouble(txtAProm.getText().toString());
        String edo = "";
        if(promedio>=7)
            edo="Aprobado";
        else
            edo="No Acreditado";
        txtAEdo.setText(edo);

    }

    public void Aceptar(View jcpm){
        Archivo archivos = new Archivo();
        boolean resp =  archivos.LeerFile(this);
        Datos datos = new Datos(
                txtAMat.getText().toString(),
                txtANom.getText().toString(),
                Integer.parseInt(txtAEdad.getText().toString()),
                txtASem.getText().toString(),
                Double.parseDouble(txtAProm.getText().toString()),
                txtAEdo.getText().toString());
        Gson gson = new Gson();
        String cadena = gson.toJson(datos);
        if(resp) {
            Datos = archivos.RegresaInfo();
            Datos.set(lin-1,cadena);
            archivos.Grabar(this,Datos);
        }

        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra("resp", "Datos Actualizados Correctamente");
        intent.putStringArrayListExtra("Dto", (ArrayList<String>) Datos);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    public void Cancelar(View jcpm){

        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra("resp", "Proceso Cancelado, no se Actualizo el registro");
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}