# 📱 Actividad 8 - App de Notificaciones SMS

## ¿Cómo se pueden desarrollar aplicaciones que se integren con los servicios del dispositivo, como la batería, los mensajes o las notificaciones, de forma eficiente y segura, considerando diferentes tipos de servicios, permisos y la experiencia del usuario?  
Android proporciona APIs específicas que permiten acceder a funciones del sistema sin comprometer la privacidad del usuario.  
Claves para hacerlo bien:  

-> Uso de permisos adecuados:  
Cada servicio requiere permisos específicos (por ejemplo, ACCESS_FINE_LOCATION, READ_SMS, POST_NOTIFICATIONS).
Solo deben solicitarse cuando realmente sean necesarios y, de preferencia, en tiempo de ejecución, para no asustar ni confundir al usuario.  

-> Gestión eficiente de recursos:  
Por ejemplo, si tu app necesita monitorear el nivel de batería, usa BatteryManager y escucha solo los cambios relevantes. Evita consultas constantes o procesos en segundo plano innecesarios, ya que esto drena la batería.  

-> Seguridad:  
Nunca se deben exponer datos personales o del sistema a través de logs, ni compartir información sensible con otras apps sin validación previa.  

  
## ¿Cómo se pueden utilizar Broadcast Intents y Broadcast Receivers para comunicar diferentes aplicaciones entre sí de forma segura y eficiente, considerando diferentes tipos de acciones, datos y la experiencia del usuario?  
Seguridad:  

-> Declara receptores en el AndroidManifest solo cuando sea necesario.  

-> Para transmisiones sensibles, usa intents explícitos (que indican exactamente el destinatario) en lugar de implícitos, evitando que otras apps intercepten la información.  

-> Usa LocalBroadcastManager cuando la comunicación sea interna, dentro de tu propia app.  

Eficiencia:  
-> No registres receptores innecesarios de forma permanente; usa registerReceiver() y unregisterReceiver() solo durante la actividad o servicio donde se necesiten.  

Experiencia del usuario:  
-> Los eventos deben mejorar la fluidez o la automatización, no interrumpir al usuario constantemente.  

## ¿Cómo se pueden utilizar hilos en Android para mejorar la eficiencia y la capacidad de respuesta de una aplicación, considerando diferentes tipos de tareas, la sincronización de datos y la experiencia del usuario?  
-> Los hilos permiten ejecutar tareas en segundo plano para evitar que la interfaz principal (UI Thread) se bloquee. Esto mejora notablemente la eficiencia y capacidad de respuesta de la aplicación.  
Tareas pesadas fuera del hilo principal:  
-> Operaciones como descargas, lectura de base de datos o cálculos intensivos deben ir en hilos secundarios (Thread, HandlerThread, ExecutorService o Coroutine).  

Sincronización de datos:  
-> Cuando un hilo modifica datos usados por otro, se debe asegurar la consistencia usando mecanismos como synchronized, Locks o LiveData para evitar errores o bloqueos.  

  
## Reflexión personal del tema (mínimo 50 palabras):  
el desarrollo eficiente y seguro en Android depende del uso responsable de los permisos, la gestión cuidadosa de los Broadcast Receivers y la correcta implementación de hilos o tareas asíncronas. Estas prácticas permiten optimizar el rendimiento del dispositivo, proteger la información del usuario y ofrecer una experiencia de uso fluida, estable y confiable.
