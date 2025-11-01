# 📘 Especificación de Requerimientos

---

## 👤 Datos del alumno
| Campo | Información |
|--------|--------------|
| **Apellido y Nombre** | Jimenez Lucas Francisco |
| **DNI** | 45704748 |
| **Legajo** | 216/22 |
| **Teléfono** | 3855150575 |
| **Email** | lucasjimnez2004@gmail.com |

---

## 🌱 Requerimientos para el Administrador (Productor)

El **administrador** será quien gestione toda la información de los lotes, cultivos, insumos, maquinarias y reportes del sistema.

| Nº | Nombre del Requerimiento | Entrada | Proceso | Salida |
|----|---------------------------|----------|----------|---------|
| 1 | **Registro de usuario** | Nombre, email, contraseña | El sistema permitirá crear una cuenta nueva para el productor o agricultor. | El sistema muestra un mensaje de “Registro exitoso”. |
| 2 | **Verificación de cuenta** | Email | El sistema deberá enviar un mensaje con código al email ingresado. | El usuario obtendrá el código de verificación para poder registrarse. |
| 3 | **Inicio de sesión** | Email, contraseña | El sistema verifica credenciales en la base de datos. | Acceso al panel principal (Inicio). |
| 4 | **Búsqueda de hectáreas en el mapa** | Nombre de la zona o latitud/longitud | El sistema buscará la dirección ingresada y posicionará la vista. | El sistema mostrará la zona buscada en el mapa. |
| 5 | **Cierre de sesión** | - | El usuario pulsa el botón “Cerrar sesión”. | El sistema redirige al inicio de sesión. |
| 6 | **Registro de lotes de campo** | Nombre del lote, ubicación, cantidad de hectáreas | El sistema permitirá agregar nuevos lotes en la base de datos. | El sistema muestra “Lote registrado con éxito”. |
| 7 | **Eliminación de un lote** | Botón | El usuario selecciona un lote existente y pulsa eliminar. | El sistema muestra “Lote eliminado correctamente”. |
| 8 | **Registro de campañas** | Tipo de cultivo, fechas, descripción, fotos, agroquímicos | El sistema guarda la información de cada cultivo asociado a su lote. | El sistema muestra “Cultivo registrado con éxito”. |
| 9 | **Consulta de detalles de campaña** | Año o campaña | El sistema busca datos anteriores en la base de datos. | El sistema muestra la información del lote para esa campaña. |
| 10 | **Consulta de rendimiento y gastos del cultivo** | Apartado de rendimientos | El sistema buscará los datos de rendimiento y permitirá su edición. | Se muestra la información actualizada por hectárea. |
| 11 | **Reporte o carga de incidencias** | Tipo de incidencia, descripción, foto (opcional) | El sistema guarda los reportes de incidencias agrícolas. | El sistema muestra “Incidencia registrada”. |
| 12 | **Consulta de incidencia cargada** | Botón de ver incidencia cargada | El sistema buscará la incidencia en la base de datos. | Se muestra la información y se puede establecer un recordatorio. |
| 13 | **Registro de tareas y actividades de campo** | Título, descripción, fecha | El sistema guarda las tareas realizadas por los empleados. | El sistema muestra “Actividad registrada con éxito”. |
| 14 | **Consulta de detalles de actividad de campo** | Selección de actividad | El sistema recupera los datos desde la base de datos. | Se muestra toda la información y los empleados involucrados. |
| 15 | **Gestión de pagos** | - | El sistema recupera información según el calendario y permite agregar empleados a días trabajados. | Se muestra la información de los días trabajados y pagos realizados. |

---

## ⚙️ Funciones generales del sistema
- Mostrar en el inicio la ubicación geográfica de los lotes en el mapa.  
- Guardar el historial completo de campañas y actividades.  
- Generar reportes y estadísticas de costos, rendimiento e insumos.  

---

## 🧭 Actores del sistema
| Actor | Descripción |
|--------|--------------|
| **Administrador (Productor o Agricultor Principal)** | Gestiona toda la información del campo. |
| **Sistema** | Ejecuta los procesos de almacenamiento, consulta y generación de reportes. |

---

## ⚙️ Requerimientos No Funcionales

| Nº | Tipo | Requerimiento | Descripción |
|----|------|----------------|--------------|
| 1 | **Rendimiento** | El sistema debe responder rápido al cargar los datos de un lote o campaña. | Asegura una experiencia fluida para el usuario. |
| 2 | **Usabilidad** | La interfaz debe ser intuitiva y fácil de usar. | Basada en Material Design. |
| 3 | **Compatibilidad** | Compatible con dispositivos Android 8.0 (Oreo) o superior. | Permite llegar a un amplio rango de usuarios. |
| 4 | **Escalabilidad** | El sistema debe permitir agregar más lotes, campañas y usuarios sin afectar el rendimiento. | Garantiza crecimiento estable. |
| 5 | **Mantenibilidad** | El código debe estar documentado y estructurado bajo el patrón MVC. | Facilita actualizaciones futuras. |
| 6 | **Confiabilidad** | La app debe garantizar la integridad de los datos aun sin conexión. | Se usará Room como base de datos local. |
| 7 | **Portabilidad** | Adaptable a diferentes tamaños de pantalla (smartphones y tablets). | Diseño responsive adaptable a pantallas móviles. |

---

📅 **Documento elaborado para la aplicación AgroTech-Gamara**
