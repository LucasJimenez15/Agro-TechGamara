# 📘 Especificación de Requerimientos 

## 🧾 Datos del Alumno

| **Dato** | **Información** |
|-----------|-----------------|
| **Apellido y Nombre** | Jimenez Lucas Francisco |
| **DNI** | 45704748 |
| **Legajo** | 216/22 |
| **Teléfono** | 3855150575 |
| **Email** | lucasjimnez2004@gmail.com |

---

## 🌱 Requerimientos para el Administrador (Productor)

Será quien gestione toda la información de los lotes, cultivos, insumos, maquinarias y reportes del sistema.

| Nº | Nombre del requerimiento | Entrada | Proceso | Salida |
|----|----------------------------|----------|----------|---------|
| 1 | Registro de usuario | Nombre, email, contraseña | El sistema permitirá crear una cuenta nueva para el productor o agricultor. | El sistema muestra un mensaje de “Registro exitoso”. |
| 2 | Verificación de cuenta | Email | El sistema deberá enviar un mensaje con código al email ingresado. | El usuario obtendrá el código de verificación para poder registrarse. |
| 2 | Inicio de sesión | Email, contraseña | El sistema verifica credenciales en la base de datos. | Acceso al panel principal (Inicio). |
| 3 | Búsqueda de hectáreas en el mapa | Nombre de la zona o latitud y longitud | El sistema buscará la dirección ingresada en el mapa y posicionará la vista en esa zona. | El sistema mostrará en el mapa la zona buscada. |
| 3 | Cierre de sesión | - | El usuario pulsa el botón “Cerrar sesión” en la sección de perfil. | El sistema redirige al inicio de sesión. |
| 4 | Registro de lotes de campo | Nombre del lote, ubicación, cantidad de hectáreas | El sistema permitirá agregar nuevos lotes en la base de datos. | El sistema muestra “Lote registrado con éxito”. |
| 5 | Eliminación de un lote | Botón | El usuario selecciona un lote existente y pulsa el botón. | El sistema actualiza la base de datos y muestra “Lote actualizado/eliminado correctamente”. |
| 6 | Registro de campañas | Tipo de cultivo, fecha de siembra, riego y cosecha, descripción, fotos, agroquímicos | El sistema guarda la información de cada cultivo asociado a su lote y campaña. | El sistema muestra “Cultivo registrado con éxito”. |
| 7 | Consulta de detalles de campaña | Año o campaña | El sistema busca datos anteriores en la base de datos. | El sistema muestra la información del lote seleccionada para esa campaña. |
| 8 | Consulta de rendimiento y gastos del cultivo | Apartado de ver rendimientos | El sistema buscará para esa campaña y en ese lote determinado información sobre su rendimiento y este podrá ser modificado a gusto por el agricultor. | El sistema muestra información actualizada de los gastos y rendimiento por hectárea. |
| 11 | Reporte o carga de incidencias | Tipo de incidencia (plagas, riego, suelo, etc.), descripción, foto (opcional) | El sistema guarda los reportes de incidencias agrícolas en la sección de detalles de la campaña. | El sistema muestra “Incidencia registrada”. |
| 12 | Consulta de incidencia cargada | Botón de ver incidencia cargada | El sistema buscará la incidencia cargada en la base de datos. | Se mostrará la vista con la información de la incidencia y además la posibilidad de poder establecer un recordatorio como alerta para controlar dicha siembra. |
| 13 | Registro de tareas y actividades de campo | Título de actividad, descripción, fecha | El sistema guarda las tareas realizadas por los empleados en la base de datos. | El sistema muestra “Actividad registrada con éxito”. |
| 14 | Consulta de detalles de la actividad de campo | Selección de actividad | El sistema recupera de la base de datos los datos de la actividad. | Se muestra la interfaz de la actividad con toda la info y se puede agregar recordatorios y además los empleados que realizaron la tarea. |
| 15 | Gestión de pagos | - | El sistema recupera la información de acuerdo al calendario y además permite agregar a determinado día un empleado que haya trabajado ese día. | Se mostrará la información de qué días trabajaron los empleados. |
| 16 | Edicion de camapaña | - | El sistema permite modificar los datos de la campaña registrada a travez de un boton en el sistema que te lleva a dicha pantalla. | Se mostrará la información actualizada de la campaña. |
| 17 | ELiminacion de incidencia | - | El sistema permite eliminar los datos de la incidencia registrada a travez de un boton en el sistema | Desaparecera de la pantalla de detalles de campaña la incidencia. |
| 17 | ELiminacion de actividad | - | El sistema permite eliminar los datos de la actividad registrada a travez de un boton en el sistema | Desaparecera de la pantalla de control la actividad. |

---

## ⚙️ Funciones Generales del Sistema

- Mostrar en el inicio la ubicación geográfica de los lotes en el mapa.  
- Guardar el historial completo de campañas y actividades.  
- Generar reportes y estadísticas de costos, rendimiento e insumos.

---

## 🧭 Actores del Sistema

- **Administrador (productor o agricultor principal):** gestiona toda la información del campo.  
- **Sistema:** ejecuta los procesos de almacenamiento, consulta y generación de reportes.

---

## ⚙️ Requerimientos No Funcionales

| Nº | Tipo | Requerimiento | Descripción |
|----|------|----------------|--------------|
| 1 | Rendimiento | El sistema debe responder rápido al cargar los datos de un lote o campaña. | Asegura una experiencia fluida para el usuario. |
| 3 | Usabilidad | La interfaz debe ser intuitiva y fácil de usar, incluso para usuarios sin experiencia técnica. | Se utilizará un diseño basado en Material Design. |
| 4 | Compatibilidad | La aplicación debe ser compatible con dispositivos Android 8.0 (Oreo) o superior. | Permite llegar a un amplio rango de usuarios. |
| 6 | Escalabilidad | El sistema debe permitir agregar más lotes, campañas y usuarios sin afectar el rendimiento. | Asegura que la app pueda crecer sin perder estabilidad. |
| 7 | Mantenibilidad | El código debe estar documentado y estructurado bajo el patrón MVVC. | Facilita futuras actualizaciones o corrección de errores. |
| 8 | Confiabilidad | La app debe garantizar la integridad de los datos aun si se pierde la conexión a Internet. | Se usará Room (sqlite) como base de datos local para sincronización posterior. |
| 10 | Portabilidad | El sistema debe poder ejecutarse en diferentes tamaños de pantalla (smartphones y tablets). | Se aplicará diseño responsive adaptable a pantallas móviles. |
