package com.angel.act8;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity
    implements PhoneNumberAdapter.OnPhoneNumberDeleteListener {

    private static final int SMS_PERMISSION_REQUEST = 100;
    private static final String PREFS_NAME = "sms_monitor_prefs";
    private static final String KEY_PHONE_NUMBERS = "monitored_numbers";

    private EditText etPhoneNumber;
    private Button btnAddNumber;
    private RecyclerView rvPhoneNumbers;
    private TextView tvStatus;

    private PhoneNumberAdapter adapter;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupClickListeners();
        loadSavedNumbers();
        checkPermissions();
    }

    private void initializeViews() {
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnAddNumber = findViewById(R.id.btnAddNumber);
        rvPhoneNumbers = findViewById(R.id.rvPhoneNumbers);
        tvStatus = findViewById(R.id.tvStatus);

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    private void setupRecyclerView() {
        adapter = new PhoneNumberAdapter();
        adapter.setDeleteListener(this);
        rvPhoneNumbers.setLayoutManager(new LinearLayoutManager(this));
        rvPhoneNumbers.setAdapter(adapter);
    }

    private void setupClickListeners() {
        btnAddNumber.setOnClickListener(v -> addPhoneNumber());

        Button btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(v -> {
            TestHelper.simulateSMS(this, "+5215512345678", "Mensaje de prueba para testing");
            TestHelper.testPhoneNumberValidation();
        });
    }

    private void addPhoneNumber() {
        String phoneNumber = etPhoneNumber.getText().toString().trim();

        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "❌ Ingresa un número de teléfono", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            Toast.makeText(this, "❌ Formato de número inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        PhoneNumber phone = new PhoneNumber(phoneNumber);
        adapter.addPhoneNumber(phone);
        savePhoneNumbers();

        etPhoneNumber.setText("");
        Toast.makeText(this, "✅ Número agregado: " + phoneNumber, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Validación básica de número de teléfono
        return phoneNumber.matches("^[+]?[0-9]{10,15}$");
    }

    @Override
    public void onDelete(PhoneNumber phoneNumber) {
        adapter.removePhoneNumber(phoneNumber);
        savePhoneNumbers();
        Toast.makeText(this, "🗑️ Número eliminado: " + phoneNumber.getNumber(),
            Toast.LENGTH_SHORT).show();
    }

    private void savePhoneNumbers() {
        Set<String> numberSet = new HashSet<>();
        for (PhoneNumber phone : adapter.getPhoneNumbers()) {
            numberSet.add(phone.getNumber());
        }

        preferences.edit()
            .putStringSet(KEY_PHONE_NUMBERS, numberSet)
            .apply();
    }

    private void loadSavedNumbers() {
        Set<String> savedNumbers = preferences.getStringSet(KEY_PHONE_NUMBERS, new HashSet<>());
        for (String number : savedNumbers) {
            adapter.addPhoneNumber(new PhoneNumber(number));
        }
    }

    private void checkPermissions() {
        String[] permissions = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
        };

        boolean allGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }

        if (!allGranted) {
            ActivityCompat.requestPermissions(this, permissions, SMS_PERMISSION_REQUEST);
        } else {
            updateStatus(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQUEST) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            updateStatus(allGranted);

            if (!allGranted) {
                Toast.makeText(this,
                    "⚠️ Permisos necesarios para el funcionamiento de la app",
                    Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateStatus(boolean isActive) {
        if (isActive) {
            tvStatus.setText("🟢 Monitoreo Activo");
        } else {
            tvStatus.setText("🔴 Permisos Requeridos");
        }
    }
}
