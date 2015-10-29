================ REAME ================
==== Observaciones sobre entrega 1 ====

Dise�o:

- Tu c�digo me parece bastante bueno y preciso. Se nota que has entendido la materia de clase.
- Tengo sin embargo algunos comentarios menores:
- Por qu� no usaste herencia para los tipos de jugadores? (la l�gica de negocio para calcular el valor de los jugadores no es correcta me parece)
[listo] - Por qu� las entidades est�n en "persistence" ? Las entidades forman parte de la capa de negocios y no de la capa de acceso a datos


Informe:

- En el informe echo de menos las argumentaciones de las decisiones de dise�o. Te doy ejemplos:
- [trabajando en esto] Por qu� no hay herencia para los tipos de jugadores?
- Por qu� el validador tiene los m�todos est�ticos y no es una clase con una interfaz (los Bean usan la clase directamente, lo que viola el principio "D" de SOLID).
- Por qu� decidiste no utilizar enumeraciones?
- No hab�a posibilidad de poner l�gica de negocio en las entidades?

Conclusi�n:
- Repito que tu c�digo es bastante bueno.
- Me parece que las entidades han sido generadas autom�ticamente, aunque no estoy 100% seguro. El hecho de definir tus entidades como lo has hecho
te ha dificultado el dise�o de ciertas partes de tu proyecto (p.ej. el c�lculo del valor de los jugadores).
- El informe es pobre es discusi�n.

Nota: 6,0