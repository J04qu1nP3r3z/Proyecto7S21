package mx.edu.tesoem.ISC.P2.JCPM.M2260;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
public class PrincipalActivity extends AppCompatActivity {

    GridView GVDtos;
    String[] informacion;
    ArrayAdapter<String> arreglo;
    ArrayList<Datos> items;
    List<String> Datos;
    String cadenas;
    EditText txtMatri, txtNom, txtEdad, txtSem, txtProm, txtEdo;
    int line=0;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent datos1 = result.getData();
            if(result.getResultCode()== Activity.RESULT_OK){

                Datos = datos1.getStringArrayListExtra("Dto");
                CargaDtosGV((ArrayList) Datos);

                String Resp = datos1.getStringExtra("resp");
                Toast.makeText(PrincipalActivity.this, Resp+" (-_-)", Toast.LENGTH_SHORT).show();
            }else if(result.getResultCode()==Activity.RESULT_CANCELED) {
                String Resp = datos1.getStringExtra("resp");
                if(Resp=="")
                    Toast.makeText(PrincipalActivity.this, "No se Regreso Correctamente ", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(PrincipalActivity.this, Resp+" (-_-)", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        txtMatri = findViewById(R.id.txtMatri);
        txtNom = findViewById(R.id.txtPANom);
        txtEdad = findViewById(R.id.txtPAEdad);
        txtSem = findViewById(R.id.txtPASem);
        txtProm = findViewById(R.id.txtPAProm);
        txtEdo = findViewById(R.id.txtPAEdo);

        GVDtos = findViewById(R.id.GVDtos);
        Archivo archivos = new Archivo();
        boolean resp =  archivos.LeerFile(this);
        if(resp){
            Datos = archivos.RegresaInfo();
            CargaDtosGV((ArrayList) Datos);
        }else{
            Toast.makeText(this, "No hay informacion -_-",Toast.LENGTH_LONG);
        }

        GVDtos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                if(posicion>0 && (posicion%6)==0) {
                    int linea = posicion / 6;
                    line=linea;
                    Log.i("informacion","Valor Lista"+ Datos.get(linea-1));
                    cadenas = String.valueOf(Datos.get(linea-1));
                    AcomodaDtos(cadenas);
                    Toast.makeText(PrincipalActivity.this, "posicion" + linea, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CargaDtosGV(ArrayList cadena){
        items = new ArrayList<>();
        for (int i=0;i<cadena.size();i++) {
            Gson gson = new Gson();
            Datos datos = gson.fromJson(String.valueOf(cadena.get(i)),Datos.class);
            items.add(datos);
        }
        int x=6;
        informacion= new String[((items.size())*6)+6];
        informacion[0]="Matricula";
        informacion[1]="Nombre";
        informacion[2]="Edad";
        informacion[3]="Semestre";
        informacion[4]="Promedio";
        informacion[5]="Estado";

        for (int i = 0; i < items.size(); i++) {

            informacion[x]=items.get(i).getMatricula();
            informacion[x+1]=items.get(i).getNombre();
            informacion[x+2]= String.valueOf(items.get(i).getEdad());
            informacion[x+3]=items.get(i).getSemestre();
            informacion[x+4]= String.valueOf(items.get(i).getPromedio());
            informacion[x+5]=items.get(i).getEstado();
            x=x+6;
        }

        arreglo = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,informacion);
        GVDtos.setAdapter(arreglo);
    }

    private void AcomodaDtos(String cadena){
        Gson gson = new Gson();
        Datos datos = gson.fromJson(cadena,Datos.class);
        txtMatri.setText(datos.getMatricula());
        txtNom.setText(datos.getNombre());
        txtEdad.setText(String.valueOf(datos.getEdad()));
        txtSem.setText(datos.getSemestre());
        txtProm.setText(String.valueOf(datos.getPromedio()));
        txtEdo.setText(datos.getEstado());
    }

    public void PantallaAgregar(View jcpm){
        Intent inten = new Intent(this,PantallaAgregarActivity.class);
        double promedio = Double.parseDouble(txtProm.getText().toString());
        String edo = "";
        if(promedio>=7)
            edo="Aprobado";
        else
            edo="No Acreditado";

        inten.putExtra("Matricula",txtMatri.getText().toString());
        inten.putExtra("Nombre",txtNom.getText().toString());
        inten.putExtra("Edad",txtEdad.getText().toString());
        inten.putExtra("Semestre",txtSem.getText().toString());
        inten.putExtra("Promedio",txtProm.getText().toString());
        inten.putExtra("Edo",edo);
        txtEdo.setText(edo);
        Datos datos = new Datos(
                txtMatri.getText().toString(),
                txtNom.getText().toString(),
                Integer.parseInt(txtEdad.getText().toString()),
                txtSem.getText().toString(),
                Double.parseDouble(txtProm.getText().toString()),
                edo);

        Gson gson = new Gson();
        String cadena = gson.toJson(datos);
        Archivo archivos = new Archivo();
        boolean resp =  archivos.LeerFile(this);
        if(resp)
            Datos.add(cadena);
        else {
            Datos = new ArrayList<>();
            Datos.add(cadena);
        }
        archivos.Grabar(this,Datos);

        activityResultLauncher.launch(inten);
    }

    public void PantallaEliminar(View jcpm){
        Intent inten = new Intent(this,PantallaEliminaActivity.class);

        inten.putExtra("Matricula",txtMatri.getText().toString());
        inten.putExtra("Nombre",txtNom.getText().toString());
        inten.putExtra("Edad",txtEdad.getText().toString());
        inten.putExtra("Semestre",txtSem.getText().toString());
        inten.putExtra("Promedio",txtProm.getText().toString());
        inten.putExtra("Edo",txtEdo.getText().toString());
        inten.putExtra("Linea",line);

        activityResultLauncher.launch(inten);
    }

    public void PantallaActualizar(View jcpm){
        Intent inten = new Intent(this,PantallaActualizaActivity.class);

        inten.putExtra("Matricula",txtMatri.getText().toString());
        inten.putExtra("Nombre",txtNom.getText().toString());
        inten.putExtra("Edad",txtEdad.getText().toString());
        inten.putExtra("Semestre",txtSem.getText().toString());
        inten.putExtra("Promedio",txtProm.getText().toString());
        inten.putExtra("Edo",txtEdo.getText().toString());
        inten.putExtra("Linea",line);

        activityResultLauncher.launch(inten);
    }
}