# Parcial #2 - Gestión de Eventos (Syncro-App)

Este proyecto es un sistema de gestión de eventos desarrollado con **Javalin 7.0.1**, **H2 Database** y desplegado utilizando una arquitectura de contenedores con **Docker** y **Proxy Inverso**.

## Requerimientos Técnicos Implementados

* **Javalin 7.0.1**: Framework web principal.
* **H2 en Modo Servidor**: Base de datos persistente ejecutándose en el puerto `9092`.
* **Docker Multi-stage Build**: Optimización de la imagen dividiendo el proceso en `builder` y `runtime`.
* **Publicación en Docker Hub**: Imagen disponible en `scarletabreudev/syncro-app`.
* **Seguridad SSL/TLS**: Implementación de proxy inverso con **Caddy** para manejo de certificados y redirección automática del puerto 80 al 443.

---

## Estructura del Proyecto

```
syncro/
├── build/
│   ├── classes/
│   ├── distributions/
│   ├── libs/
│   ├── reports/
│   ├── resources/
│   ├── scripts/
│   └── tmp/
├── data/
│   ├── parcial2.mv.db
│   └── parcial2.trace.db
├── gradle/
│   └── wrapper/
├── src/
│   └── main/
├── build.gradle
├── Caddyfile
├── docker-compose.yml
├── Dockerfile
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle
```

---

## Archivos de Configuración Docker

* `Dockerfile`: Construcción multi-etapa (`builder` → `runtime`).
* `docker-compose.yml`: Orquestación de la aplicación y el proxy inverso.
* `Caddyfile`: Configuración de Caddy para SSL/TLS y redirecciones.

---

## Pasos para Ejecutar el Proyecto

Asegúrate de estar en la raíz del proyecto y ejecuta:

```bash
docker compose up -d
```

Acceso al sistema:
* **Web (vía Proxy):** http://localhost (redirigido automáticamente a HTTPS)
* **App directa:** http://localhost:7070
* **H2 Server:** Puerto `9092` disponible para conexiones externas

---

## Diagrama de Clases

<img width="1118" height="911" alt="image" src="https://github.com/user-attachments/assets/92f53525-6fc8-4649-99eb-b7e0c1352a00" />

---

## 👤 Autores

Scarlet Abreu y Renso Peralta
