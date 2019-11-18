package com.oscarfoav.sqlserverconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private TextView txtIdUsuario ;
    private TextView txtUsuario;
    private TextView txtDireccion;
    private TextView txtTelefono;

    private Connection cnn;
    private Statement stm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtIdUsuario = findViewById(R.id.etIdUsuario);
        txtUsuario = findViewById(R.id.tvUsuario);
        txtDireccion = findViewById(R.id.tvDireccion);
        txtTelefono = findViewById(R.id.tvTelefono);

        Button consultar = findViewById(R.id.btConsultar);

        consultar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                consultaPersona();
            }
        });

    }

    public Connection conexionBD(){
        cnn = null;
        try{
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.136;databasename=DB_Android;user=DB_Android;password=Ganda123+");

        } catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            Log.e(TAG, "ERROR: La conexión no se ha creado.");
        }
        return cnn;
    }

    public void consultaPersona(){
        try{
            stm = conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT idUsuario, Usuario, Direccion, Telefono FROM dbo.tbl_Usuarios where idUsuario = '" + txtIdUsuario.getText().toString() + "'");

            if (rs.next()){
                txtUsuario.setText(rs.getString(2));
                txtDireccion.setText(rs.getString(3));
                txtTelefono.setText(rs.getString(4));
            }
        } catch ( Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            Log.e(TAG, "ERROR: No se accede a la BD después de conectar correctamente.");
        }
    }
}
