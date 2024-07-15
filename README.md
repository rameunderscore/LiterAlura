<h1 align="center"> 📖 LiterAlura 📖 </h1>

App interactiva mediante consola que solicita datos de libros mediante la API Gutendex que permite almacenar datos e itneractuar con una base de datos en PostgreSQL

<h1>Estado del proyecto: FINALIZADO</h1>

El codigo permite la interaccion con una base de datos local creada con PostgreSQL donde se almacenan dos tablas, una llamada "libros" y otra llamada "autores", dentro de ellas se almacenan los datos que el usuario busca y encuentra gracias a la API de Gutendex que muestra una biblioteca amplia de libros.
La interaccion con el usuario se hace mediante un menu de opciones mostrado de la siguiente manera:
![image](https://github.com/user-attachments/assets/0e03da0e-46eb-40e2-a666-6e10f85263fa)
Las opciones del menu se seleccionan gracias a la clase Scanner, donde el usuario ingresa el digito de la opcion que le gustaria realizar.

<h1> Funciones </h1>h1

Opcion 1:
  Esta opcion hace un Request mediante la API para buscar un libro, si el libro es encontrado con exito este mismo se registra en la base de datos.
  ![image](https://github.com/user-attachments/assets/b8a4a322-813f-4f50-84f5-8f2f4553852d)

Opcion 2:
  Esta opcion muestra un historial de la busquedas mediante la API y basandose en la Base de Datos.
  ![image](https://github.com/user-attachments/assets/839ac9ab-9b9b-479a-81fc-d099dba76844)

Opcion 3:
Esta opcion permite listar todos los Autores mediante los DerviedQueries que hacen una busqueda en la base de datos con los datos de los libros previamente guardados.
![image](https://github.com/user-attachments/assets/9b25c49c-98fe-4ab8-a7cd-63f3024bbcc8)

Opcion 4:
  Esta opcion te permite elegir una fecha (año) para filtrar la base de datos y retornar una busqueda de autores vivos 
 durante esa fecha.
 ![image](https://github.com/user-attachments/assets/76a481ad-7f6c-458a-b2d1-1250dc90f0ba)

Opcion 5:
  Finalmente, esta opcion  te permite filtrar los libros en base a su idioma desde una lista predeterminada en base a los datos(libros) previamente guardados en la base de datos.
  ![image](https://github.com/user-attachments/assets/c98f3c31-5726-4d15-9616-de4984e93b42)
  ![image](https://github.com/user-attachments/assets/b8d9b11c-bd80-4113-8d6c-a7f7eb415291)



<h1>Tecnologías utilizadas ✔️:</h1>
- Java
- Spring
- Maven
- PostgreSQL
- Gutendex API: https://gutendex.com/
