package com.angel.act8;

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
