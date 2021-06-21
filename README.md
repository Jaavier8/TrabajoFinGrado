# Trabajo Fin de Grado de Javier Rodríguez Gallardo

Repositorio que contiene el código necesario para la realización del TFG: "DISEÑO Y DESARROLLO DE UN SENSOR DE FUENTES DE INTELIGENCIA DE AMENAZAS E INTEGRACIÓN EN UN SISTEMA DE GESTIÓN DINÁMICA DE RIESGOS BASADO EN ONTOLOGÍAS"

## Comenzando 🚀

Para clonar el proyecto ejecuta en un terminal:

`git clone https://github.com/Jaavier8/TrabajoFinGrado/`

### Pre-requisitos 📋

Para el correcto funcionamiento es necesario instalar una serie de bibliotecas como módulos en Python:

1.`pip install stix2`
2.`pip install disposable-email-domains`
3.`pip install otx-misp`
4.`pip install requests`

### Funcionamiento 🔧

1. En la carpeta `tfg_back` se encuentra el código correspondiente al proyecto Java.
  1.1. Abrir el proyecto con Eclipse.
  1.2. Correr el proyecto sobre un servidor Tomcat 9.0.
  1.3. Poner en marcha la base de datos H2.
2. En la carpeta `tfg` se encuentra el programa Python para la descarga de datos, conversión y envío de los mismos a la base de datos.
  2.1. Dentro de la carpeta `tfg` ejecutar `python3 main.py`
  2.2. Dentro de la carpeta `tfg` ejecutar `python3 otx_alienvault/get_alienvault_data.py`
3. En la carpeta `webpage` se encuentra el código de la aplicación web. Lanzarla ejecutando `yarn start`.

## Autores ✒️

* **Javier Rodríguez** - [Jaavier8](https://github.com/Jaavier8)
