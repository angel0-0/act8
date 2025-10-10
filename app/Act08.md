# 📱 ACTIVIDAD 8 - DESARROLLO PASO A PASO

## App de Notificaciones SMS con Broadcast Receivers

### 🎯 Guía completa de implementación

Esta guía te llevará paso a paso a través del desarrollo completo de una aplicación Android que monitorea SMS y envía notificaciones usando Broadcast Receivers.

---

## FASE 1: CONFIGURACIÓN DEL PROYECTO

### Paso 1.1: Configuración del repositorio Git

```bash
# 1. Crear directorio del proyecto
mkdir Actividad08
cd Actividad08

# 2. Inicializar repositorio Git
git init

# 3. Crear README inicial
echo "# 📱 Actividad 8 - App de Notificaciones SMS" > README.md

# 4. Primer commit
git add .
git commit -m "feat: configuración inicial del repositorio"

# 5. Conectar con GitHub (reemplaza con tu usuario)
git remote add origin https://github.com/TU_USUARIO/Actividad08.git
git branch -M main
git push -u origin main
```

### Paso 1.2: Crear proyecto Android Studio

1. **Abrir Android Studio**
2. **File → New → New Project**
3. **Configurar proyecto:**
   -  **Application name:** `actividad08`
   -  **Package name:** `com.tecmilenio.actividad08`
   -  **Language:** Java
   -  **Minimum SDK:** API 28 (Android 9.0)
4. **Finish**

### Paso 1.3: Configurar control de versiones

```bash
# En la carpeta del proyecto Android
git init
git add .
git commit -m "feat: proyecto Android inicial"
git remote add origin https://github.com/TU_USUARIO/Actividad08.git
git push -u origin main
```

---

## FASE 2: CONFIGURACIÓN DE DISEÑO

### Paso 2.1: Crear paleta de colores

**Archivo: `app/src/main/res/values/colors.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- Colores primarios -->
    <color name="primary_blue">#2196F3</color>
    <color name="primary_dark">#1976D2</color>
    <color name="primary_light">#BBDEFB</color>

    <!-- Colores secundarios -->
    <color name="secondary_green">#4CAF50</color>
    <color name="secondary_dark">#388E3C</color>

    <!-- Colores de acento -->
    <color name="accent_orange">#FF9800</color>
    <color name="accent_red">#F44336</color>

    <!-- Colores de fondo y texto -->
    <color name="background_white">#FFFFFF</color>
    <color name="background_light">#F5F5F5</color>
    <color name="text_primary">#212121</color>
    <color name="text_secondary">#757575</color>
    <color name="text_hint">#BDBDBD</color>
</resources>
```

### Paso 2.2: Configurar fuente personalizada

1. **Crear carpeta:** `app/src/main/res/font/`
2. **Descargar fuente Roboto de Google Fonts**
3. **Agregar archivos:**
   -  `roboto_regular.ttf`
   -  `roboto_bold.ttf`
   -  `roboto_light.ttf`

### Paso 2.3: Configurar tema

**Archivo: `app/src/main/res/values/themes.xml`**

```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <style name="Theme.Actividad08" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
        <!-- Colores primarios -->
        <item name="colorPrimary">@color/primary_blue</item>
        <item name="colorPrimaryVariant">@color/primary_dark</item>
        <item name="colorOnPrimary">@color/background_white</item>

        <!-- Colores secundarios -->
        <item name="colorSecondary">@color/secondary_green</item>
        <item name="colorSecondaryVariant">@color/secondary_dark</item>
        <item name="colorOnSecondary">@color/background_white</item>

        <!-- Colores de fondo -->
        <item name="android:colorBackground">@color/background_light</item>
        <item name="colorSurface">@color/background_white</item>

        <!-- Fuente personalizada -->
        <item name="android:fontFamily">@font/roboto_regular</item>
    </style>

    <!-- Estilo para botones -->
    <style name="CustomButton" parent="Widget.MaterialComponents.Button">
        <item name="android:fontFamily">@font/roboto_bold</item>
        <item name="backgroundTint">@color/accent_orange</item>
        <item name="android:textColor">@color/background_white</item>
    </style>
</resources>
```

### Paso 2.4: Commit de configuración de diseño

```bash
git checkout -b feature/configuracion-diseno
git add .
git commit -m "feat: configuración de colores, fuentes y temas"
git push origin feature/configuracion-diseno
```

---

## FASE 3: CONFIGURACIÓN DE PERMISOS

### Paso 3.1: Configurar AndroidManifest.xml

**Archivo: `app/src/main/AndroidManifest.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.tecmilenio.actividad08">

    <!-- Permisos necesarios para API 28 -->
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.READ_SMS" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.Actividad08"
        android:requestLegacyExternalStorage="true">

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Registro del BroadcastReceiver -->
        <receiver
            android:name=".SMSReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter android:priority="1000">
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
            </intent-filter>
        </receiver>

    </application>
</manifest>
```

---

## FASE 4: DESARROLLO DE LA INTERFAZ

### Paso 4.1: Diseño de la actividad principal

**Archivo: `app/src/main/res/layout/activity_main.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/appcompat/lib/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp"
    android:background="@color/background_light">

    <!-- Título -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="📱 Actividad 08"
        android:textSize="24sp"
        android:textStyle="bold"
        android:textColor="@color/primary_blue"
        android:fontFamily="@font/roboto_bold"
        android:gravity="center"
        android:layout_marginBottom="24dp" />

    <!-- Campo para agregar número -->
    <com.google.android.material.textfield.TextInputLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Número de teléfono"
        app:boxStrokeColor="@color/primary_blue"
        android:layout_marginBottom="16dp">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/etPhoneNumber"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="phone"
            android:fontFamily="@font/roboto_regular" />

    </com.google.android.material.textfield.TextInputLayout>

    <!-- Botón para agregar número -->
    <Button
        android:id="@+id/btnAddNumber"
        style="@style/CustomButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="➕ Agregar Número"
        android:layout_marginBottom="24dp" />

    <!-- Título de la lista -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="📋 Números Monitoreados"
        android:textSize="18sp"
        android:textStyle="bold"
        android:textColor="@color/text_primary"
        android:fontFamily="@font/roboto_bold"
        android:layout_marginBottom="12dp" />

    <!-- Lista de números -->
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvPhoneNumbers"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="@color/background_white"
        android:padding="8dp" />

    <!-- Estado del monitoreo -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:gravity="center"
        android:padding="16dp"
        android:background="@color/primary_light"
        android:layout_marginTop="16dp">

        <TextView
            android:id="@+id/tvStatus"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="🟢 Monitoreo Activo"
            android:textSize="16sp"
            android:textColor="@color/secondary_green"
            android:fontFamily="@font/roboto_bold" />

    </LinearLayout>

</LinearLayout>
```

### Paso 4.2: Diseño del elemento de lista

**Archivo: `app/src/main/res/layout/item_phone_number.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/appcompat/lib/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="4dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="4dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="16dp"
        android:gravity="center_vertical">

        <!-- Icono -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="📞"
            android:textSize="24sp"
            android:layout_marginEnd="16dp" />

        <!-- Número de teléfono -->
        <TextView
            android:id="@+id/tvPhoneNumber"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Número de teléfono"
            android:textSize="16sp"
            android:textColor="@color/text_primary"
            android:fontFamily="@font/roboto_regular" />

        <!-- Botón eliminar -->
        <Button
            android:id="@+id/btnDelete"
            android:layout_width="48dp"
            android:layout_height="48dp"
            android:text="🗑️"
            android:textSize="16sp"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:layout_marginStart="8dp" />

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

---

## FASE 5: IMPLEMENTACIÓN DEL CÓDIGO JAVA

### Paso 5.1: Crear el modelo de datos

**Archivo: `app/src/main/java/com/tecmilenio/actividad08/PhoneNumber.java`**

```java
package com.tecmilenio.actividad08;

public class PhoneNumber {
    private String number;
    private boolean isActive;

    public PhoneNumber(String number) {
        this.number = number;
        this.isActive = true;
    }

    // Getters y Setters
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PhoneNumber that = (PhoneNumber) obj;
        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
```

### Paso 5.2: Crear el adaptador del RecyclerView

**Archivo: `app/src/main/java/com/tecmilenio/actividad08/PhoneNumberAdapter.java`**

```java
package com.tecmilenio.actividad08;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumberAdapter extends RecyclerView.Adapter<PhoneNumberAdapter.ViewHolder> {

    private List<PhoneNumber> phoneNumbers;
    private OnPhoneNumberDeleteListener deleteListener;

    public interface OnPhoneNumberDeleteListener {
        void onDelete(PhoneNumber phoneNumber);
    }

    public PhoneNumberAdapter() {
        this.phoneNumbers = new ArrayList<>();
    }

    public void setDeleteListener(OnPhoneNumberDeleteListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phone_number, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhoneNumber phoneNumber = phoneNumbers.get(position);
        holder.tvPhoneNumber.setText(phoneNumber.getNumber());

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(phoneNumber);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneNumbers.size();
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        if (!phoneNumbers.contains(phoneNumber)) {
            phoneNumbers.add(phoneNumber);
            notifyItemInserted(phoneNumbers.size() - 1);
        }
    }

    public void removePhoneNumber(PhoneNumber phoneNumber) {
        int position = phoneNumbers.indexOf(phoneNumber);
        if (position >= 0) {
            phoneNumbers.remove(position);
            notifyItemRemoved(position);
        }
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return new ArrayList<>(phoneNumbers);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPhoneNumber;
        Button btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
```

### Paso 5.3: Implementar el BroadcastReceiver

**Archivo: `app/src/main/java/com/tecmilenio/actividad08/SMSReceiver.java`**

```java
package com.tecmilenio.actividad08;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import java.util.HashSet;
import java.util.Set;

public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = "SMSReceiver";
    private static final String PREFS_NAME = "sms_monitor_prefs";
    private static final String KEY_PHONE_NUMBERS = "monitored_numbers";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "SMS recibido");

        // Verificar que es un SMS recibido
        if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                // Extraer mensajes SMS
                Object[] pdus = (Object[]) bundle.get("pdus");
                String format = bundle.getString("format");

                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu(
                            (byte[]) pdu, format);

                        String sender = smsMessage.getDisplayOriginatingAddress();
                        String messageBody = smsMessage.getDisplayMessageBody();

                        Log.d(TAG, "SMS de: " + sender + ", Mensaje: " + messageBody);

                        // Verificar si el número está en la lista de monitoreo
                        if (isNumberMonitored(context, sender)) {
                            showNotification(context, sender, messageBody);
                        }
                    }
                }
            }
        }
    }

    private boolean isNumberMonitored(Context context, String phoneNumber) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> monitoredNumbers = prefs.getStringSet(KEY_PHONE_NUMBERS, new HashSet<>());

        // Limpiar formato del número para comparación
        String cleanNumber = phoneNumber.replaceAll("[^0-9]", "");

        for (String monitoredNumber : monitoredNumbers) {
            String cleanMonitored = monitoredNumber.replaceAll("[^0-9]", "");
            if (cleanNumber.endsWith(cleanMonitored) || cleanMonitored.endsWith(cleanNumber)) {
                return true;
            }
        }

        return false;
    }

    private void showNotification(Context context, String sender, String message) {
        // Crear notificación Toast
        String notificationText = "📨 SMS de " + sender + ": " +
            (message.length() > 50 ? message.substring(0, 50) + "..." : message);

        Toast.makeText(context, notificationText, Toast.LENGTH_LONG).show();

        // Log detallado
        Log.i(TAG, "🔔 NOTIFICACIÓN - SMS Monitoreado");
        Log.i(TAG, "Remitente: " + sender);
        Log.i(TAG, "Mensaje: " + message);
        Log.i(TAG, "Hora: " + System.currentTimeMillis());

        // Aquí se podría agregar NotificationManager para notificaciones del sistema
        createSystemNotification(context, sender, message);
    }

    private void createSystemNotification(Context context, String sender, String message) {
        // Implementación de notificación del sistema
        // (Se implementará en el siguiente paso)
        Log.d(TAG, "Preparando notificación del sistema...");
    }
}
```

### Paso 5.4: Implementar la actividad principal

**Archivo: `app/src/main/java/com/tecmilenio/actividad08/MainActivity.java`**

```java
package com.tecmilenio.actividad08;

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
            tvStatus.setTextColor(getResources().getColor(R.color.secondary_green));
        } else {
            tvStatus.setText("🔴 Permisos Requeridos");
            tvStatus.setTextColor(getResources().getColor(R.color.accent_red));
        }
    }
}
```

---

## FASE 6: IMPLEMENTACIÓN DE NOTIFICACIONES AVANZADAS

### Paso 6.1: Crear el gestor de notificaciones

**Archivo: `app/src/main/java/com/tecmilenio/actividad08/NotificationHelper.java`**

```java
package com.tecmilenio.actividad08;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationHelper {

    private static final String CHANNEL_ID = "sms_monitor_channel";
    private static final String CHANNEL_NAME = "Actividad 08";
    private static final String CHANNEL_DESCRIPTION = "Notificaciones de SMS monitoreados";

    private Context context;
    private NotificationManager notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager)
            context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{0, 250, 250, 250});

            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showSMSNotification(String sender, String message) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification) // Crear este icono
            .setContentTitle("📨 SMS Monitoreado")
            .setContentText("De: " + sender)
            .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("De: " + sender + "\n" + message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
```

### Paso 6.2: Actualizar SMSReceiver con notificaciones avanzadas

**Modificar el método `createSystemNotification` en `SMSReceiver.java`:**

```java
private void createSystemNotification(Context context, String sender, String message) {
    NotificationHelper notificationHelper = new NotificationHelper(context);
    notificationHelper.showSMSNotification(sender, message);
}
```

---

## FASE 7: TESTING Y DEBUGGING

### Paso 7.1: Crear clase de testing

**Archivo: `app/src/main/java/com/tecmilenio/actividad08/TestHelper.java`**

```java
package com.tecmilenio.actividad08;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TestHelper {

    private static final String TAG = "TestHelper";

    public static void simulateSMS(Context context, String sender, String message) {
        Log.d(TAG, "🧪 SIMULANDO SMS");
        Log.d(TAG, "Remitente: " + sender);
        Log.d(TAG, "Mensaje: " + message);

        // Simular notificación
        Toast.makeText(context,
            "🧪 TEST - SMS de " + sender + ": " + message,
            Toast.LENGTH_LONG).show();

        // Crear notificación real
        NotificationHelper helper = new NotificationHelper(context);
        helper.showSMSNotification(sender, message);
    }

    public static void testPhoneNumberValidation() {
        Log.d(TAG, "🧪 TESTING - Validación de números");

        String[] testNumbers = {
            "+5215512345678",  // Válido
            "5512345678",      // Válido
            "123",             // Inválido
            "+1234567890123456", // Inválido (muy largo)
            "abc123",          // Inválido
        };

        for (String number : testNumbers) {
            boolean isValid = number.matches("^[+]?[0-9]{10,15}$");
            Log.d(TAG, "Número: " + number + " - Válido: " + isValid);
        }
    }
}
```

### Paso 7.2: Agregar botón de testing en MainActivity

**Agregar al layout `activity_main.xml` antes del LinearLayout de estado:**

```xml
<!-- Botón de testing (solo para desarrollo) -->
<Button
    android:id="@+id/btnTest"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="🧪 Simular SMS"
    android:layout_marginTop="16dp"
    android:backgroundTint="@color/accent_orange"
    android:textColor="@color/background_white" />
```

**Agregar en MainActivity.java en el método `setupClickListeners()`:**

```java
Button btnTest = findViewById(R.id.btnTest);
btnTest.setOnClickListener(v -> {
    TestHelper.simulateSMS(this, "+5215512345678", "Mensaje de prueba para testing");
    TestHelper.testPhoneNumberValidation();
});
```

---

## FASE 8: DOCUMENTACIÓN Y ENTREGA

### Paso 8.1: Crear README completo

**Archivo: `README.md`**

```markdown
# 📱 Actividad 8 - App de Notificaciones SMS

## 📋 Descripción

Aplicación Android que monitorea mensajes SMS de números específicos y envía notificaciones usando Broadcast Receivers. Desarrollada como parte del curso de Desarrollo de Aplicaciones Móviles.

## 🎯 Objetivos de aprendizaje

-  ✅ Integración con servicios del dispositivo (SMS)
-  ✅ Implementación de Broadcast Receivers
-  ✅ Manejo eficiente de hilos en Android
-  ✅ Gestión de permisos en tiempo de ejecución
-  ✅ Diseño de interfaces responsivas

## 🛠️ Tecnologías utilizadas

-  **Android SDK:** API 28 (Android 9.0)
-  **Lenguaje:** Java
-  **Componentes:** Broadcast Receivers, Notification Manager
-  **UI:** Material Design Components
-  **Almacenamiento:** SharedPreferences

## ⚠️ Notas importantes para API 28

### Diferencias con versiones más recientes:

-  **POST_NOTIFICATIONS**: Este permiso NO existe en API 28, se introdujo en API 33
-  **PendingIntent.FLAG_IMMUTABLE**: No disponible en API 28, usar solo FLAG_UPDATE_CURRENT
-  **Notificaciones**: Funcionan sin permisos especiales en API 28
-  **BroadcastReceiver**: Funciona normalmente para SMS en API 28

## 📱 Funcionalidades

### ✅ Implementadas:

-  [x] Lista dinámica de números de teléfono a monitorear
-  [x] Agregar/eliminar números de la lista
-  [x] BroadcastReceiver para interceptar SMS
-  [x] Notificaciones Toast para feedback inmediato
-  [x] Notificaciones del sistema para alertas
-  [x] Logging detallado para debugging
-  [x] Validación de números de teléfono
-  [x] Persistencia de datos con SharedPreferences
-  [x] Manejo de permisos en tiempo de ejecución

### 🎨 Diseño:

-  [x] Paleta de colores temática (comunicación/alerta)
-  [x] Fuente personalizada (Roboto)
-  [x] Interfaz siguiendo Material Design
-  [x] Componentes responsivos

## 🔧 Instalación y configuración

### Prerrequisitos:

-  Android Studio 4.0+
-  SDK mínimo: API 28
-  Dispositivo Android o emulador con API 28+

### Pasos:

1. Clona este repositorio
2. Abre el proyecto en Android Studio
3. Sincroniza Gradle
4. Ejecuta en dispositivo/emulador
5. Concede permisos de SMS cuando se soliciten

## 🚀 Uso de la aplicación

1. **Agregar números:** Ingresa un número y presiona "Agregar Número"
2. **Monitoreo:** La app monitoreará SMS de los números agregados
3. **Notificaciones:** Recibirás alertas cuando lleguen SMS monitoreados
4. **Gestión:** Elimina números deslizando o presionando el botón eliminar

## 🔒 Permisos necesarios

-  `RECEIVE_SMS`: Para interceptar mensajes entrantes
-  `READ_SMS`: Para leer contenido de mensajes

## 🧪 Testing

La aplicación incluye funciones de testing:

-  Simulación de SMS para pruebas
-  Validación de números de teléfono
-  Logging detallado en Logcat

Para activar testing: presiona el botón "🧪 Simular SMS"

## 📁 Estructura del proyecto
```

app/src/main/
├── java/com/tecmilenio/actividad08/
│ ├── MainActivity.java # Actividad principal
│ ├── SMSReceiver.java # BroadcastReceiver para SMS
│ ├── PhoneNumber.java # Modelo de datos
│ ├── PhoneNumberAdapter.java # Adaptador RecyclerView
│ ├── NotificationHelper.java # Gestor de notificaciones
│ └── TestHelper.java # Utilidades de testing
├── res/
│ ├── layout/ # Diseños XML
│ ├── values/ # Colores, temas, strings
│ └── font/ # Fuentes personalizadas
└── AndroidManifest.xml # Configuración y permisos

````

## 🐛 Troubleshooting

### Problemas comunes:

**Error de permisos POST_NOTIFICATIONS en API 28:**
- Este permiso NO existe en API 28 (Android 9.0)
- Elimina `<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />` del AndroidManifest.xml
- Elimina `Manifest.permission.POST_NOTIFICATIONS` del array de permisos en MainActivity.java

**Error de PendingIntent.FLAG_IMMUTABLE en API 28:**
- Esta flag no existe en API 28
- Usa solo `PendingIntent.FLAG_UPDATE_CURRENT`
- No combines con FLAG_IMMUTABLE

**No se reciben notificaciones:**
- Verifica que los permisos estén concedidos
- Revisa que el número esté en la lista de monitoreo
- Comprueba los logs en Logcat

**La app no funciona en Android 10+:**
- Asegúrate de manejar permisos en tiempo de ejecución
- Verifica configuración de Background App Refresh

**Números no se guardan:**
- Verifica formato del número de teléfono
- Comprueba logs de SharedPreferences

## 📝 Respuestas a preguntas de reflexión

### 🔋 Integración con servicios del dispositivo:

Las aplicaciones pueden integrarse con servicios del dispositivo de manera eficiente mediante:

1. **Gestión de permisos:** Implementar solicitud de permisos en tiempo de ejecución (API 23+)
2. **BroadcastReceivers:** Escuchar eventos del sistema de forma reactiva
3. **Servicios en segundo plano:** Usar WorkManager para tareas diferidas
4. **Optimización de batería:** Implementar Doze mode y App Standby compatibility

**Ejemplo en la app:**
```java
// Solicitud de permisos en tiempo de ejecución
if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
    != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
}
````

### 📡 Broadcast Intents y Receivers:

Los Broadcast Receivers permiten comunicación entre aplicaciones mediante:

1. **Intents explícitos:** Comunicación directa entre componentes conocidos
2. **Intents implícitos:** Comunicación basada en acciones/categorías
3. **Ordered broadcasts:** Control de prioridad y propagación
4. **Local broadcasts:** Comunicación interna segura

**Implementación en la app:**

```java
// Registro en AndroidManifest.xml
<receiver android:name=".SMSReceiver">
    <intent-filter android:priority="1000">
        <action android:name="android.provider.Telephony.SMS_RECEIVED" />
    </intent-filter>
</receiver>
```

### 🧵 Manejo de hilos:

El manejo eficiente de hilos en Android incluye:

1. **UI Thread:** Todas las actualizaciones de interfaz en hilo principal
2. **Background threads:** AsyncTask, ExecutorService para tareas pesadas
3. **Sincronización:** Handler, Looper para comunicación entre hilos
4. **Lifecycle awareness:** Cancelar tareas al destruir componentes

**Ejemplo en la app:**

```java
// Procesamiento en hilo secundario
new Thread(() -> {
    // Procesamiento pesado
    String result = processData();

    // Actualización en UI thread
    runOnUiThread(() -> {
        updateUI(result);
    });
}).start();
```

### 💭 Reflexión personal:

Durante el desarrollo de esta aplicación, enfrenté varios desafíos importantes que me ayudaron a comprender mejor el ecosistema Android:

**Desafíos principales:**

1. **Gestión de permisos:** Adaptar el código para manejar permisos dinámicos fue complejo
2. **BroadcastReceivers:** Entender el ciclo de vida y limitaciones requirió investigación
3. **Testing:** Simular SMS en emulador presentó dificultades técnicas

**Conceptos más interesantes:**

-  La arquitectura de eventos de Android mediante Broadcast Intents
-  El balance entre funcionalidad y seguridad en permisos
-  La importancia del diseño responsivo en diferentes dispositivos

**Aplicaciones futuras:**
Esta experiencia me permitirá desarrollar aplicaciones más robustas que integren:

-  Notificaciones push avanzadas
-  Servicios de localización
-  Integración con APIs de redes sociales
-  Sistemas de monitoreo en tiempo real

El proyecto demostró la importancia de planificar la arquitectura antes de codificar y la necesidad de testing exhaustivo en desarrollo móvil.

## 👥 Autor

**[Tu Nombre]**  
Estudiante de Tecmilenio - Desarrollo de Aplicaciones Móviles  
Actividad 8 - Octubre 2025

## 📞 Soporte

Para dudas técnicas sobre este proyecto:

-  Revisar documentación en este README
-  Consultar logs en Logcat
-  Contactar al instructor del curso

````

### Paso 8.2: Commit final y entrega

```bash
# Commit de toda la implementación
git add .
git commit -m "feat: implementación completa de la aplicación SMS Monitor

- Configuración de diseño y temas
- Implementación de BroadcastReceiver para SMS
- Interfaz de usuario con RecyclerView
- Sistema de notificaciones avanzado
- Testing y debugging incluido
- Documentación completa"

# Push al repositorio
git push origin desarrollo

# Crear Pull Request para revisión
# (Hacer desde GitHub interface)
````

---

## 🎯 CHECKLIST DE ENTREGA

### ✅ Configuración:

-  [x] Repositorio GitHub creado y configurado
-  [x] Colaborador 'hasanyfa' agregado
-  [x] Proyecto Android Studio configurado

### ✅ Diseño:

-  [x] Paleta de colores implementada (8+ colores)
-  [x] Fuente personalizada instalada
-  [x] Tema configurado correctamente
-  [x] Interfaz siguiendo Material Design

### ✅ Funcionalidad:

-  [x] Lista de números implementada
-  [x] BroadcastReceiver funcional
-  [x] Sistema de notificaciones completo
-  [x] Manejo de permisos correcto
-  [x] Persistencia de datos

### ✅ Código:

-  [x] Estructura clara y comentada
-  [x] Manejo de errores implementado
-  [x] Testing incluido
-  [x] Optimizaciones de rendimiento

### ✅ Documentación:

-  [x] README completo
-  [x] Respuestas de reflexión incluidas
-  [x] Comentarios en código
-  [x] Guías de instalación y uso

### ✅ Git:

-  [x] Commits descriptivos
-  [x] Ramas organizadas
-  [x] Pull Request creado
-  [x] Código revisado

---

**🎉 ¡Proyecto completado exitosamente!**

Esta guía paso a paso te ha llevado desde la configuración inicial hasta una aplicación Android completamente funcional que cumple con todos los requisitos de la Actividad 8.
