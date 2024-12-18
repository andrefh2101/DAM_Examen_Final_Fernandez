package com.example.dam_examen_final_fernandez;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables
    private ImageView iconClaveInfo;
    private Switch switchRecordar;
    private TextView tooltipSwitch;
    private EditText editTextNumeroTarjeta;
    private Button buttonGenerarClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialización de vistas
        iconClaveInfo = findViewById(R.id.iconClaveInfo);
        tooltipSwitch = findViewById(R.id.textViewRecordar);
        switchRecordar = findViewById(R.id.switchRecordar);
        editTextNumeroTarjeta = findViewById(R.id.editTextNumeroTarjeta);
        // Botón sin funcionalidad

        // Mostrar diálogo al tocar el ícono de información
        iconClaveInfo.setOnClickListener(view -> mostrarDialogoClave());

        // Mostrar el texto encima del Switch cuando está activado
        switchRecordar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tooltipSwitch.setVisibility(View.VISIBLE);
            } else {
                tooltipSwitch.setVisibility(View.GONE);
            }
        });

        // TextWatcher para formatear el número de tarjeta automáticamente
        editTextNumeroTarjeta.addTextChangedListener(new TextWatcher() {
            private boolean isDeleting;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isDeleting = count > after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().replaceAll("\\s", ""); // Eliminar espacios
                if (isDeleting || input.length() > 16) return;

                StringBuilder formatted = new StringBuilder();
                for (int i = 0; i < input.length(); i++) {
                    formatted.append(input.charAt(i));
                    if ((i + 1) % 4 == 0 && i + 1 < input.length()) {
                        formatted.append(" ");
                    }
                }

                editTextNumeroTarjeta.removeTextChangedListener(this);
                editTextNumeroTarjeta.setText(formatted.toString());
                editTextNumeroTarjeta.setSelection(formatted.length());
                editTextNumeroTarjeta.addTextChangedListener(this);
            }
        });
    }

    // Método para mostrar el diálogo de clave de Internet
    private void mostrarDialogoClave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Banco de la Nación")
                .setMessage("Esta clave de 6 dígitos te permite acceder a la Banca por Internet (Multired Virtual) y también a la Banca Móvil (Multired App) del Banco de la Nación.")
                .setPositiveButton("Aceptar", null);
        builder.create().show();
    }
}
