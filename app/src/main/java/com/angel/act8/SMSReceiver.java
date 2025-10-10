package com.angel.act8;

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
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.showSMSNotification(sender, message);
    }
}
