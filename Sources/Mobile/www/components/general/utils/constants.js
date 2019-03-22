angular.module('YanbalKiosko.constants', [])

.constant('queryConstants', {
    "createTableColeccion": "CREATE TABLE IF NOT EXISTS KSK_COLECCION (CORRELATIVO_COLECCION integer primary key, CORRELATIVO_COLECCION_PADRE integer, NOMBRE text, NIVEL text, ORDEN integer, COLOR text, HIJOS integer, NIETOS integer)",
    "createTableArchivo": "CREATE TABLE IF NOT EXISTS KSK_ARCHIVO (CORRELATIVO_ARCHIVO integer primary key, CORRELATIVO_COLECCION integer, NOMBRE text, RUTA_ARCHIVO text, RUTA_IMGPREVIA text, DESTACADO numeric, DESCARGABLE numeric, NUEVO numeric, ORDEN integer, ARCHIVO_DESCARGADO text, TAMANO_ARCHIVO numeric, FECHA_PUBLICACION numeric, FECHA_CADUCIDAD numeric, EXTENSION text, DESCARGANDO integer)",
    "createTableVariables": "CREATE TABLE IF NOT EXISTS KSK_VARIABLES_GLOBALES (VARIABLE text unique, VALOR text)",
    "createTableBitacora": "CREATE TABLE IF NOT EXISTS KSK_BITACORA (CORRELATIVO_BITACORA integer primary key, TIPO_LOG text, DESCRIPCION text, ACCION text, NOMBRE_ARCHIVO text, TAMANHO_ARCHIVO text, TIPO_ARCHIVO text, TIPO_DESCARGA text, USUARIO text, PLATAFORMA text, DISPOSITIVO_IDENTIFICADOR text, FECHA_BITACORA numeric)",
    "createTablePais": "CREATE TABLE IF NOT EXISTS KSK_PAIS (CORRELATIVO_PAIS integer, NOMBRE text, CODIGO_PAIS text primary key)",
    "replacePais": "REPLACE INTO KSK_PAIS (CORRELATIVO_PAIS, NOMBRE, CODIGO_PAIS) VALUES (?,?,?)",
    "insertVariable": "REPLACE INTO KSK_VARIABLES_GLOBALES (VARIABLE, VALOR) VALUES (?,?)",
    "updateVariable": "UPDATE KSK_VARIABLES_GLOBALES SET VALOR = ? WHERE VARIABLE = ?",
    "selectVariable": "SELECT VALOR FROM KSK_VARIABLES_GLOBALES WHERE VARIABLE = ?",
    "selectPaises": "SELECT CORRELATIVO_PAIS, NOMBRE, CODIGO_PAIS FROM KSK_PAIS WHERE NOMBRE LIKE '%' || ? || '%' ORDER BY NOMBRE ASC",
    "deletePaises": "DELETE FROM KSK_PAIS",
    "selectUser": "SELECT VALOR FROM KSK_VARIABLES_GLOBALES WHERE VARIABLE = 'USR'",
    "replaceColeccion": "REPLACE INTO KSK_COLECCION (CORRELATIVO_COLECCION, CORRELATIVO_COLECCION_PADRE, NOMBRE, NIVEL, ORDEN, COLOR, HIJOS) VALUES (?,?,?,?,?,?,?)",
    "updateColeccion": "UPDATE KSK_COLECCION SET CORRELATIVO_COLECCION_PADRE = ?, NOMBRE = ?, NIVEL = ?, ORDEN = ?, COLOR = ?, HIJOS = ?) WHERE CORRELATIVO_COLECCION = ?",
    "deleteColeccion": "DELETE FROM KSK_COLECCION WHERE CORRELATIVO_COLECCION = ?",
    "deleteColeccionAll": "DELETE FROM KSK_COLECCION",
    "selectColeccionPrincipal": "SELECT * FROM KSK_COLECCION WHERE CORRELATIVO_COLECCION_PADRE IS NULL ORDER BY ORDEN ASC",
    "selectColeccion": "SELECT *, 1 AS COLECCION FROM KSK_COLECCION AS A WHERE CORRELATIVO_COLECCION_PADRE = ? ORDER BY ORDEN ASC",
    "replaceArchivo": "REPLACE INTO KSK_ARCHIVO (CORRELATIVO_ARCHIVO, CORRELATIVO_COLECCION, NOMBRE, RUTA_ARCHIVO, RUTA_IMGPREVIA, DESTACADO, DESCARGABLE, NUEVO, ORDEN, ARCHIVO_DESCARGADO, TAMANO_ARCHIVO, FECHA_PUBLICACION, FECHA_CADUCIDAD, EXTENSION, DESCARGANDO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
    "updateArchivo": "UPDATE KSK_ARCHIVO SET CORRELATIVO_COLECCION = ?, RUTA_ARCHIVO = ?, RUTA_IMGPREVIA = ?, DESTACADO = ?, DESCARGABLE = ?, NUEVO = ?, ORDEN = ?, ARCHIVO_DESCARGADO = ?, TAMANO_ARCHIVO = ?, FECHA_PUBLICACION = ?, FECHA_CADUCIDAD = ?, EXTENSION = ? WHERE CORRELATIVO_ARCHIVO = ?",
    "updateArchivoNuevo": "UPDATE KSK_ARCHIVO SET NUEVO = 0 WHERE CORRELATIVO_ARCHIVO = ?",
    "updateDescargandoArchivo": "UPDATE KSK_ARCHIVO SET DESCARGANDO = ? WHERE CORRELATIVO_ARCHIVO = ?",
    "updateArchivoRuta": "UPDATE KSK_ARCHIVO SET ARCHIVO_DESCARGADO = ? WHERE CORRELATIVO_ARCHIVO = ?",
    "deleteArchivo": "DELETE FROM KSK_ARCHIVO WHERE CORRELATIVO_ARCHIVO = ?",
    "deleteArchivoAll": "DELETE FROM KSK_ARCHIVO",
    "selectArchivo": "SELECT *, 0 AS COLECCION, (CASE WHEN DATE('now') BETWEEN DATE(substr(FECHA_PUBLICACION,7,4)||'-'||substr(FECHA_PUBLICACION,4,2)||'-'||substr(FECHA_PUBLICACION,1,2)) AND DATE(substr(FECHA_CADUCIDAD,7,4)||'-'||substr(FECHA_CADUCIDAD,4,2)||'-'||substr(FECHA_CADUCIDAD,1,2)) THEN 1 ELSE 0 END) AS PUBLICADO FROM KSK_ARCHIVO WHERE CORRELATIVO_COLECCION = ? ORDER BY ORDEN ASC",
    "selectArchivoNotificacion": "SELECT *, (CASE WHEN DATE('now') BETWEEN DATE(substr(FECHA_PUBLICACION,7,4)||'-'||substr(FECHA_PUBLICACION,4,2)||'-'||substr(FECHA_PUBLICACION,1,2)) AND DATE(substr(FECHA_CADUCIDAD,7,4)||'-'||substr(FECHA_CADUCIDAD,4,2)||'-'||substr(FECHA_CADUCIDAD,1,2)) THEN 1 ELSE 0 END) AS PUBLICADO FROM KSK_ARCHIVO WHERE NUEVO = 1 ORDER BY CORRELATIVO_ARCHIVO DESC",
    "selectArchivoById": "SELECT * FROM KSK_ARCHIVO WHERE CORRELATIVO_ARCHIVO = ?",
    "insertLog": "INSERT INTO KSK_BITACORA (TIPO_LOG, DESCRIPCION, ACCION, NOMBRE_ARCHIVO, TAMANHO_ARCHIVO, TIPO_ARCHIVO, TIPO_DESCARGA, USUARIO, PLATAFORMA, DISPOSITIVO_IDENTIFICADOR, FECHA_BITACORA) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
    "selectBitacora": "SELECT * FROM KSK_BITACORA WHERE TIPO_LOG = '0'",
    "selectGA": "SELECT * FROM KSK_BITACORA WHERE TIPO_LOG = 1",
    "limpiarBitacora": "DELETE FROM KSK_BITACORA WHERE TIPO_LOG = '0'",
    "limpiarGA": "DELETE FROM KSK_BITACORA WHERE TIPO_LOG = 1"
})
.constant('serviceUrlConstants', {
    "servidorDesarrollo": "http://201.234.55.22:9082/YanbalKioskoServices",
    "servidorYanbal": "http://proxynp.unique-yanbal.com",
    "autenticarUsuario": "/integraciondesa/WSIntegracionYanbalStoreWeb/rest/WSMantenimientoUsuarios/validaUsuarioMbl",
    "registrarSesion": "/movil/registrarsesion",
    "cerrarSesion": "/movil/cerrarSesion",
    "listarPaises": "/web/listarPaises",
    "obtenerColeccionesArchivos": "/movil/obtenerColeccionesArchivos",
    "enviarBitacora": "/movil/registrarBitacora",
    "validarArchivoRol": "/movil/validarArchivoRol"
})
.constant('templateUrlConstants', {
    "menuArchivoUrl": "general/views/menu-archivo.html"
})
.constant('textConstants', {
    "inicioSesionDescripcion": "log de inicio de sesion",
    "inicioSesionAccion": "inicio de sesion",
    "cerrarSesionDescripcion": "log de cierre de sesion",
    "cerrarSesionAccion": "cierre de sesion",
    "abrirArchivoDescripcion": "log de abrir archivo",
    "abrirArchivoAccion": "abrir archivo",
    "guardarArchivoDescripcion": "log de guardar archivo",
    "guardarArchivoAccion": "guardar archivo",
    "eliminarArchivoDescripcion": "log de eliminar archivo",
    "eliminarArchivoAccion": "eliminar archivo",
    "PreviewName": "_mini",
    "PreviewExt": "jpg",
    "RutaImagenDefault": "../img/preview.png",
    "windowsPhoneCacheFolder" : "cache",
    "ANDROID_PLATFORM": "ANDROID_PLATFORM",
    "WINDOWS_PLATFORM": "WINDOWS_PLATFORM",
    "IOS_PLATFORM": "IOS_PLATFORM",
    "DATOS_MOVILES": "DATOS_MOVILES",
    "NOMBRE_CARPETA": "NOMBRE_CARPETA",
    "USR": "USR",
    "PASS": "PASS",
    "TOKEN": "TOKEN",
    "PAIS": "PAIS",
    "FECHA_SINCRONIZACION": "FECHA_SINCRONIZACION",
    "TIPOUSR": "TIPOUSR",
    "ROL": "ROL",
    "FECHA_SINCRONIZACION_ALTER": "FECHA_SINCRONIZACION_ALTER",
    "FECHA_INICIAL": "26/06/2000 23:59:59",
    "CODIGO_EXITO": "0",
    "CODIGO_EXITO_YANBAL": "1",
    "CODIGO_TRANSACCION_OK_YANBAL": "0000",
    "CODIGO_USUARIO_NO_EXISTE_YANBAL": "1",
    "CODIGO_USUARIO_STAFF_YANBAL": "1",
    "PAIS_DEFECTO": "PER",
    "USR_STAFF_YANBAL": 1,
    "USR_FFVV_YANBAL": 2,
    "USR_STAFF": 0,
    "CLASSACTIVE": "active",
    "LOCALSTORAGETRUE": "1",
    "LOCALSTORAGEFALSE": "0"
})
.constant('paisesConstants', {
    "Pais1": { nombre: "PERU", codigo: "PER" },
    "Pais2": { nombre: "COLOMBIA", codigo: "COL" },
    "Pais3": { nombre: "BOLIVIA", codigo: "BOL" },
    "Pais4": { nombre: "ECUADOR", codigo: "ECU" },
    "Pais5": { nombre: "GUATEMALA", codigo: "GTM" },
    "Pais6": { nombre: "MEXICO", codigo: "MEX" },
    "Pais7": { nombre: "VENEZUELA", codigo: "VEN" }
})
.constant('tipoContenidoConstants', {
    "Coleccion": 1,
    "Archivo": 2
})
.constant('configurationsConstants', {
    "bitacora": "0",
    "ga": "1",
    "tipoSalidaHex": "hex",
    "tipoSalidaBase64": "base64"
});